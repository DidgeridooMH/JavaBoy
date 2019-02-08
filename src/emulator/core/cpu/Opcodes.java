package emulator.core.cpu;

import java.util.HashMap;
import java.util.Map;

public class Opcodes {
    private Map<Byte, Runnable> funcTable;

    Opcodes(CPU cpu) {
        funcTable = new HashMap<>();

        Addition.buildOpcodes(this.funcTable, cpu);
        BitOperation.buildOpcodes(this.funcTable, cpu);
        Compare.buildOpcodes(this.funcTable, cpu);
        Decrement.buildOpcodes(this.funcTable, cpu);
        Increment.buildOpcodes(this.funcTable, cpu);
        Jump.buildOpcodes(this.funcTable, cpu);
        Load.buildOpcodes(this.funcTable, cpu);
        Subtract.buildOpcodes(this.funcTable, cpu);
        Stack.buildOpcodes(this.funcTable, cpu);
    }

    public void execute(byte instruction) {
        this.funcTable.get(instruction).run();
    }

    boolean isValid(byte instruction) {
        return this.funcTable.containsKey(instruction);
    }
}
