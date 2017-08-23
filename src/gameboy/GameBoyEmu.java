package gameboy;
import gameboy.Machine;

public class GameBoyEmu {
	
	static Machine machine;
	
	public static void main(String argv[]) 
	{
		machine = new Machine();
	
		while(machine.m_state == 1)
			machine.Execute();
		
		return;
	}
	
}
