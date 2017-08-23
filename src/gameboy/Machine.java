package gameboy;

public class Machine {

	CPU m_cpu;
	Memory m_memory;
	//	GPU m_graphics;
	
	int m_state = 0; // 0 = off, 1 = on, 2 = paused
	
	public Machine()
	{	
		m_memory = new Memory("bios/bios.bin", "ROMS/pokemon.gb");
		m_cpu = new CPU(m_memory);
		
		m_state = 1;
		
		return;
	}
	
	public void Execute() 
	{
		m_cpu.Execute();
		
		if(m_cpu.IsError())
			m_state = 0;
		
		return;
	}
	
}
