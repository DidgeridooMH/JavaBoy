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

package emulator;

/**
 * Collection of commonly used conversions
 * and print statements.
 * 
 * @author Daniel Simpkins
 *
 */
public interface Utils {

    /**
     * Converts a decimal value into printable
     * hex value.
     * 
     * @param num Decimal value number.
     * @return Printable hex value.
     */
    static String hex(int num) {    
        return ("$" + 
                Integer.
                toHexString(0x10000 | num).
                substring(1)
        );
    }
    
    /**
     * Prints a formatted readout of the
     * executed instruction.
     * 
     * @param decodedIns Decoded opcode into instruction.
     * @param ins Hex value of the opcode.
     * @param location Location in memory of the executed instruciton
     * @param parameters All parameters the instruction used.
     * @param paramSize Number parameters the instruction used.
     */
    static void PrintInstruction(String decodedIns, 
                                    byte ins, 
                                    int location, 
                                    byte parameters[], 
                                    int paramSize) {
        
//        System.out.print(hex(location) +
//                            ": " +
//                            decodedIns +
//                            "(" +
//                            hex(ins & 0xFF) +
//                            ") "
//        );
//
//        for(int i = 0; i < paramSize; i++) {
//            System.out.print(hex(parameters[i] & 0xFF) + " ");
//        }
//
//        System.out.print("\n");
    }
    
    /**
     * Gives the state of a bit numbering from 0-7
     * 
     * @param in Byte to parse.
     * @param bitNum Bit position to return
     * @return State of bitNum in in.
     */
    static boolean getBit(byte in, int bitNum) {
        return (((in >> bitNum) & 0x01) > 0);
    }
}
