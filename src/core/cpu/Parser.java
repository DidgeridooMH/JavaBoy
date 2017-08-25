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
	
	private void SetHashMap()
	{
		// 0x0
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
