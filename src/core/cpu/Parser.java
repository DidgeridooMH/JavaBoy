/*
 * Parser.java
 * 
 * This will store all that data necessary to
 * decode the z80 opcodes.
 * 
 * TODO:
 * 	- Add timing table
 * 
 * Reference - http://pastraiser.com/cpu/gameboy/gameboy_opcodes.html
 */

package core.cpu;

import java.util.HashMap;

public class Parser {

	HashMap<Byte, String> m_opcodes;
	HashMap<Byte, String> m_prefix;
	
	public Parser()
	{
		m_opcodes = new HashMap<Byte, String>();
		m_prefix = new HashMap<Byte, String>();
		
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

		//0x00
		m_prefix.put((byte) 0x00, "RLC");
		m_prefix.put((byte) 0x01, "RLC");
		m_prefix.put((byte) 0x02, "RLC");
		m_prefix.put((byte) 0x03, "RLC");
		m_prefix.put((byte) 0x04, "RLC");
		m_prefix.put((byte) 0x05, "RLC");
		m_prefix.put((byte) 0x06, "RLC");
		m_prefix.put((byte) 0x07, "RLC");
		m_prefix.put((byte) 0x08, "RRC");
		m_prefix.put((byte) 0x09, "RRC");
		m_prefix.put((byte) 0x0A, "RRC");
		m_prefix.put((byte) 0x0B, "RRC");
		m_prefix.put((byte) 0x0C, "RRC");
		m_prefix.put((byte) 0x0D, "RRC");
		m_prefix.put((byte) 0x0E, "RRC");
		m_prefix.put((byte) 0x0F, "RRC");
		
		//0x10
		m_prefix.put((byte) 0x10, "RL");
		m_prefix.put((byte) 0x11, "RL");
		m_prefix.put((byte) 0x12, "RL");
		m_prefix.put((byte) 0x13, "RL");
		m_prefix.put((byte) 0x14, "RL");
		m_prefix.put((byte) 0x15, "RL");
		m_prefix.put((byte) 0x16, "RL");
		m_prefix.put((byte) 0x17, "RL");
		m_prefix.put((byte) 0x18, "RR");
		m_prefix.put((byte) 0x19, "RR");
		m_prefix.put((byte) 0x1A, "RR");
		m_prefix.put((byte) 0x1B, "RR");
		m_prefix.put((byte) 0x1C, "RR");
		m_prefix.put((byte) 0x1D, "RR");
		m_prefix.put((byte) 0x1E, "RR");
		m_prefix.put((byte) 0x1F, "RR");

		//0x20
		m_prefix.put((byte) 0x20, "SLA");
		m_prefix.put((byte) 0x21, "SLA");
		m_prefix.put((byte) 0x22, "SLA");
		m_prefix.put((byte) 0x23, "SLA");
		m_prefix.put((byte) 0x24, "SLA");
		m_prefix.put((byte) 0x25, "SLA");
		m_prefix.put((byte) 0x26, "SLA");
		m_prefix.put((byte) 0x27, "SLA");
		m_prefix.put((byte) 0x28, "SRA");
		m_prefix.put((byte) 0x29, "SRA");
		m_prefix.put((byte) 0x2A, "SRA");
		m_prefix.put((byte) 0x2B, "SRA");
		m_prefix.put((byte) 0x2C, "SRA");
		m_prefix.put((byte) 0x2D, "SRA");
		m_prefix.put((byte) 0x2E, "SRA");
		m_prefix.put((byte) 0x2F, "SRA");

		//0x30
		m_prefix.put((byte) 0x30, "SWAP");
		m_prefix.put((byte) 0x31, "SWAP");
		m_prefix.put((byte) 0x32, "SWAP");
		m_prefix.put((byte) 0x33, "SWAP");
		m_prefix.put((byte) 0x34, "SWAP");
		m_prefix.put((byte) 0x35, "SWAP");
		m_prefix.put((byte) 0x36, "SWAP");
		m_prefix.put((byte) 0x37, "SWAP");
		m_prefix.put((byte) 0x38, "SRL");
		m_prefix.put((byte) 0x39, "SRL");
		m_prefix.put((byte) 0x3A, "SRL");
		m_prefix.put((byte) 0x3B, "SRL");
		m_prefix.put((byte) 0x3C, "SRL");
		m_prefix.put((byte) 0x3D, "SRL");
		m_prefix.put((byte) 0x3E, "SRL");
		m_prefix.put((byte) 0x3F, "SRL");

		//0x40
		m_prefix.put((byte) 0x40, "BIT");
		m_prefix.put((byte) 0x41, "BIT");
		m_prefix.put((byte) 0x42, "BIT");
		m_prefix.put((byte) 0x43, "BIT");
		m_prefix.put((byte) 0x44, "BIT");
		m_prefix.put((byte) 0x45, "BIT");
		m_prefix.put((byte) 0x46, "BIT");
		m_prefix.put((byte) 0x47, "BIT");
		m_prefix.put((byte) 0x48, "BIT");
		m_prefix.put((byte) 0x49, "BIT");
		m_prefix.put((byte) 0x4A, "BIT");
		m_prefix.put((byte) 0x4B, "BIT");
		m_prefix.put((byte) 0x4C, "BIT");
		m_prefix.put((byte) 0x4D, "BIT");
		m_prefix.put((byte) 0x4E, "BIT");
		m_prefix.put((byte) 0x4F, "BIT");

		//0x50
		m_prefix.put((byte) 0x50, "BIT");
		m_prefix.put((byte) 0x51, "BIT");
		m_prefix.put((byte) 0x52, "BIT");
		m_prefix.put((byte) 0x53, "BIT");
		m_prefix.put((byte) 0x54, "BIT");
		m_prefix.put((byte) 0x55, "BIT");
		m_prefix.put((byte) 0x56, "BIT");
		m_prefix.put((byte) 0x57, "BIT");
		m_prefix.put((byte) 0x58, "BIT");
		m_prefix.put((byte) 0x59, "BIT");
		m_prefix.put((byte) 0x5A, "BIT");
		m_prefix.put((byte) 0x5B, "BIT");
		m_prefix.put((byte) 0x5C, "BIT");
		m_prefix.put((byte) 0x5D, "BIT");
		m_prefix.put((byte) 0x5E, "BIT");
		m_prefix.put((byte) 0x5F, "BIT");

		//0x60
		m_prefix.put((byte) 0x60, "BIT");
		m_prefix.put((byte) 0x61, "BIT");
		m_prefix.put((byte) 0x62, "BIT");
		m_prefix.put((byte) 0x63, "BIT");
		m_prefix.put((byte) 0x64, "BIT");
		m_prefix.put((byte) 0x65, "BIT");
		m_prefix.put((byte) 0x66, "BIT");
		m_prefix.put((byte) 0x67, "BIT");
		m_prefix.put((byte) 0x68, "BIT");
		m_prefix.put((byte) 0x69, "BIT");
		m_prefix.put((byte) 0x6A, "BIT");
		m_prefix.put((byte) 0x6B, "BIT");
		m_prefix.put((byte) 0x6C, "BIT");
		m_prefix.put((byte) 0x6D, "BIT");
		m_prefix.put((byte) 0x6E, "BIT");
		m_prefix.put((byte) 0x6F, "BIT");

		//0x70
		m_prefix.put((byte) 0x70, "BIT");
		m_prefix.put((byte) 0x71, "BIT");
		m_prefix.put((byte) 0x72, "BIT");
		m_prefix.put((byte) 0x73, "BIT");
		m_prefix.put((byte) 0x74, "BIT");
		m_prefix.put((byte) 0x75, "BIT");
		m_prefix.put((byte) 0x76, "BIT");
		m_prefix.put((byte) 0x77, "BIT");
		m_prefix.put((byte) 0x78, "BIT");
		m_prefix.put((byte) 0x79, "BIT");
		m_prefix.put((byte) 0x7A, "BIT");
		m_prefix.put((byte) 0x7B, "BIT");
		m_prefix.put((byte) 0x7C, "BIT");
		m_prefix.put((byte) 0x7D, "BIT");
		m_prefix.put((byte) 0x7E, "BIT");
		m_prefix.put((byte) 0x7F, "BIT");

		//0x80
		m_prefix.put((byte) 0x80, "RES");
		m_prefix.put((byte) 0x81, "RES");
		m_prefix.put((byte) 0x82, "RES");
		m_prefix.put((byte) 0x83, "RES");
		m_prefix.put((byte) 0x84, "RES");
		m_prefix.put((byte) 0x85, "RES");
		m_prefix.put((byte) 0x86, "RES");
		m_prefix.put((byte) 0x87, "RES");
		m_prefix.put((byte) 0x88, "RES");
		m_prefix.put((byte) 0x89, "RES");
		m_prefix.put((byte) 0x8A, "RES");
		m_prefix.put((byte) 0x8B, "RES");
		m_prefix.put((byte) 0x8C, "RES");
		m_prefix.put((byte) 0x8D, "RES");
		m_prefix.put((byte) 0x8E, "RES");
		m_prefix.put((byte) 0x8F, "RES");

		//0x90
		m_prefix.put((byte) 0x90, "RES");
		m_prefix.put((byte) 0x91, "RES");
		m_prefix.put((byte) 0x92, "RES");
		m_prefix.put((byte) 0x93, "RES");
		m_prefix.put((byte) 0x94, "RES");
		m_prefix.put((byte) 0x95, "RES");
		m_prefix.put((byte) 0x96, "RES");
		m_prefix.put((byte) 0x97, "RES");
		m_prefix.put((byte) 0x98, "RES");
		m_prefix.put((byte) 0x99, "RES");
		m_prefix.put((byte) 0x9A, "RES");
		m_prefix.put((byte) 0x9B, "RES");
		m_prefix.put((byte) 0x9C, "RES");
		m_prefix.put((byte) 0x9D, "RES");
		m_prefix.put((byte) 0x9E, "RES");
		m_prefix.put((byte) 0x9F, "RES");
		
		//0xA0
		m_prefix.put((byte) 0xA0, "RES");
		m_prefix.put((byte) 0xA1, "RES");
		m_prefix.put((byte) 0xA2, "RES");
		m_prefix.put((byte) 0xA3, "RES");
		m_prefix.put((byte) 0xA4, "RES");
		m_prefix.put((byte) 0xA5, "RES");
		m_prefix.put((byte) 0xA6, "RES");
		m_prefix.put((byte) 0xA7, "RES");
		m_prefix.put((byte) 0xA8, "RES");
		m_prefix.put((byte) 0xA9, "RES");
		m_prefix.put((byte) 0xAA, "RES");
		m_prefix.put((byte) 0xAB, "RES");
		m_prefix.put((byte) 0xAC, "RES");
		m_prefix.put((byte) 0xAD, "RES");
		m_prefix.put((byte) 0xAE, "RES");
		m_prefix.put((byte) 0xAF, "RES");
		
		//0xB0
		m_prefix.put((byte) 0xB0, "RES");
		m_prefix.put((byte) 0xB1, "RES");
		m_prefix.put((byte) 0xB2, "RES");
		m_prefix.put((byte) 0xB3, "RES");
		m_prefix.put((byte) 0xB4, "RES");
		m_prefix.put((byte) 0xB5, "RES");
		m_prefix.put((byte) 0xB6, "RES");
		m_prefix.put((byte) 0xB7, "RES");
		m_prefix.put((byte) 0xB8, "RES");
		m_prefix.put((byte) 0xB9, "RES");
		m_prefix.put((byte) 0xBA, "RES");
		m_prefix.put((byte) 0xBB, "RES");
		m_prefix.put((byte) 0xBC, "RES");
		m_prefix.put((byte) 0xBD, "RES");
		m_prefix.put((byte) 0xBE, "RES");
		m_prefix.put((byte) 0xBF, "RES");

		//0xC0
		m_prefix.put((byte) 0xC0, "SET");
		m_prefix.put((byte) 0xC1, "SET");
		m_prefix.put((byte) 0xC2, "SET");
		m_prefix.put((byte) 0xC3, "SET");
		m_prefix.put((byte) 0xC4, "SET");
		m_prefix.put((byte) 0xC5, "SET");
		m_prefix.put((byte) 0xC6, "SET");
		m_prefix.put((byte) 0xC7, "SET");
		m_prefix.put((byte) 0xC8, "SET");
		m_prefix.put((byte) 0xC9, "SET");
		m_prefix.put((byte) 0xCA, "SET");
		m_prefix.put((byte) 0xCB, "SET");
		m_prefix.put((byte) 0xCC, "SET");
		m_prefix.put((byte) 0xCD, "SET");
		m_prefix.put((byte) 0xCE, "SET");
		m_prefix.put((byte) 0xCF, "SET");

		//0xD0
		m_prefix.put((byte) 0xD0, "SET");
		m_prefix.put((byte) 0xD1, "SET");
		m_prefix.put((byte) 0xD2, "SET");
		m_prefix.put((byte) 0xD3, "SET");
		m_prefix.put((byte) 0xD4, "SET");
		m_prefix.put((byte) 0xD5, "SET");
		m_prefix.put((byte) 0xD6, "SET");
		m_prefix.put((byte) 0xD7, "SET");
		m_prefix.put((byte) 0xD8, "SET");
		m_prefix.put((byte) 0xD9, "SET");
		m_prefix.put((byte) 0xDA, "SET");
		m_prefix.put((byte) 0xDB, "SET");
		m_prefix.put((byte) 0xDC, "SET");
		m_prefix.put((byte) 0xDD, "SET");
		m_prefix.put((byte) 0xDE, "SET");
		m_prefix.put((byte) 0xDF, "SET");

		//0xE0
		m_prefix.put((byte) 0xE0, "SET");
		m_prefix.put((byte) 0xE1, "SET");
		m_prefix.put((byte) 0xE2, "SET");
		m_prefix.put((byte) 0xE3, "SET");
		m_prefix.put((byte) 0xE4, "SET");
		m_prefix.put((byte) 0xE5, "SET");
		m_prefix.put((byte) 0xE6, "SET");
		m_prefix.put((byte) 0xE7, "SET");
		m_prefix.put((byte) 0xE8, "SET");
		m_prefix.put((byte) 0xE9, "SET");
		m_prefix.put((byte) 0xEA, "SET");
		m_prefix.put((byte) 0xEB, "SET");
		m_prefix.put((byte) 0xEC, "SET");
		m_prefix.put((byte) 0xED, "SET");
		m_prefix.put((byte) 0xEE, "SET");
		m_prefix.put((byte) 0xEF, "SET");

		//0xF0
		m_prefix.put((byte) 0xF0, "SET");
		m_prefix.put((byte) 0xF1, "SET");
		m_prefix.put((byte) 0xF2, "SET");
		m_prefix.put((byte) 0xF3, "SET");
		m_prefix.put((byte) 0xF4, "SET");
		m_prefix.put((byte) 0xF5, "SET");
		m_prefix.put((byte) 0xF6, "SET");
		m_prefix.put((byte) 0xF7, "SET");
		m_prefix.put((byte) 0xF8, "SET");
		m_prefix.put((byte) 0xF9, "SET");
		m_prefix.put((byte) 0xFA, "SET");
		m_prefix.put((byte) 0xFB, "SET");
		m_prefix.put((byte) 0xFC, "SET");
		m_prefix.put((byte) 0xFD, "SET");
		m_prefix.put((byte) 0xFE, "SET");
		m_prefix.put((byte) 0xFF, "SET");
		
	}
	
	public String DecodeIns(byte ins)
	{
		return m_opcodes.get(ins);
	}
	
	public String DecodePrefix(byte ins)
	{
		return m_prefix.get(ins);
	}
	
}
