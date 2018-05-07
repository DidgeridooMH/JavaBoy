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

/**
 * Stores a 16-bit combined register value.
 * 
 * @author Daniel Simpkins
 *
 */
public class Register
{
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
