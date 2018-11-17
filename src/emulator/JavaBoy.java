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

/*
 * 
 * @author Daniel Simpkins
 * @email <dpsimpkins@gmail.com>
 * @version 0.0.1a (no releases yet)
 * 
 */

package emulator;

/**
 * A Game Boy emulator written in java
 * using swift for UI, java2D for rendering
 * and interpretation for CPU instruction
 * emulation.
 * 
 * @author Daniel Simpkins
 *
 */
public class JavaBoy {
    /**
     * Parses commandline arguments and begins
     * the machine loop.
     * 
     * @param args Path to ROM file and BIOS file.
     */
    public static void main(String[] args) {
        String files[];
        
        if(args.length > 2) {
            files = parseArguments(args);
        } else {
            System.out.println("Invalid arugements! " + 
                                "Try: \"JavaBoy [-r RomName] [-b Biosfile]\""
            );
            return;
        }
        
        Machine machine = new Machine(files[0], files[1]);

        machine.start();
    }
    
    

    /**
     * Looks for the ROM and BIOS flags and paths
     * in the commandline arguments.
     * 
     * @param args Commandline arguments.
     * @return Array of rom and bios paths.
     */
    private static String[] parseArguments(String args[]) {
        String files[] = {"", ""};
        
        for(int i = 0; i < args.length; i += 2) {
            switch(args[i]) {
                case "-b":
                    files[0] = args[i+1];
                    break;
                case "-r":
                    files[1] = args[i+1];
                    break;
                default:
                    System.err.println("Try: JavaBoy [-r RomName] [-b Biosfile]");
                    System.exit(1);
                    break;
            }
        }
        
        return files;
    }

}
