import java.util.Random;

public class Engine
{
	private Element[][] tabla;
	private int pojedeno;
	private Smer trenutniSmer;
	private boolean daLiJeKraj;
	
	public Engine()
	{
		initialize();
	}
	
	public void initialize()
	{
		pojedeno = 0;
		tabla = new Element[15][15];
		trenutniSmer = Smer.values()[new Random().nextInt(4)];
		daLiJeKraj = false;
		
		setPrazno();
		setGlava();
		setProfesor();
	}
	
	private void setPrazno()
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				tabla[i][j] = new Element(i, j, Stanje.PRAZNO);
	}
	
	private void setGlava()
	{
		Random rand = new Random();
		int i = rand.nextInt(15);
		int j = rand.nextInt(15);
		
		tabla[i][j].setStanje(Stanje.GLAVA);
	}
	
	public void setTrenutniSmer(Smer trenutniSmer)
	{
		this.trenutniSmer = trenutniSmer;
	}
	
	private void setProfesor()
	{
		Random rand = new Random();
		int x, y;
		
		while (true)
		{
			x = rand.nextInt(15);
			y = rand.nextInt(15);
			
			if (tabla[x][y].getStanje() != Stanje.GLAVA && tabla[x][y].getStanje() != Stanje.TELO)
			{
				tabla[x][y].setStanje(Stanje.PROFESOR);
				break;
			}
		}
	}
	
	public Stanje getTabla(int i, int j)
	{
		return tabla[i][j].getStanje();
	}
	
	public int getPojedeno()
	{
		return pojedeno;
	}

	public boolean getDaLiJeKraj()
	{
		return daLiJeKraj;
	}
	
	private Element getGlava()
	{
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				if (tabla[i][j].getStanje() == Stanje.GLAVA)
				{
					return tabla[i][j];
				}
		
		return null;
	}
	
	private Element getNext() throws Exception
	{
		Element sled;
		Element glava = getGlava();
		
		int x = glava.getI();
		int y = glava.getJ();
		
		switch (trenutniSmer)
		{
			case LEVO:
				if (y - 1 < 0 || tabla[x][y - 1].getStanje() == Stanje.TELO)
					throw new Exception();
			
				sled = tabla[x][y - 1];					
				break;
							
			case GORE:
				if (x - 1 < 0 || tabla[x - 1][y].getStanje() == Stanje.TELO)
					throw new Exception();
						
				sled = tabla[x - 1][y];		
				break;
						
			case DESNO:
				if (y + 1 >= 15 || tabla[x][y + 1].getStanje() == Stanje.TELO)
					throw new Exception();
							
				sled = tabla[x][y + 1];			
				break;
							
			case DOLE:
				if (x + 1 >= 15 || tabla[x + 1][y].getStanje() == Stanje.TELO)
					throw new Exception();
				
				sled = tabla[x + 1][y];	
				break;
							
			default:
				
				sled = null;
				break;
		}
		
		return sled;
	}
	
	public void pomeriSe()
	{
		Element sled, glava;
		
		try
		{
			sled = getNext();
			glava = getGlava();
					
			sled.setSledeci(glava);
			
			if (sled.getStanje() == Stanje.PROFESOR)
			{	
				sled.setStanje(Stanje.GLAVA);
				glava.setStanje(Stanje.TELO);			
				pojedeno++;
				setProfesor();
			}
			else
			{
				sled.setStanje(Stanje.GLAVA);
				glava.setStanje(Stanje.TELO);
				sled.getPoslednji().setStanje(Stanje.PRAZNO);
				sled.setPoslednjiNull();
			}
		}
		catch (Exception e)
		{
			daLiJeKraj = true;
		}
	}
	
	public String toString()
	{
		String s = "";
		
		for (int i = 0; i < 15; s += "\n", i++)
			for (int j = 0; j < 15; j++)
				if (tabla[i][j].getStanje() == Stanje.GLAVA)
					s += " X ";
				else if (tabla[i][j].getStanje() == Stanje.TELO)
					s += " O ";
				else if (tabla[i][j].getStanje() == Stanje.PROFESOR)
					s += " P ";
				else if (tabla[i][j].getStanje() == Stanje.PRAZNO)
					s += " - ";
				else s += "   ";
		
		return s;
	}
	
}
