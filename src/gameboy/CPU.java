package gameboy;

public class CPU {

	Memory m_memory; // Get reference of the memory unit
	byte m_stack[];
	
	// CPU Status Flags
	boolean m_Z;	// Zero Flag
	boolean m_P;	// Parity Flag
	boolean m_I;	// Interrupt Flag
	boolean m_CF;	// Carry Flag
	
	
	// CPU Registers
	byte m_A;
	byte m_F;
	byte m_B;
	byte m_C;
	byte m_D;
	byte m_E;
	byte m_H;
	byte m_L;
	short m_SP;
	int m_PC;
	
	
	// Error flag
	boolean m_error;
	
	// Used to combine registers ie: HL AB CD
	// or to combine two bytes to a Word
	short RegPair(short reg1, short reg2)
	{
		return (short) (((reg1 & 0xFF) << 8) | (reg2 & 0xFF));
	}
	
	public CPU(Memory mem)
	{
		// Reset Registers
		m_PC	= 0x0;
		m_A		= 0x0;
		m_F		= 0x0;
		m_B		= 0x0;
		m_C		= 0x0;
		m_D 	= 0x0;
		m_E 	= 0x0;
		m_H 	= 0x0;
		m_L 	= 0x0;
		m_SP 	= 0x0;
		m_PC 	= 0x0; // ROM bank starts at 0x150
		
		// Reference the memory
		m_memory = mem;
		m_stack = new byte[0x0f];
		
		// Reset flags
		m_error = false;
		m_Z 	= false;
		m_I		= true;
		m_P		= false;
		m_CF	= false;
		
		return;
	}
	
	private void SetError()
	{
		m_error = true;
		return;
	}
	
	public boolean IsError()
	{
		return m_error;
	}
	
	// Creates a string after converting decimal to hex
	private String hex(int num)
	{
		return ("0x" + (Integer.toHexString(num & 0xFF)));
	}
	
	private String hex16(int num)
	{
		return ("0x" + (Integer.toHexString(num & 0xFFFF)));
	}
	
	// Stack operations
	private void Push(byte in)
	{
		m_memory.Write(in, m_SP);
		m_SP--;
		return;
	}
	private void Push16(short in)
	{
		short low = (short) (in & 0x00ff);
		short high = (short) (in >>> 4);
		Push((byte) high);
		Push((byte) low);
		
		return;
	}
	
	private byte Pull()
	{
		byte out = m_memory.Read(m_SP);
		m_memory.Write((byte) 0, m_SP);
		m_SP++;
		return out;
	}
	
	private short Pull16()
	{
		short low = Pull();
		short high = Pull();
		
		short out = RegPair(high, low);
		
		return out;
	}
	
	// Loads data into registers or memory
	private void ld(byte ins)
	{
		byte parameters[] = { 	m_memory.Read(m_PC+1), 
								m_memory.Read(m_PC+2) };
		
		System.out.print(hex16(m_PC));
		System.out.print(": ld ");
		System.out.print(hex(parameters[0]));
		System.out.print(" ");
		System.out.print(hex(parameters[1]));
		System.out.print("\n");
		
		switch(ins)
		{
		case 0x01:
			m_B = parameters[1];
			m_C = parameters[0];
			m_PC += 3;
			break;
			
		case 0x02:
			m_memory.Write(m_A, RegPair(m_B, m_C));
			m_PC++;
			break;
			
		case 0x06:
			m_B = parameters[0];
			m_PC += 2;
			break;
			
		case 0x0A:
			m_A = m_memory.Read(RegPair(m_B, m_C));
			m_PC++;
			break;
		
		case 0x0E:
			m_C = parameters[0];
			m_PC += 2;
			break;
			
		case 0x11:
			m_D = parameters[1];
			m_E = parameters[0];
			m_PC += 3;
			break;
			
		case 0x12:
			m_memory.Write(m_A, RegPair(m_D, m_E));
			m_PC++;
			break;
			
		case 0x1A:
			m_A = m_memory.Read(RegPair(m_D, m_E));
			m_PC++;
			break;
			
		case 0x21:
			m_H = parameters[1];
			m_L = parameters[0];
			m_PC += 3;
			break;
		
		case 0x31:
			m_SP = (short) ((parameters[1] << 8) | parameters[0]);
			m_PC += 3;
			break;
			
		case 0x32:
			int address = RegPair(m_H, m_L) - 1;
			m_H = (byte) (address >> 8);
			m_L = (byte) (address & 0xFF);
			m_memory.Write(m_A, address);
			m_PC++;
			break;
			
		case 0x3E:
			m_A = parameters[0];
			m_PC += 2;
			break;
			
		case 0x4F:
			m_C = m_A;
			m_PC++;
			break;
			
		case 0x58:
			m_E = m_B;
			m_PC++;
			break;
			
		case 0x77:
			m_memory.Write(m_A, RegPair(m_H, m_L));
			m_PC++;
			break;
			
		case (byte) 0xE0:
			m_memory.Write(m_A, 0xFF00 + parameters[0]);
			m_PC += 2;
			break;
			
		case (byte) 0xE2:
			m_memory.Write(m_A, (0xFF00 + m_C));
			m_PC++;
			break;
			
		default:
			System.out.println("Unknown variant of \"LD\"");
			SetError();
			break;
		}
	}
	
	
	// Does an exclusive or operation and stores the result in a
	private void xor(byte ins)
	{
		System.out.print(hex16(m_PC));
		System.out.print(": xor\n");
		
		switch(ins)
		{
		case (byte) 0xAF:
			m_A ^= m_A;
			if(m_A == 0)
				m_Z = true;
			if(m_A == 0)
				m_P = false;
			else
				m_P = true;
			m_PC++;
			break;
		default:
			System.out.println("Unknown variant of \"XOR\"");
			SetError();
			break;
		}
		
		return;
	}
	
	
	// Jumps to a subroutine if conditions allow
	private void jr(byte ins)
	{
		byte parameter = m_memory.Read(m_PC+1);
		
		System.out.print(hex16(m_PC));
		System.out.print(": jr(");
		System.out.print(hex(ins));
		System.out.print(") ");
		System.out.print(hex(parameter));
		System.out.print("\n");
		
		switch(ins)
		{
		// Not Zero
		case 0x20:
			if(!m_Z)
			{
				m_PC += 2;
				m_PC += parameter;
			}
			else
				m_PC += 2;
			break;
		
		default:
			System.err.println("Unknown variant of \"JR\"");
			break;
		}
		
		return;
	}
	
