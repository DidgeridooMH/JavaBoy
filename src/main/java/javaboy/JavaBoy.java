/*
 * @author Daniel Simpkins
 * @email <dpsimpkins@gmail.com>
 * @version 1.0-SNAPSHOT
 * 
 */
package javaboy;

/**
 * A Game Boy emulator written in java using swift for UI, java2D for rendering
 * and interpretation for CPU instruction emulation.
 * 
 * @author Daniel Simpkins
 */
public class JavaBoy {
    /**
     * Parses commandline arguments and begins the machine loop.
     * 
     * @param args Path to ROM file and BIOS file.
     */
    public static void main(String[] args) {
        String files[];

        if (args.length > 2) {
            files = parseArguments(args);
        } else {
            System.out.println("Invalid arugements! " + "Try: \"JavaBoy [-r RomName] [-b Biosfile]\"");
            return;
        }

        Machine machine = new Machine(files[0], files[1]);

        machine.start();
    }

    /**
     * Looks for the ROM and BIOS flags and paths in the commandline arguments.
     * 
     * @param args Commandline arguments.
     * @return Array of rom and bios paths.
     */
    private static String[] parseArguments(String args[]) {
        String files[] = { "", "" };

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
            case "-b":
                files[0] = args[i + 1];
                break;
            case "-r":
                files[1] = args[i + 1];
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
