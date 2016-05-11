import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener
{
	private Engine engine;
	
	public MyKeyListener(Engine engine)
	{
		this.engine = engine;
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		switch (key)
		{
			case KeyEvent.VK_LEFT:				
				engine.setTrenutniSmer(Smer.LEVO);
				break;
			
			case KeyEvent.VK_UP:
				engine.setTrenutniSmer(Smer.GORE);
				break;
				
			case KeyEvent.VK_RIGHT:
				engine.setTrenutniSmer(Smer.DESNO);
				break;
				
			case KeyEvent.VK_DOWN:
				engine.setTrenutniSmer(Smer.DOLE);
				break;
				
			default:
				break;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
}
