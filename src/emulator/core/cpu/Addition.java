package emulator.core.cpu;

import emulator.Utils;

public interface Addition {

	static void addition(CPU cpu, byte instruction) {
		int value = 0x0;
		int initial = 0x0;
		int result = 0x0;
		String register = "";
		
		switch(instruction) {
			case 0x09:
				initial = cpu.HL.get();
				result = initial + cpu.BC.get();
				cpu.HL.set(initial);
				
				register = "HL, BC";
				
				break;
			case 0x19:
				initial = cpu.HL.get();
				result = initial + cpu.DE.get();
				cpu.HL.set(initial);
				
				register = "HL, DE";
				
				break;
			case 0x29:
				initial = cpu.HL.get();
				result = initial + cpu.HL.get();
				cpu.HL.set(initial);
				
				register = "HL, HL";
				
				break;
			case 0x39:
				initial = cpu.HL.get();
				result = initial + cpu.SP.get();
				cpu.HL.set(initial);
				
				register = "HL, SP";
				
				break;
			case (byte) 0x80:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.BC.getHighByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, B";
				
				break;
			case (byte) 0x81:
				initial = cpu.AF.getLowByte();
				result = initial + cpu.BC.getLowByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, C";
				
				break;
			case (byte) 0x82:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.DE.getHighByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, D";
				
				break;
			case (byte) 0x83:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.DE.getLowByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, E";
				
				break;
			case (byte) 0x84:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.HL.getHighByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, H";
				
				break;
			case (byte) 0x85:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.HL.getLowByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, L";
				
				break;
			case (byte) 0x86:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.memory.Read(cpu.HL.get());
				cpu.AF.setHighByte((byte) result);
				
				register = "A, (HL)";
				
				break;
			case (byte) 0x87:
				initial = cpu.AF.getHighByte();
				result = initial + cpu.AF.getHighByte();
				cpu.AF.setHighByte((byte) result); 
				
				register = "A, A";
				
				break;
			default:
				System.err.println("Error parsing ADD instruction: " + 
									Utils.hex(instruction) + 
									" at " + 
									Utils.hex(cpu.PC.get())
				);
				cpu.error = true;
				return;
		}
		
		if(instruction <= 0x39) {
			cpu.flags.setFlags(	initial, result, 
								true, 
								Flags.HALFC | Flags.CARRY
			);
		} else {
			cpu.flags.setFlags( initial, result, 
								false, 
								Flags.HALFC | Flags.CARRY
			);
		}
		
		Utils.PrintInstruction("ADD " + register, instruction, cpu.PC.get(), null, 0);
		
		cpu.PC.set(cpu.PC.get() + 1);
	}
	
}