	private void jp(byte ins)
	{
		short parameters[] = { 	m_memory.Read(m_PC+1), 
								m_memory.Read(m_PC+2) };

		System.out.print(hex16(m_PC));
		System.out.print(": jp ");
		System.out.print(hex(parameters[0]));
		System.out.print(" ");
		System.out.print(hex(parameters[1]));
		System.out.print("\n");
		
		switch(ins)
		{	
		case (byte) 0xC3:
			m_PC = (int) (RegPair(parameters[1], parameters[0]));
			break;
			
		default:
			SetError();
			break;
		}
		return;
	}
	
	void SetFlag(byte ins)
	{
		System.out.print(hex16(m_PC));
		
		switch(ins)
		{
		case 0x37:
			System.out.print(": scf\n");
			
			m_CF = true;
			m_PC++;
			break;
		
		case (byte) 0xF3:
			System.out.print(": di\n");
		
			m_I = false;
			m_PC++;
			break;
			
		default:
			System.err.println("Unknown flag set operation!");
			SetError();
			break;
		}
		return;
	}
	
	private void Prefix(byte ins)
	{
		byte parameter = m_memory.Read(m_PC+1);
		
		switch(parameter)
		{
		case (byte) 0x7C:
			if(((m_H & 0x80) >>> 7) == 0)
				m_Z = true;
			else
				m_Z = false;
			m_PC += 2;
			break;
			
		default:
			System.err.println("Unsupported prefix statement\n");
			SetError();
			break;
		}
		
		return;
	}
	private void Call(byte ins)
	{
		System.out.print(hex(m_PC));
		System.out.print(": call ");
		
		switch(ins)
		{
		case (byte) 0xCD:	
			short address[] = { m_memory.Read(m_PC+1),
								m_memory.Read(m_PC+2) };
			
			System.out.print(hex(address[0]));
			System.out.print(" ");
			System.out.print(hex(address[1]));
			System.out.print("\n");
		
			Push16((short) m_PC);
			m_PC = RegPair(address[1], address[0]);
			break;
		
		default:
			System.err.println("Unknown variant of the call function\n");
			SetError();
			break;
		}
		return;
	}
	
	void Execute()
	{
		byte instruction = m_memory.Read(m_PC);
		
		switch(instruction)
		{
		case 0x0:
			System.out.print(hex(m_PC) + ": NOP\n");
			SetError();
			m_PC++;
			break;
		
		case 0x01:
		case 0x02:
		case 0x06:
		case 0x0A:
		case 0x0E:
		case 0x11:
		case 0x12:
		case 0x1A:
		case 0x21:
		case 0x31:
		case 0x32:
		case 0x3E:
		case 0x4F:
		case 0x58:
		case 0x77:
		case (byte) 0xE0:
		case (byte) 0xE2:
			ld(instruction);
			break;
			
		case (byte) 0xAF:
			xor(instruction);
			break;
			
		case 0x20:
			jr(instruction);
			break;
			
		case (byte) 0xC3:
			jp(instruction);
			break;
		
		case 0x37:
		case (byte) 0xF3:
			SetFlag(instruction);
			break;
			
		case (byte) 0xCB:
			Prefix(instruction);
			break;
			
		case 0x0C:
			System.out.print(hex(m_PC));
			System.out.print(": INC C\n");
			m_C++;kin
			m_PC++;
			break;
			
		case (byte) 0xC5:
			System.out.print(hex(m_PC));
			System.out.print(": push BC\n");
			Push16(RegPair(m_B, m_C));
			m_PC++;
			break;
			
		case (byte) 0xCD:
			Call(instruction);
			break;
			
		default:
			System.err.println("Unsupported OPCode: " + hex(instruction) + " at " + hex(m_PC));
			SetError();
			return;
		}
		
		return;
	}
	
}
