/*
 * Parser.java
 * 
 * This will store all that data necessary to
 * decode the z80 opcodes.
 */

package core.cpu;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class Parser {

	HashMap<Byte, String> m_opcodes;
	
	public Parser()
	{
		m_opcodes = new HashMap<Byte, String>();
		
		SetHashMap();
	}
	
	/*
	 * Set up a search table that contains all
	 * the opcodes for the gameboy.
	 */
	
	private void SetHashMap()
	{
		// 0x00
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "LD");
		m_opcodes.put((byte) 0x02, "LD");
		m_opcodes.put((byte) 0x03, "INC");
		m_opcodes.put((byte) 0x04, "INC");
		m_opcodes.put((byte) 0x05, "DEC");
		m_opcodes.put((byte) 0x06, "LD");
		m_opcodes.put((byte) 0x07, "RLCA");
		m_opcodes.put((byte) 0x08, "LD");
		m_opcodes.put((byte) 0x09, "ADD");
		m_opcodes.put((byte) 0x0a, "LD");
		m_opcodes.put((byte) 0x0b, "DEC");
		m_opcodes.put((byte) 0x0c, "INC");
		m_opcodes.put((byte) 0x0d, "DEC");
		m_opcodes.put((byte) 0x0e, "LD");
		m_opcodes.put((byte) 0x0f, "RRCA");

		// 0x10
		m_opcodes.put((byte) 0x10, "STP");
		m_opcodes.put((byte) 0x11, "LD");
		m_opcodes.put((byte) 0x12, "LD");
		m_opcodes.put((byte) 0x13, "INC");
		m_opcodes.put((byte) 0x14, "INC");
		m_opcodes.put((byte) 0x15, "DEC");
		m_opcodes.put((byte) 0x16, "LD");
		m_opcodes.put((byte) 0x17, "RLA");
		m_opcodes.put((byte) 0x18, "JR");
		m_opcodes.put((byte) 0x19, "ADD");
		m_opcodes.put((byte) 0x1a, "LD");
		m_opcodes.put((byte) 0x1b, "DEC");
		m_opcodes.put((byte) 0x1c, "INC");
		m_opcodes.put((byte) 0x1d, "DEC");
		m_opcodes.put((byte) 0x1e, "LD");
		m_opcodes.put((byte) 0x1f, "RRA");

		// 0x20
		m_opcodes.put((byte) 0x20, "JR");
		m_opcodes.put((byte) 0x21, "LD");
		m_opcodes.put((byte) 0x22, "LD");
		m_opcodes.put((byte) 0x23, "INC");
		m_opcodes.put((byte) 0x24, "INC");
		m_opcodes.put((byte) 0x25, "DEC");
		m_opcodes.put((byte) 0x26, "LD");
		m_opcodes.put((byte) 0x27, "DAA");
		m_opcodes.put((byte) 0x28, "JR");
		m_opcodes.put((byte) 0x29, "ADD");
		m_opcodes.put((byte) 0x2a, "LD");
		m_opcodes.put((byte) 0x2b, "DEC");
		m_opcodes.put((byte) 0x2c, "INC");
		m_opcodes.put((byte) 0x2d, "DEC");
		m_opcodes.put((byte) 0x2e, "LD");
		m_opcodes.put((byte) 0x2f, "CPL");

		// 0x30
		m_opcodes.put((byte) 0x30, "JR");
		m_opcodes.put((byte) 0x31, "LD");
		m_opcodes.put((byte) 0x32, "LD");
		m_opcodes.put((byte) 0x33, "INC");
		m_opcodes.put((byte) 0x34, "INC");
		m_opcodes.put((byte) 0x35, "DEC");
		m_opcodes.put((byte) 0x36, "LD");
		m_opcodes.put((byte) 0x37, "SCF");
		m_opcodes.put((byte) 0x38, "JR");
		m_opcodes.put((byte) 0x39, "ADD");
		m_opcodes.put((byte) 0x3a, "LD");
		m_opcodes.put((byte) 0x3b, "DEC");
		m_opcodes.put((byte) 0x3c, "INC");
		m_opcodes.put((byte) 0x3d, "DEC");
		m_opcodes.put((byte) 0x3e, "LD");
		m_opcodes.put((byte) 0x3f, "CCF");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

		// 0x0
		m_opcodes.put((byte) 0x00, "NOP");
		m_opcodes.put((byte) 0x01, "NOP");
		m_opcodes.put((byte) 0x02, "NOP");
		m_opcodes.put((byte) 0x03, "NOP");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "NOP");
		m_opcodes.put((byte) 0x06, "NOP");
		m_opcodes.put((byte) 0x07, "NOP");
		m_opcodes.put((byte) 0x08, "NOP");
		m_opcodes.put((byte) 0x09, "NOP");
		m_opcodes.put((byte) 0x0a, "NOP");
		m_opcodes.put((byte) 0x0b, "NOP");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "NOP");
		m_opcodes.put((byte) 0x0f, "NOP");

	}
	
	public String DecodeIns(byte ins)
	{
		return m_opcodes.get(ins);
	}
	
}
