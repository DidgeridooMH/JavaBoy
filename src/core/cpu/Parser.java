/*
 * Parser.java
 * 
 * This will store all that data necessary to
 * decode the z80 opcodes.
 * 
 * TODO:
 * 	- Add prefix table
 * 	- Add timing table
 * 
 * Reference - http://pastraiser.com/cpu/gameboy/gameboy_opcodes.html
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
	
<<<<<<< HEAD
	/*
	 * Set up a search table that contains all
	 * the opcodes for the gameboy.
	 */
	
	private void SetHashMap()
	{
		// 0x00
=======
	private void SetHashMap()
	{
		// 0x0
>>>>>>> fae6c68f918ee5c7e8e5acf2d0050b164d7ca132
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

<<<<<<< HEAD
		// 0x10
		m_opcodes.put((byte) 0x10, "STOP");
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
=======
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
>>>>>>> fae6c68f918ee5c7e8e5acf2d0050b164d7ca132

		// 0x40
		m_opcodes.put((byte) 0x40, "LD");
		m_opcodes.put((byte) 0x41, "LD");
		m_opcodes.put((byte) 0x42, "LD");
		m_opcodes.put((byte) 0x43, "LD");
		m_opcodes.put((byte) 0x44, "LD");
		m_opcodes.put((byte) 0x45, "LD");
		m_opcodes.put((byte) 0x46, "LD");
		m_opcodes.put((byte) 0x47, "LD");
		m_opcodes.put((byte) 0x48, "LD");
		m_opcodes.put((byte) 0x49, "LD");
		m_opcodes.put((byte) 0x4a, "LD");
		m_opcodes.put((byte) 0x4b, "LD");
		m_opcodes.put((byte) 0x4c, "LD");
		m_opcodes.put((byte) 0x4d, "LD");
		m_opcodes.put((byte) 0x4e, "LD");
		m_opcodes.put((byte) 0x4f, "LD");

		// 0x50
		m_opcodes.put((byte) 0x50, "LD");
		m_opcodes.put((byte) 0x51, "LD");
		m_opcodes.put((byte) 0x52, "LD");
		m_opcodes.put((byte) 0x53, "LD");
		m_opcodes.put((byte) 0x54, "LD");
		m_opcodes.put((byte) 0x55, "LD");
		m_opcodes.put((byte) 0x56, "LD");
		m_opcodes.put((byte) 0x57, "LD");
		m_opcodes.put((byte) 0x58, "LD");
		m_opcodes.put((byte) 0x59, "LD");
		m_opcodes.put((byte) 0x5a, "LD");
		m_opcodes.put((byte) 0x5b, "LD");
		m_opcodes.put((byte) 0x5c, "LD");
		m_opcodes.put((byte) 0x5d, "LD");
		m_opcodes.put((byte) 0x5e, "LD");
		m_opcodes.put((byte) 0x5f, "LD");

		// 0x60
		m_opcodes.put((byte) 0x60, "LD");
		m_opcodes.put((byte) 0x61, "LD");
		m_opcodes.put((byte) 0x62, "LD");
		m_opcodes.put((byte) 0x63, "LD");
		m_opcodes.put((byte) 0x64, "LD");
		m_opcodes.put((byte) 0x65, "LD");
		m_opcodes.put((byte) 0x66, "LD");
		m_opcodes.put((byte) 0x67, "LD");
		m_opcodes.put((byte) 0x68, "LD");
		m_opcodes.put((byte) 0x69, "LD");
		m_opcodes.put((byte) 0x6a, "LD");
		m_opcodes.put((byte) 0x6b, "LD");
		m_opcodes.put((byte) 0x6c, "LD");
		m_opcodes.put((byte) 0x6d, "LD");
		m_opcodes.put((byte) 0x6e, "LD");
		m_opcodes.put((byte) 0x6f, "LD");

		// 0x70
		m_opcodes.put((byte) 0x70, "LD");
		m_opcodes.put((byte) 0x71, "LD");
		m_opcodes.put((byte) 0x72, "LD");
		m_opcodes.put((byte) 0x73, "LD");
		m_opcodes.put((byte) 0x74, "LD");
		m_opcodes.put((byte) 0x75, "LD");
		m_opcodes.put((byte) 0x76, "HALT");
		m_opcodes.put((byte) 0x77, "LD");
		m_opcodes.put((byte) 0x78, "LD");
		m_opcodes.put((byte) 0x79, "LD");
		m_opcodes.put((byte) 0x7a, "LD");
		m_opcodes.put((byte) 0x7b, "LD");
		m_opcodes.put((byte) 0x7c, "LD");
		m_opcodes.put((byte) 0x7d, "LD");
		m_opcodes.put((byte) 0x7e, "LD");
		m_opcodes.put((byte) 0x7f, "LD");

		// 0x80
		m_opcodes.put((byte) 0x80, "ADD");
		m_opcodes.put((byte) 0x81, "ADD");
		m_opcodes.put((byte) 0x82, "ADD");
		m_opcodes.put((byte) 0x83, "ADD");
		m_opcodes.put((byte) 0x84, "ADD");
		m_opcodes.put((byte) 0x85, "ADD");
		m_opcodes.put((byte) 0x86, "ADD");
		m_opcodes.put((byte) 0x87, "ADD");
		m_opcodes.put((byte) 0x88, "ADC");
		m_opcodes.put((byte) 0x89, "ADC");
		m_opcodes.put((byte) 0x8a, "ADC");
		m_opcodes.put((byte) 0x8b, "ADC");
		m_opcodes.put((byte) 0x8c, "ADC");
		m_opcodes.put((byte) 0x8d, "ADC");
		m_opcodes.put((byte) 0x8e, "ADC");
		m_opcodes.put((byte) 0x8f, "ADC");

		// 0x90
		m_opcodes.put((byte) 0x90, "SUB");
		m_opcodes.put((byte) 0x91, "SUB");
		m_opcodes.put((byte) 0x92, "SUB");
		m_opcodes.put((byte) 0x93, "SUB");
		m_opcodes.put((byte) 0x94, "SUB");
		m_opcodes.put((byte) 0x95, "SUB");
		m_opcodes.put((byte) 0x96, "SUB");
		m_opcodes.put((byte) 0x97, "SUB");
		m_opcodes.put((byte) 0x98, "SBC");
		m_opcodes.put((byte) 0x99, "SBC");
		m_opcodes.put((byte) 0x9a, "SBC");
		m_opcodes.put((byte) 0x9b, "SBC");
		m_opcodes.put((byte) 0x9c, "SBC");
		m_opcodes.put((byte) 0x9d, "SBC");
		m_opcodes.put((byte) 0x9e, "SBC");
		m_opcodes.put((byte) 0x9f, "SBC");

		// 0xa0
		m_opcodes.put((byte) 0xa0, "AND");
		m_opcodes.put((byte) 0xa1, "AND");
		m_opcodes.put((byte) 0xa2, "AND");
		m_opcodes.put((byte) 0xa3, "AND");
		m_opcodes.put((byte) 0xa4, "AND");
		m_opcodes.put((byte) 0xa5, "AND");
		m_opcodes.put((byte) 0xa6, "AND");
		m_opcodes.put((byte) 0xa7, "AND");
		m_opcodes.put((byte) 0xa8, "XOR");
		m_opcodes.put((byte) 0xa9, "XOR");
		m_opcodes.put((byte) 0xaa, "XOR");
		m_opcodes.put((byte) 0xab, "XOR");
		m_opcodes.put((byte) 0xac, "XOR");
		m_opcodes.put((byte) 0xad, "XOR");
		m_opcodes.put((byte) 0xae, "XOR");
		m_opcodes.put((byte) 0xaf, "XOR");

		// 0xb0
		m_opcodes.put((byte) 0xb0, "OR");
		m_opcodes.put((byte) 0xb1, "OR");
		m_opcodes.put((byte) 0xb2, "OR");
		m_opcodes.put((byte) 0xb3, "OR");
		m_opcodes.put((byte) 0xb4, "OR");
		m_opcodes.put((byte) 0xb5, "OR");
		m_opcodes.put((byte) 0xb6, "OR");
		m_opcodes.put((byte) 0xb7, "OR");
		m_opcodes.put((byte) 0xb8, "CP");
		m_opcodes.put((byte) 0xb9, "CP");
		m_opcodes.put((byte) 0xba, "CP");
		m_opcodes.put((byte) 0xbb, "CP");
		m_opcodes.put((byte) 0xbc, "CP");
		m_opcodes.put((byte) 0xbd, "CP");
		m_opcodes.put((byte) 0xbe, "CP");
		m_opcodes.put((byte) 0xbf, "CP");

		// 0xc0
		m_opcodes.put((byte) 0xc0, "RET");
		m_opcodes.put((byte) 0xc1, "POP");
		m_opcodes.put((byte) 0xc2, "JP");
		m_opcodes.put((byte) 0xc3, "JP");
		m_opcodes.put((byte) 0xc4, "CALL");
		m_opcodes.put((byte) 0xc5, "PUSH");
		m_opcodes.put((byte) 0xc6, "ADD");
		m_opcodes.put((byte) 0xc7, "RST");
		m_opcodes.put((byte) 0xc8, "RET");
		m_opcodes.put((byte) 0xc9, "RET");
		m_opcodes.put((byte) 0xca, "JP");
		m_opcodes.put((byte) 0xcb, "PREFIX");
		m_opcodes.put((byte) 0xcc, "CALL");
		m_opcodes.put((byte) 0xcd, "CALL");
		m_opcodes.put((byte) 0xce, "ADC");
		m_opcodes.put((byte) 0xcf, "RST");

		// 0xd0
		m_opcodes.put((byte) 0xd0, "RET");
		m_opcodes.put((byte) 0xd1, "POP");
		m_opcodes.put((byte) 0xd2, "JP");
		m_opcodes.put((byte) 0xd3, "NOP");
		m_opcodes.put((byte) 0xd4, "CALL");
		m_opcodes.put((byte) 0xd5, "PUSH");
		m_opcodes.put((byte) 0xd6, "SUB");
		m_opcodes.put((byte) 0xd7, "RST");
		m_opcodes.put((byte) 0xd8, "RET");
		m_opcodes.put((byte) 0xd9, "RETI");
		m_opcodes.put((byte) 0xda, "JP");
		m_opcodes.put((byte) 0xdb, "NOP");
		m_opcodes.put((byte) 0xdc, "CALL");
		m_opcodes.put((byte) 0xdd, "NOP");
		m_opcodes.put((byte) 0xde, "SBC");
		m_opcodes.put((byte) 0xdf, "RST");

		// 0xe0
		m_opcodes.put((byte) 0xe0, "LDH");
		m_opcodes.put((byte) 0xe1, "POP");
		m_opcodes.put((byte) 0xe2, "LD");
		m_opcodes.put((byte) 0xe3, "NOP");
		m_opcodes.put((byte) 0xe4, "NOP");
		m_opcodes.put((byte) 0xe5, "PUSH");
		m_opcodes.put((byte) 0xe6, "AND");
		m_opcodes.put((byte) 0xe7, "RST");
		m_opcodes.put((byte) 0xe8, "ADD");
		m_opcodes.put((byte) 0xe9, "JP");
		m_opcodes.put((byte) 0xea, "LD");
		m_opcodes.put((byte) 0xeb, "NOP");
		m_opcodes.put((byte) 0xec, "NOP");
		m_opcodes.put((byte) 0xed, "NOP");
		m_opcodes.put((byte) 0xee, "XOR");
		m_opcodes.put((byte) 0xef, "RST");

		// 0xf0
		m_opcodes.put((byte) 0x00, "LDH");
		m_opcodes.put((byte) 0x01, "POP");
		m_opcodes.put((byte) 0x02, "LD");
		m_opcodes.put((byte) 0x03, "DI");
		m_opcodes.put((byte) 0x04, "NOP");
		m_opcodes.put((byte) 0x05, "PUSH");
		m_opcodes.put((byte) 0x06, "OR");
		m_opcodes.put((byte) 0x07, "RST");
		m_opcodes.put((byte) 0x08, "LD");
		m_opcodes.put((byte) 0x09, "LD");
		m_opcodes.put((byte) 0x0a, "LD");
		m_opcodes.put((byte) 0x0b, "EI");
		m_opcodes.put((byte) 0x0c, "NOP");
		m_opcodes.put((byte) 0x0d, "NOP");
		m_opcodes.put((byte) 0x0e, "CP");
		m_opcodes.put((byte) 0x0f, "RST");

	}
	
	public String DecodeIns(byte ins)
	{
		return m_opcodes.get(ins);
	}
	
}
