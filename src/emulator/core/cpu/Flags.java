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
 * Stores the flag bits and calculates
 * when they should be set/unset.
 * 
 * @author Daniel Simpkins
 *
 */
public class Flags
{
	/**
	 * Flag constants
	 */
	final static int ZERO = 0x80;
	static final int SIGN = 0x40;
	static final int HALFC = 0x20;
	static final int CARRY = 0x10;
	static final int PARITY = 0x08;
	static final int SUBTRACT = 0x04;
	
	private boolean Z;
	private boolean S;
	private boolean H;
	private boolean C;
	private boolean N;
	private boolean I;

	/** 
	 * Initializes the flag bits 
	 */
	public Flags() {
		Z = false;
		S = false;
		C = false;
		H = false;
		N = false;
		I = true;
	}
	
	public boolean getZero() {
		return Z;
	}
	
	public boolean getSign() {
		return S;
	}
	
	public boolean getHalfCarry() {
		return H;
	}
	
	public boolean getCarry() {
		return C;
	}
	
	public boolean getSubtract() {
		return N;
	}
	
	public boolean getInterrupt() {
		return I;
	}
	
	public void setZero(boolean bool) {
		Z = bool;
	}

	public void setSign(boolean bool) {
		S = bool;
	}
	
	public void setHalfCarry(boolean bool) {
		H = bool;
	}
	
	public void setCarry(boolean bool) {
		C = bool;
	}
	
	public void setSubtract(boolean bool) {
		N = bool;
	}
	
	public void disableInterrupts() {
		I = false;
	}
	
	public void enableInterrupts() {
		I = true;
	}
	
	/**
	 * Sets the flags accordingly to their definitions.
	 * 
	 * @param initial Value before operation was performed.
	 * @param result Value post operation.
	 * @param size If set, 16-values are used.
	 * @param flagbit Flags to test.
	 */
	public void setFlags(int initial, 
						int result, 
						boolean size, 
						int flagbit) {
		
		if((flagbit & ZERO) > 0) {
			this.setZero(result == 0);
		}
		
		// See if the most significant bit is set to find sign
		if((flagbit & SIGN) > 0) {
			if(size) {
				this.setSign((result & 0x8000) > 0);
			} else {
				this.setSign((result & 0x80) > 0);
			}
		}
		
		// More testing needed on testing half-carry
		if((flagbit & HALFC) > 0) {
			this.setHalfCarry((initial & 0x10) < 0 
						&& (result & 0x10) > 0);
		}
		
		if((flagbit & SUBTRACT) > 0) {
			this.setSubtract(true);
		} else {
			this.setSubtract(false);
		}
		
		if((flagbit & CARRY) > 0) {
			if(size) {
				this.setCarry(result > 32767 || result < -32768);
			} else {
				this.setCarry(result > 127 || result < -128);
			}
		}
	}
	
	/**
	 * Combines the flagbits into a single byte.
	 * 
	 * @return Generated status flag
	 */
	public byte generateReg() {
		byte value = 0x0;
		
		value = (byte) ((getZero()) ? 0x80 : 0x0);
		value = (byte) ((getSubtract()) ? (value | 0x40) : value);
		value = (byte) ((getHalfCarry()) ? (value | 0x20) : value);
		value = (byte) ((getCarry()) ? (value | 0x10) : value);
		
		return value;
	}
	
	/**
	 * Parses a byte into flag bits.
	 * 
	 * @param in Byte to convert into flag bits.
	 */
	public void byteToFlags(byte in) {
		setZero((in & 0x80) > 0);
		setSubtract((in & 0x40) > 0);
		setHalfCarry((in & 0x20) > 0);
		setCarry((in & 0x10) > 0);
	}
}
