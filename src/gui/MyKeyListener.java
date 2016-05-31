package gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.Engine;
import engine.Smer;

public class MyKeyListener implements KeyListener
{
	private Engine engine;
	
	public MyKeyListener(Engine engine)
	{
		this.engine = engine;
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch (key)
		{
			case KeyEvent.VK_LEFT:				
				if (engine.getTrenutniSmer() != Smer.DESNO) 
					engine.setTrenutniSmer(Smer.LEVO);
				break;
			
			case KeyEvent.VK_UP:
				if (engine.getTrenutniSmer() != Smer.DOLE)
					engine.setTrenutniSmer(Smer.GORE);
				break;
				
			case KeyEvent.VK_RIGHT:
				if (engine.getTrenutniSmer() != Smer.LEVO)
					engine.setTrenutniSmer(Smer.DESNO);
				break;
				
			case KeyEvent.VK_DOWN:
				if (engine.getTrenutniSmer() != Smer.GORE)
					engine.setTrenutniSmer(Smer.DOLE);
				break;
				
			default:
				break;
		}
	}
}
