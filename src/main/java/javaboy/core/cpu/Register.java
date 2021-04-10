package javaboy.core.cpu;

/**
 * Stores a 16-bit combined register value.
 * 
 * @author Daniel Simpkins
 *
 */
public class Register {
    private int value;

    private String name;

    public Register() {
        this.value = 0;
    }

    public Register(int defValue, String name) {
        this.value = defValue;
        this.name = name;
    }

    public void set(int in) {
        this.value = in & 0xFFFF;
    }

    public int get() {
        return this.value;
    }

    public int getHighByte() {
        return (this.value >> 8) & 0xFF;
    }

    public int getLowByte() {
        return (this.value & 0xFF);
    }

    public void setHighByte(byte in) {
        this.set(((in & 0xff) << 8) | this.getLowByte());
    }

    public void setLowByte(byte in) {
        this.set((this.getHighByte() << 8) | in & 0xff);
    }

    @Override
    public String toString() {
        return name;
    }
}
