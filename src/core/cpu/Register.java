/*
 * Register.java
 * 
 * Handles the reading and writing of the
 * CPU registers including combined register
 * arithmetic.
 * 
 */

package core.cpu;

public class Register
{
	int m_value;

	public Register()
	{
		m_value = 0;
	}
	
	public Register(int defValue)
	{
		m_value = defValue;
	}
	
	public void Set(int in)
	{
		m_value = in;
	}
	
	public int get()
	{
		return m_value;
	}
	
	public int GetHighByte()
	{
		int out = (m_value >> 8) & 0xFF;
		return out;
	}
	
	public int GetLowByte()
	{
		int out = (m_value & 0xFF);
		return out;
	}
	
	public void SetHighByte(int in)
	{
		int lowByte = this.GetLowByte();
		
		this.Set((in << 8) | lowByte);
	}
	
	public void SetLowByte(int in)
	{
		int highByte = this.GetHighByte();
		
		this.Set((highByte << 8) | in);
	}
}
