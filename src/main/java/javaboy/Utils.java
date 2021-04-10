package javaboy;

/**
 * Collection of commonly used conversions and print statements.
 * 
 * @author Daniel Simpkins
 */
public class Utils {

    /**
     * Converts a decimal value into printable hex value.
     * 
     * @param num Decimal value number.
     * @return Printable hex value.
     */
    public static String hex(int num) {
        return ("$" + Integer.toHexString(0x10000 | num).substring(1));
    }

    /**
     * Prints a formatted readout of the executed instruction.
     * 
     * @param decodedIns Decoded opcode into instruction.
     * @param ins        Hex value of the opcode.
     * @param location   Location in memory of the executed instruciton
     * @param parameters All parameters the instruction used.
     * @param paramSize  Number parameters the instruction used.
     */
    public static void PrintInstruction(String decodedIns, byte ins, int location, byte parameters[], int paramSize) {

        // System.out.print(hex(location) +
        // ": " +
        // decodedIns +
        // "(" +
        // hex(ins & 0xFF) +
        // ") "
        // );
        //
        // for(int i = 0; i < paramSize; i++) {
        // System.out.print(hex(parameters[i] & 0xFF) + " ");
        // }
        //
        // System.out.print("\n");
    }

    /**
     * Gives the state of a bit numbering from 0-7
     * 
     * @param in     Byte to parse.
     * @param bitNum Bit position to return
     * @return State of bitNum in in.
     */
    public static boolean getBit(byte in, int bitNum) {
        return (((in >> bitNum) & 0x01) > 0);
    }
}
