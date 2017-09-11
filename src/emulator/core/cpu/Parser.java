/*
 * 
 * MIT License
 * 
 * Copyright (c) 2017 Daniel Simpkins
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

package emulator.core.cpu;

import java.util.HashMap;

public class Parser {

	/*
	 *
	 * TODO:
	 * 	- Add timing table
	 * 
	 * Reference - http://pastraiser.com/cpu/gameboy/gameboy_opcodes.html
	 * 
	 */

	private HashMap<Byte, String> opcodes;
	private HashMap<Byte, String> prefix;
	
	public Parser() {
		opcodes = new HashMap<Byte, String>();
		prefix = new HashMap<Byte, String>();
		
		setHashMap();
	}
	
	/*
	 * Set up a search table that contains all
	 * the opcodes for the gameboy.
	 */
	private void setHashMap() {
		// 0x00
		opcodes.put((byte) 0x00, "NOP");
		opcodes.put((byte) 0x01, "LD");
		opcodes.put((byte) 0x02, "LD");
		opcodes.put((byte) 0x03, "INC");
		opcodes.put((byte) 0x04, "INC");
		opcodes.put((byte) 0x05, "DEC");
		opcodes.put((byte) 0x06, "LD");
		opcodes.put((byte) 0x07, "RLCA");
		opcodes.put((byte) 0x08, "LD");
		opcodes.put((byte) 0x09, "ADD");
		opcodes.put((byte) 0x0a, "LD");
		opcodes.put((byte) 0x0b, "DEC");
		opcodes.put((byte) 0x0c, "INC");
		opcodes.put((byte) 0x0d, "DEC");
		opcodes.put((byte) 0x0e, "LD");
		opcodes.put((byte) 0x0f, "RRCA");

		// 0x10
		opcodes.put((byte) 0x10, "STOP");
		opcodes.put((byte) 0x11, "LD");
		opcodes.put((byte) 0x12, "LD");
		opcodes.put((byte) 0x13, "INC");
		opcodes.put((byte) 0x14, "INC");
		opcodes.put((byte) 0x15, "DEC");
		opcodes.put((byte) 0x16, "LD");
		opcodes.put((byte) 0x17, "RLA");
		opcodes.put((byte) 0x18, "JR");
		opcodes.put((byte) 0x19, "ADD");
		opcodes.put((byte) 0x1a, "LD");
		opcodes.put((byte) 0x1b, "DEC");
		opcodes.put((byte) 0x1c, "INC");
		opcodes.put((byte) 0x1d, "DEC");
		opcodes.put((byte) 0x1e, "LD");
		opcodes.put((byte) 0x1f, "RRA");

		// 0x20
		opcodes.put((byte) 0x20, "JR");
		opcodes.put((byte) 0x21, "LD");
		opcodes.put((byte) 0x22, "LD");
		opcodes.put((byte) 0x23, "INC");
		opcodes.put((byte) 0x24, "INC");
		opcodes.put((byte) 0x25, "DEC");
		opcodes.put((byte) 0x26, "LD");
		opcodes.put((byte) 0x27, "DAA");
		opcodes.put((byte) 0x28, "JR");
		opcodes.put((byte) 0x29, "ADD");
		opcodes.put((byte) 0x2a, "LD");
		opcodes.put((byte) 0x2b, "DEC");
		opcodes.put((byte) 0x2c, "INC");
		opcodes.put((byte) 0x2d, "DEC");
		opcodes.put((byte) 0x2e, "LD");
		opcodes.put((byte) 0x2f, "CPL");

		// 0x30
		opcodes.put((byte) 0x30, "JR");
		opcodes.put((byte) 0x31, "LD");
		opcodes.put((byte) 0x32, "LD");
		opcodes.put((byte) 0x33, "INC");
		opcodes.put((byte) 0x34, "INC");
		opcodes.put((byte) 0x35, "DEC");
		opcodes.put((byte) 0x36, "LD");
		opcodes.put((byte) 0x37, "SCF");
		opcodes.put((byte) 0x38, "JR");
		opcodes.put((byte) 0x39, "ADD");
		opcodes.put((byte) 0x3a, "LD");
		opcodes.put((byte) 0x3b, "DEC");
		opcodes.put((byte) 0x3c, "INC");
		opcodes.put((byte) 0x3d, "DEC");
		opcodes.put((byte) 0x3e, "LD");
		opcodes.put((byte) 0x3f, "CCF");
	
		// 0x40
		opcodes.put((byte) 0x40, "LD");
		opcodes.put((byte) 0x41, "LD");
		opcodes.put((byte) 0x42, "LD");
		opcodes.put((byte) 0x43, "LD");
		opcodes.put((byte) 0x44, "LD");
		opcodes.put((byte) 0x45, "LD");
		opcodes.put((byte) 0x46, "LD");
		opcodes.put((byte) 0x47, "LD");
		opcodes.put((byte) 0x48, "LD");
		opcodes.put((byte) 0x49, "LD");
		opcodes.put((byte) 0x4a, "LD");
		opcodes.put((byte) 0x4b, "LD");
		opcodes.put((byte) 0x4c, "LD");
		opcodes.put((byte) 0x4d, "LD");
		opcodes.put((byte) 0x4e, "LD");
		opcodes.put((byte) 0x4f, "LD");

		// 0x50
		opcodes.put((byte) 0x50, "LD");
		opcodes.put((byte) 0x51, "LD");
		opcodes.put((byte) 0x52, "LD");
		opcodes.put((byte) 0x53, "LD");
		opcodes.put((byte) 0x54, "LD");
		opcodes.put((byte) 0x55, "LD");
		opcodes.put((byte) 0x56, "LD");
		opcodes.put((byte) 0x57, "LD");
		opcodes.put((byte) 0x58, "LD");
		opcodes.put((byte) 0x59, "LD");
		opcodes.put((byte) 0x5a, "LD");
		opcodes.put((byte) 0x5b, "LD");
		opcodes.put((byte) 0x5c, "LD");
		opcodes.put((byte) 0x5d, "LD");
		opcodes.put((byte) 0x5e, "LD");
		opcodes.put((byte) 0x5f, "LD");

		// 0x60
		opcodes.put((byte) 0x60, "LD");
		opcodes.put((byte) 0x61, "LD");
		opcodes.put((byte) 0x62, "LD");
		opcodes.put((byte) 0x63, "LD");
		opcodes.put((byte) 0x64, "LD");
		opcodes.put((byte) 0x65, "LD");
		opcodes.put((byte) 0x66, "LD");
		opcodes.put((byte) 0x67, "LD");
		opcodes.put((byte) 0x68, "LD");
		opcodes.put((byte) 0x69, "LD");
		opcodes.put((byte) 0x6a, "LD");
		opcodes.put((byte) 0x6b, "LD");
		opcodes.put((byte) 0x6c, "LD");
		opcodes.put((byte) 0x6d, "LD");
		opcodes.put((byte) 0x6e, "LD");
		opcodes.put((byte) 0x6f, "LD");

		// 0x70
		opcodes.put((byte) 0x70, "LD");
		opcodes.put((byte) 0x71, "LD");
		opcodes.put((byte) 0x72, "LD");
		opcodes.put((byte) 0x73, "LD");
		opcodes.put((byte) 0x74, "LD");
		opcodes.put((byte) 0x75, "LD");
		opcodes.put((byte) 0x76, "HALT");
		opcodes.put((byte) 0x77, "LD");
		opcodes.put((byte) 0x78, "LD");
		opcodes.put((byte) 0x79, "LD");
		opcodes.put((byte) 0x7a, "LD");
		opcodes.put((byte) 0x7b, "LD");
		opcodes.put((byte) 0x7c, "LD");
		opcodes.put((byte) 0x7d, "LD");
		opcodes.put((byte) 0x7e, "LD");
		opcodes.put((byte) 0x7f, "LD");

		// 0x80
		opcodes.put((byte) 0x80, "ADD");
		opcodes.put((byte) 0x81, "ADD");
		opcodes.put((byte) 0x82, "ADD");
		opcodes.put((byte) 0x83, "ADD");
		opcodes.put((byte) 0x84, "ADD");
		opcodes.put((byte) 0x85, "ADD");
		opcodes.put((byte) 0x86, "ADD");
		opcodes.put((byte) 0x87, "ADD");
		opcodes.put((byte) 0x88, "ADC");
		opcodes.put((byte) 0x89, "ADC");
		opcodes.put((byte) 0x8a, "ADC");
		opcodes.put((byte) 0x8b, "ADC");
		opcodes.put((byte) 0x8c, "ADC");
		opcodes.put((byte) 0x8d, "ADC");
		opcodes.put((byte) 0x8e, "ADC");
		opcodes.put((byte) 0x8f, "ADC");

		// 0x90
		opcodes.put((byte) 0x90, "SUB");
		opcodes.put((byte) 0x91, "SUB");
		opcodes.put((byte) 0x92, "SUB");
		opcodes.put((byte) 0x93, "SUB");
		opcodes.put((byte) 0x94, "SUB");
		opcodes.put((byte) 0x95, "SUB");
		opcodes.put((byte) 0x96, "SUB");
		opcodes.put((byte) 0x97, "SUB");
		opcodes.put((byte) 0x98, "SBC");
		opcodes.put((byte) 0x99, "SBC");
		opcodes.put((byte) 0x9a, "SBC");
		opcodes.put((byte) 0x9b, "SBC");
		opcodes.put((byte) 0x9c, "SBC");
		opcodes.put((byte) 0x9d, "SBC");
		opcodes.put((byte) 0x9e, "SBC");
		opcodes.put((byte) 0x9f, "SBC");

		// 0xa0
		opcodes.put((byte) 0xa0, "AND");
		opcodes.put((byte) 0xa1, "AND");
		opcodes.put((byte) 0xa2, "AND");
		opcodes.put((byte) 0xa3, "AND");
		opcodes.put((byte) 0xa4, "AND");
		opcodes.put((byte) 0xa5, "AND");
		opcodes.put((byte) 0xa6, "AND");
		opcodes.put((byte) 0xa7, "AND");
		opcodes.put((byte) 0xa8, "XOR");
		opcodes.put((byte) 0xa9, "XOR");
		opcodes.put((byte) 0xaa, "XOR");
		opcodes.put((byte) 0xab, "XOR");
		opcodes.put((byte) 0xac, "XOR");
		opcodes.put((byte) 0xad, "XOR");
		opcodes.put((byte) 0xae, "XOR");
		opcodes.put((byte) 0xaf, "XOR");

		// 0xb0
		opcodes.put((byte) 0xb0, "OR");
		opcodes.put((byte) 0xb1, "OR");
		opcodes.put((byte) 0xb2, "OR");
		opcodes.put((byte) 0xb3, "OR");
		opcodes.put((byte) 0xb4, "OR");
		opcodes.put((byte) 0xb5, "OR");
		opcodes.put((byte) 0xb6, "OR");
		opcodes.put((byte) 0xb7, "OR");
		opcodes.put((byte) 0xb8, "CP");
		opcodes.put((byte) 0xb9, "CP");
		opcodes.put((byte) 0xba, "CP");
		opcodes.put((byte) 0xbb, "CP");
		opcodes.put((byte) 0xbc, "CP");
		opcodes.put((byte) 0xbd, "CP");
		opcodes.put((byte) 0xbe, "CP");
		opcodes.put((byte) 0xbf, "CP");

		// 0xc0
		opcodes.put((byte) 0xc0, "RET");
		opcodes.put((byte) 0xc1, "POP");
		opcodes.put((byte) 0xc2, "JP");
		opcodes.put((byte) 0xc3, "JP");
		opcodes.put((byte) 0xc4, "CALL");
		opcodes.put((byte) 0xc5, "PUSH");
		opcodes.put((byte) 0xc6, "ADD");
		opcodes.put((byte) 0xc7, "RST");
		opcodes.put((byte) 0xc8, "RET");
		opcodes.put((byte) 0xc9, "RET");
		opcodes.put((byte) 0xca, "JP");
		opcodes.put((byte) 0xcb, "PREFIX");
		opcodes.put((byte) 0xcc, "CALL");
		opcodes.put((byte) 0xcd, "CALL");
		opcodes.put((byte) 0xce, "ADC");
		opcodes.put((byte) 0xcf, "RST");

		// 0xd0
		opcodes.put((byte) 0xd0, "RET");
		opcodes.put((byte) 0xd1, "POP");
		opcodes.put((byte) 0xd2, "JP");
		opcodes.put((byte) 0xd3, "NOP");
		opcodes.put((byte) 0xd4, "CALL");
		opcodes.put((byte) 0xd5, "PUSH");
		opcodes.put((byte) 0xd6, "SUB");
		opcodes.put((byte) 0xd7, "RST");
		opcodes.put((byte) 0xd8, "RET");
		opcodes.put((byte) 0xd9, "RETI");
		opcodes.put((byte) 0xda, "JP");
		opcodes.put((byte) 0xdb, "NOP");
		opcodes.put((byte) 0xdc, "CALL");
		opcodes.put((byte) 0xdd, "NOP");
		opcodes.put((byte) 0xde, "SBC");
		opcodes.put((byte) 0xdf, "RST");

		// 0xe0
		opcodes.put((byte) 0xe0, "LDH");
		opcodes.put((byte) 0xe1, "POP");
		opcodes.put((byte) 0xe2, "LD");
		opcodes.put((byte) 0xe3, "NOP");
		opcodes.put((byte) 0xe4, "NOP");
		opcodes.put((byte) 0xe5, "PUSH");
		opcodes.put((byte) 0xe6, "AND");
		opcodes.put((byte) 0xe7, "RST");
		opcodes.put((byte) 0xe8, "ADD");
		opcodes.put((byte) 0xe9, "JP");
		opcodes.put((byte) 0xea, "LD");
		opcodes.put((byte) 0xeb, "NOP");
		opcodes.put((byte) 0xec, "NOP");
		opcodes.put((byte) 0xed, "NOP");
		opcodes.put((byte) 0xee, "XOR");
		opcodes.put((byte) 0xef, "RST");

		// 0xf0
		opcodes.put((byte) 0xf0, "LDH");
		opcodes.put((byte) 0xf1, "POP");
		opcodes.put((byte) 0xf2, "LD");
		opcodes.put((byte) 0xf3, "DI");
		opcodes.put((byte) 0xf4, "NOP");
		opcodes.put((byte) 0xf5, "PUSH");
		opcodes.put((byte) 0xf6, "OR");
		opcodes.put((byte) 0xf7, "RST");
		opcodes.put((byte) 0xf8, "LD");
		opcodes.put((byte) 0xf9, "LD");
		opcodes.put((byte) 0xfa, "LD");
		opcodes.put((byte) 0xfb, "EI");
		opcodes.put((byte) 0xfc, "NOP");
		opcodes.put((byte) 0xfd, "NOP");
		opcodes.put((byte) 0xfe, "CP");
		opcodes.put((byte) 0xff, "RST");

		//0x00
		prefix.put((byte) 0x00, "RLC");
		prefix.put((byte) 0x01, "RLC");
		prefix.put((byte) 0x02, "RLC");
		prefix.put((byte) 0x03, "RLC");
		prefix.put((byte) 0x04, "RLC");
		prefix.put((byte) 0x05, "RLC");
		prefix.put((byte) 0x06, "RLC");
		prefix.put((byte) 0x07, "RLC");
		prefix.put((byte) 0x08, "RRC");
		prefix.put((byte) 0x09, "RRC");
		prefix.put((byte) 0x0A, "RRC");
		prefix.put((byte) 0x0B, "RRC");
		prefix.put((byte) 0x0C, "RRC");
		prefix.put((byte) 0x0D, "RRC");
		prefix.put((byte) 0x0E, "RRC");
		prefix.put((byte) 0x0F, "RRC");
		
		//0x10
		prefix.put((byte) 0x10, "RL");
		prefix.put((byte) 0x11, "RL");
		prefix.put((byte) 0x12, "RL");
		prefix.put((byte) 0x13, "RL");
		prefix.put((byte) 0x14, "RL");
		prefix.put((byte) 0x15, "RL");
		prefix.put((byte) 0x16, "RL");
		prefix.put((byte) 0x17, "RL");
		prefix.put((byte) 0x18, "RR");
		prefix.put((byte) 0x19, "RR");
		prefix.put((byte) 0x1A, "RR");
		prefix.put((byte) 0x1B, "RR");
		prefix.put((byte) 0x1C, "RR");
		prefix.put((byte) 0x1D, "RR");
		prefix.put((byte) 0x1E, "RR");
		prefix.put((byte) 0x1F, "RR");

		//0x20
		prefix.put((byte) 0x20, "SLA");
		prefix.put((byte) 0x21, "SLA");
		prefix.put((byte) 0x22, "SLA");
		prefix.put((byte) 0x23, "SLA");
		prefix.put((byte) 0x24, "SLA");
		prefix.put((byte) 0x25, "SLA");
		prefix.put((byte) 0x26, "SLA");
		prefix.put((byte) 0x27, "SLA");
		prefix.put((byte) 0x28, "SRA");
		prefix.put((byte) 0x29, "SRA");
		prefix.put((byte) 0x2A, "SRA");
		prefix.put((byte) 0x2B, "SRA");
		prefix.put((byte) 0x2C, "SRA");
		prefix.put((byte) 0x2D, "SRA");
		prefix.put((byte) 0x2E, "SRA");
		prefix.put((byte) 0x2F, "SRA");

		//0x30
		prefix.put((byte) 0x30, "SWAP");
		prefix.put((byte) 0x31, "SWAP");
		prefix.put((byte) 0x32, "SWAP");
		prefix.put((byte) 0x33, "SWAP");
		prefix.put((byte) 0x34, "SWAP");
		prefix.put((byte) 0x35, "SWAP");
		prefix.put((byte) 0x36, "SWAP");
		prefix.put((byte) 0x37, "SWAP");
		prefix.put((byte) 0x38, "SRL");
		prefix.put((byte) 0x39, "SRL");
		prefix.put((byte) 0x3A, "SRL");
		prefix.put((byte) 0x3B, "SRL");
		prefix.put((byte) 0x3C, "SRL");
		prefix.put((byte) 0x3D, "SRL");
		prefix.put((byte) 0x3E, "SRL");
		prefix.put((byte) 0x3F, "SRL");

		//0x40
		prefix.put((byte) 0x40, "BIT");
		prefix.put((byte) 0x41, "BIT");
		prefix.put((byte) 0x42, "BIT");
		prefix.put((byte) 0x43, "BIT");
		prefix.put((byte) 0x44, "BIT");
		prefix.put((byte) 0x45, "BIT");
		prefix.put((byte) 0x46, "BIT");
		prefix.put((byte) 0x47, "BIT");
		prefix.put((byte) 0x48, "BIT");
		prefix.put((byte) 0x49, "BIT");
		prefix.put((byte) 0x4A, "BIT");
		prefix.put((byte) 0x4B, "BIT");
		prefix.put((byte) 0x4C, "BIT");
		prefix.put((byte) 0x4D, "BIT");
		prefix.put((byte) 0x4E, "BIT");
		prefix.put((byte) 0x4F, "BIT");

		//0x50
		prefix.put((byte) 0x50, "BIT");
		prefix.put((byte) 0x51, "BIT");
		prefix.put((byte) 0x52, "BIT");
		prefix.put((byte) 0x53, "BIT");
		prefix.put((byte) 0x54, "BIT");
		prefix.put((byte) 0x55, "BIT");
		prefix.put((byte) 0x56, "BIT");
		prefix.put((byte) 0x57, "BIT");
		prefix.put((byte) 0x58, "BIT");
		prefix.put((byte) 0x59, "BIT");
		prefix.put((byte) 0x5A, "BIT");
		prefix.put((byte) 0x5B, "BIT");
		prefix.put((byte) 0x5C, "BIT");
		prefix.put((byte) 0x5D, "BIT");
		prefix.put((byte) 0x5E, "BIT");
		prefix.put((byte) 0x5F, "BIT");

		//0x60
		prefix.put((byte) 0x60, "BIT");
		prefix.put((byte) 0x61, "BIT");
		prefix.put((byte) 0x62, "BIT");
		prefix.put((byte) 0x63, "BIT");
		prefix.put((byte) 0x64, "BIT");
		prefix.put((byte) 0x65, "BIT");
		prefix.put((byte) 0x66, "BIT");
		prefix.put((byte) 0x67, "BIT");
		prefix.put((byte) 0x68, "BIT");
		prefix.put((byte) 0x69, "BIT");
		prefix.put((byte) 0x6A, "BIT");
		prefix.put((byte) 0x6B, "BIT");
		prefix.put((byte) 0x6C, "BIT");
		prefix.put((byte) 0x6D, "BIT");
		prefix.put((byte) 0x6E, "BIT");
		prefix.put((byte) 0x6F, "BIT");

		//0x70
		prefix.put((byte) 0x70, "BIT");
		prefix.put((byte) 0x71, "BIT");
		prefix.put((byte) 0x72, "BIT");
		prefix.put((byte) 0x73, "BIT");
		prefix.put((byte) 0x74, "BIT");
		prefix.put((byte) 0x75, "BIT");
		prefix.put((byte) 0x76, "BIT");
		prefix.put((byte) 0x77, "BIT");
		prefix.put((byte) 0x78, "BIT");
		prefix.put((byte) 0x79, "BIT");
		prefix.put((byte) 0x7A, "BIT");
		prefix.put((byte) 0x7B, "BIT");
		prefix.put((byte) 0x7C, "BIT");
		prefix.put((byte) 0x7D, "BIT");
		prefix.put((byte) 0x7E, "BIT");
		prefix.put((byte) 0x7F, "BIT");

		//0x80
		prefix.put((byte) 0x80, "RES");
		prefix.put((byte) 0x81, "RES");
		prefix.put((byte) 0x82, "RES");
		prefix.put((byte) 0x83, "RES");
		prefix.put((byte) 0x84, "RES");
		prefix.put((byte) 0x85, "RES");
		prefix.put((byte) 0x86, "RES");
		prefix.put((byte) 0x87, "RES");
		prefix.put((byte) 0x88, "RES");
		prefix.put((byte) 0x89, "RES");
		prefix.put((byte) 0x8A, "RES");
		prefix.put((byte) 0x8B, "RES");
		prefix.put((byte) 0x8C, "RES");
		prefix.put((byte) 0x8D, "RES");
		prefix.put((byte) 0x8E, "RES");
		prefix.put((byte) 0x8F, "RES");

		//0x90
		prefix.put((byte) 0x90, "RES");
		prefix.put((byte) 0x91, "RES");
		prefix.put((byte) 0x92, "RES");
		prefix.put((byte) 0x93, "RES");
		prefix.put((byte) 0x94, "RES");
		prefix.put((byte) 0x95, "RES");
		prefix.put((byte) 0x96, "RES");
		prefix.put((byte) 0x97, "RES");
		prefix.put((byte) 0x98, "RES");
		prefix.put((byte) 0x99, "RES");
		prefix.put((byte) 0x9A, "RES");
		prefix.put((byte) 0x9B, "RES");
		prefix.put((byte) 0x9C, "RES");
		prefix.put((byte) 0x9D, "RES");
		prefix.put((byte) 0x9E, "RES");
		prefix.put((byte) 0x9F, "RES");
		
		//0xA0
		prefix.put((byte) 0xA0, "RES");
		prefix.put((byte) 0xA1, "RES");
		prefix.put((byte) 0xA2, "RES");
		prefix.put((byte) 0xA3, "RES");
		prefix.put((byte) 0xA4, "RES");
		prefix.put((byte) 0xA5, "RES");
		prefix.put((byte) 0xA6, "RES");
		prefix.put((byte) 0xA7, "RES");
		prefix.put((byte) 0xA8, "RES");
		prefix.put((byte) 0xA9, "RES");
		prefix.put((byte) 0xAA, "RES");
		prefix.put((byte) 0xAB, "RES");
		prefix.put((byte) 0xAC, "RES");
		prefix.put((byte) 0xAD, "RES");
		prefix.put((byte) 0xAE, "RES");
		prefix.put((byte) 0xAF, "RES");
		
		//0xB0
		prefix.put((byte) 0xB0, "RES");
		prefix.put((byte) 0xB1, "RES");
		prefix.put((byte) 0xB2, "RES");
		prefix.put((byte) 0xB3, "RES");
		prefix.put((byte) 0xB4, "RES");
		prefix.put((byte) 0xB5, "RES");
		prefix.put((byte) 0xB6, "RES");
		prefix.put((byte) 0xB7, "RES");
		prefix.put((byte) 0xB8, "RES");
		prefix.put((byte) 0xB9, "RES");
		prefix.put((byte) 0xBA, "RES");
		prefix.put((byte) 0xBB, "RES");
		prefix.put((byte) 0xBC, "RES");
		prefix.put((byte) 0xBD, "RES");
		prefix.put((byte) 0xBE, "RES");
		prefix.put((byte) 0xBF, "RES");

		//0xC0
		prefix.put((byte) 0xC0, "SET");
		prefix.put((byte) 0xC1, "SET");
		prefix.put((byte) 0xC2, "SET");
		prefix.put((byte) 0xC3, "SET");
		prefix.put((byte) 0xC4, "SET");
		prefix.put((byte) 0xC5, "SET");
		prefix.put((byte) 0xC6, "SET");
		prefix.put((byte) 0xC7, "SET");
		prefix.put((byte) 0xC8, "SET");
		prefix.put((byte) 0xC9, "SET");
		prefix.put((byte) 0xCA, "SET");
		prefix.put((byte) 0xCB, "SET");
		prefix.put((byte) 0xCC, "SET");
		prefix.put((byte) 0xCD, "SET");
		prefix.put((byte) 0xCE, "SET");
		prefix.put((byte) 0xCF, "SET");

		//0xD0
		prefix.put((byte) 0xD0, "SET");
		prefix.put((byte) 0xD1, "SET");
		prefix.put((byte) 0xD2, "SET");
		prefix.put((byte) 0xD3, "SET");
		prefix.put((byte) 0xD4, "SET");
		prefix.put((byte) 0xD5, "SET");
		prefix.put((byte) 0xD6, "SET");
		prefix.put((byte) 0xD7, "SET");
		prefix.put((byte) 0xD8, "SET");
		prefix.put((byte) 0xD9, "SET");
		prefix.put((byte) 0xDA, "SET");
		prefix.put((byte) 0xDB, "SET");
		prefix.put((byte) 0xDC, "SET");
		prefix.put((byte) 0xDD, "SET");
		prefix.put((byte) 0xDE, "SET");
		prefix.put((byte) 0xDF, "SET");

		//0xE0
		prefix.put((byte) 0xE0, "SET");
		prefix.put((byte) 0xE1, "SET");
		prefix.put((byte) 0xE2, "SET");
		prefix.put((byte) 0xE3, "SET");
		prefix.put((byte) 0xE4, "SET");
		prefix.put((byte) 0xE5, "SET");
		prefix.put((byte) 0xE6, "SET");
		prefix.put((byte) 0xE7, "SET");
		prefix.put((byte) 0xE8, "SET");
		prefix.put((byte) 0xE9, "SET");
		prefix.put((byte) 0xEA, "SET");
		prefix.put((byte) 0xEB, "SET");
		prefix.put((byte) 0xEC, "SET");
		prefix.put((byte) 0xED, "SET");
		prefix.put((byte) 0xEE, "SET");
		prefix.put((byte) 0xEF, "SET");

		//0xF0
		prefix.put((byte) 0xF0, "SET");
		prefix.put((byte) 0xF1, "SET");
		prefix.put((byte) 0xF2, "SET");
		prefix.put((byte) 0xF3, "SET");
		prefix.put((byte) 0xF4, "SET");
		prefix.put((byte) 0xF5, "SET");
		prefix.put((byte) 0xF6, "SET");
		prefix.put((byte) 0xF7, "SET");
		prefix.put((byte) 0xF8, "SET");
		prefix.put((byte) 0xF9, "SET");
		prefix.put((byte) 0xFA, "SET");
		prefix.put((byte) 0xFB, "SET");
		prefix.put((byte) 0xFC, "SET");
		prefix.put((byte) 0xFD, "SET");
		prefix.put((byte) 0xFE, "SET");
		prefix.put((byte) 0xFF, "SET");
	}
	
	/**
	 * Decodes an opcode into an instruction.
	 * 
	 * @param instruction Opcode to decode.
	 * @return String of instruction.
	 */
	public String decodeIns(int instruction) {
		return opcodes.get((byte) instruction);
	}
	
	/**
	 * Decodes a prefix opcode into an instruction.
	 * 
	 * @param instruction Opcode to decode.
	 * @return String of instruction
	 */
	public String decodePrefix(byte instruction) {
		return prefix.get(instruction);
	}
	
}
