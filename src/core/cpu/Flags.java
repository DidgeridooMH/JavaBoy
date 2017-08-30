/*
 * Flags.java
 * 
 * Handles all the functions and storage of
 * CPU flags. Intakes a value and can set the
 * flags accordingly.
 * 
 */

package core.cpu;

public class Flags
{
	static final int ZERO = 0x80;
	static final int SIGN = 0x40;
	static final int HALFC = 0x20;
	static final int CARRY = 0x10;
	static final int PARITY = 0x08;
	static final int SUBTRACT = 0x04;
	
	private boolean m_Z;
	private boolean m_S;
	private boolean m_H;
	private boolean m_C;
	private boolean m_N;
	private boolean m_I;
	
	public Flags()
	{
		m_Z = false;
		m_S = false;
		m_C = false;
		m_H = false;
		m_N = false;
		m_I = true;
	}
	
	public boolean getZero()
	{
		return m_Z;
	}
	
	public boolean getSign()
	{
		return m_S;
	}
	
	public boolean getHalfCarry()
	{
		return m_H;
	}
	
	public boolean getCarry()
	{
		return m_C;
	}
	
	public boolean getSubtract()
	{
		return m_N;
	}
	
	public boolean getInterrupt()
	{
		return m_I;
	}
	
	public void setZero(boolean bool)
	{
		m_Z = bool;
	}

	public void setSign(boolean bool)
	{
		m_S = bool;
	}
	
	public void setHalfCarry(boolean bool)
	{
		m_H = bool;
	}
	
	public void setCarry(boolean bool)
	{
		m_C = bool;
	}
	
	public void setSubtract(boolean bool)
	{
		m_N = bool;
	}
	
	public void disableInterrupts()
	{
		m_I = false;
	}
	
	public void enableInterrupts()
	{
		m_I = true;
	}
	
	/*
	 * Sets the flags accordingly to the bits set.
	 * The second parameter is false if the tested number
	 * is 8 bits long (byte) and true if the tested number
	 * is 16 bits long (short).
	 */
	
	public void setFlags(int initial, int result, boolean size, int flagbit)
	{
		if((flagbit & ZERO) > 0)
			this.setZero(result == 0);
		
		// See if the most significant bit is set to find sign
		
		if((flagbit & SIGN) > 0)
		{
			if(size)
				this.setSign((result & 0x8000) > 0);
			else
				this.setSign((result & 0x80) > 0);
		}
		
		// More testing needed on testing half-carry
		
		if((flagbit & HALFC) > 0)
		{
			this.setHalfCarry((initial & 0x10) < 0 
						&& (result & 0x10) > 0);
		}
		
		if((flagbit & SUBTRACT) > 0)
			this.setSubtract(true);
		else
			this.setSubtract(false);
		
		// More testing is needed to see if this method works
		
		if((flagbit & CARRY) > 0)
		{
			if(size)
				this.setCarry(result > 32767 || result < -32768);
			else
				this.setCarry(result > 127 || result < -128);
		}
	}
	
	public byte GenerateReg()
	{
		byte value = 0x0;
		
		value = (byte) ((getZero()) ? 0x80 : 0x0);
		value = (byte) ((getSubtract()) ? (value | 0x40) : value);
		value = (byte) ((getHalfCarry()) ? (value | 0x20) : value);
		value = (byte) ((getCarry()) ? (value | 0x10) : value);
		
		return value;
	}
	
	public void ByteToFlags(byte in)
	{
		setZero((in & 0x80) > 0);
		setSubtract((in & 0x40) > 0);
		setHalfCarry((in & 0x20) > 0);
		setCarry((in & 0x10) > 0);
		return;
	}
}
