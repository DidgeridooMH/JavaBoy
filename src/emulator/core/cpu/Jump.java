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

import emulator.Utils;

import java.util.Map;

/**
 * Handles jump, jump-to-subroutine,
 * and call instructions.
 * 
 * @author Daniel Simpkins
 *
 */
public class Jump {

    public static void buildOpcodes(Map<Byte, Runnable> funcTable, CPU cpu) {
        funcTable.put((byte)0xC4, () -> Jump.call(cpu, "NZ", !cpu.flags.getZero(), (byte)0xC4));
        funcTable.put((byte)0xCC, () -> Jump.call(cpu, "Z", cpu.flags.getZero(), (byte)0xCC));
        funcTable.put((byte)0xD4, () -> Jump.call(cpu, "NC", !cpu.flags.getCarry(), (byte)0xD4));
        funcTable.put((byte)0xDC, () -> Jump.call(cpu, "C", cpu.flags.getCarry(), (byte)0xDC));
        funcTable.put((byte)0xCD, () -> Jump.call(cpu, "", true, (byte)0xCD));
    }

    private static void callLogInstruction(CPU cpu, String condition, int address, byte instruction) {
        Utils.PrintInstruction("CALL " + condition + address,
                instruction,
                cpu.PC.get(),
                null, 0
        );
    }

    private static void call(CPU cpu, String condition, boolean doJump, byte instruction) {
        if(doJump) {
            byte parameters[] = { cpu.memory.read(cpu.PC.get() + 1),
                    cpu.memory.read(cpu.PC.get() + 2) };
            int address = ((short) parameters[1] << 8) | (short) (parameters[0] & 0xFF);

            cpu.push16((short) (cpu.PC.get() + 3));
            cpu.PC.set(address);

            callLogInstruction(cpu, condition, address, instruction);
        } else {
            cpu.PC.set(cpu.PC.get() + 3);
            callLogInstruction(cpu, condition, 0x0, instruction);
        }
    }
    
    /**
     * Increments the Program Counter by
     * a signed byte.
     * 
     * @param cpu Reference to CPU object.
     * @param instruction Instruction opcode.
     */
    static void jumpSubroutine(CPU cpu, byte instruction) {
        byte parameter[] = { cpu.memory.read(cpu.PC.get() + 1) };
        boolean jump = false;
        
        //System.out.print(Utils.hex(cpu.PC.get()) + ": JR ");
        
        switch(instruction) {
            case 0x18:
                jump = true;
                break;
            case 0x20:
                jump = (!cpu.flags.getZero());
                //System.out.print("NZ ");
                break;
            case 0x28:
                jump = (cpu.flags.getZero());
                //System.out.print("Z ");
                break;
            case 0x30:
                jump = (!cpu.flags.getCarry());
                //System.out.print("NC ");
                break;
            case 0x38:
                jump = (cpu.flags.getCarry());
                //System.out.print("C ");
                break;
            default:
                System.err.println("Unknown variant of JR: " + 
                                    Utils.hex(instruction & 0xFF) + 
                                    " at " + 
                                    Utils.hex(cpu.PC.get()));
                cpu.error = true;
                return;
        }
        
        //System.out.print(Utils.hex(cpu.PC.get() + parameter[0] + 2) + "(" + Utils.hex(instruction) + ")\n");
        
        // Add 2 to account for the JR instruction size
        if(jump) {
            cpu.PC.set(cpu.PC.get() + parameter[0] + 2);
        } else {
            cpu.PC.set(cpu.PC.get() + 2);
        }
    }

    /**
     * Pops a 16-bit address from the stack
     * then loads it into the Program Counter.
     * 
     * @param cpu Reference to CPU object.
     * @param instruction Instruction opcode.
     */
    static void returnFromCall(CPU cpu, byte instruction) {
        String condition = "";
        
        switch(instruction) {
            case (byte) 0xC0:
                condition = "NZ";
                
                if(!cpu.flags.getZero()) {
                    cpu.PC.set(cpu.pop16());
                } else {
                    cpu.PC.set(cpu.PC.get() + 2);
                }
                
                break;
            case (byte) 0xC8:
                condition = "Z";
            
                if(cpu.flags.getZero()) {
                    cpu.PC.set(cpu.pop16());
                } else {
                    cpu.PC.set(cpu.PC.get() + 2);
                }
                
                break;
            case (byte) 0xD0:
                condition = "NC";
            
                if(!cpu.flags.getCarry()) {
                    cpu.PC.set(cpu.pop16());
                } else {
                    cpu.PC.set(cpu.PC.get() + 2);
                }
                
                break;
            case (byte) 0xD8:
                condition = "C";
            
                if(cpu.flags.getCarry()) {
                    cpu.PC.set(cpu.pop16());
                } else {
                    cpu.PC.set(cpu.PC.get() + 2);
                }
                
                break;
            case (byte) 0xC9:
                cpu.PC.set(cpu.pop16());
                break;
            default:
                System.err.println("Unknown variant of RET: " + 
                                    Utils.hex(instruction & 0xFF) + 
                                    " at " + 
                                    Utils.hex(cpu.PC.get())
                );
                cpu.error = true;
                return;
        }
        
        Utils.PrintInstruction("RET " + condition, 
                                instruction, 
                                cpu.PC.get(), 
                                null, 0);
    }
    
    /**
     * Sets the pointer counter to a specified
     * address in memory.
     * 
     * @param cpu Reference to CPU object.
     * @param instruction Instruction opcode.
     */
    static void jump(CPU cpu, byte instruction) {
        byte parameter[] = {     cpu.memory.read(cpu.PC.get() + 1),
                                cpu.memory.read(cpu.PC.get() + 2) };
        boolean jump = false;
        
        // System.out.print(Utils.hex(cpu.PC.get()) + ": JP ");
        
        switch(instruction) {
            case (byte) 0xC3:
                jump = true;
                break;
            case (byte) 0xC2:
                jump = (!cpu.flags.getZero());
                // System.out.print("NZ ");
                break;
            case (byte) 0xCA:
                jump = (cpu.flags.getZero());
                // System.out.print("Z ");
                break;
            case (byte) 0xD2:
                jump = (!cpu.flags.getCarry());
                // System.out.print("NC ");
                break;
            case (byte) 0xDA:
                jump = (cpu.flags.getCarry());
                // System.out.print("C ");
                break;
            default:
                System.err.println("Unknown variant of JP: " +
                                    Utils.hex(instruction & 0xFF) + 
                                    " at " + 
                                    Utils.hex(cpu.PC.get()));
                cpu.error = true;
                return;
        }
        
        // System.out.print(Utils.hex(((parameter[1] << 8) & 0xFF00) | parameter[0]) + "(" + Utils.hex(instruction & 0xFF) + ")\n");
        
        if(jump) {
            int highByte = (parameter[1] << 8) & 0xFF00;
            int lowByte = parameter[0];
            int address = (highByte & 0xFF00) | (lowByte & 0x00FF);
            cpu.PC.set(address);
        } else {
            cpu.PC.set(cpu.PC.get() + 2);
        }
    }
    
}
