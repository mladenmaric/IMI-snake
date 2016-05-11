
public class Element
{
	private Stanje stanje;
	private Element sledeci;
	private int i;
	private int j;
	
	public Element(int i, int j, Stanje stanje)
	{
		this.i = i;
		this.j = j;
		this.stanje = stanje;
		this.sledeci = null;
	}

	public Stanje getStanje()
	{
		return stanje;
	}

	public void setStanje(Stanje stanje)
	{
		this.stanje = stanje;
	}
	
	public void setSledeci(Element e)
	{
		this.sledeci = e;
	}
	
	public Element getPoslednji()
	{
		Element e = this;
		
		while (e.sledeci != null)
			e = e.sledeci;
		
		return e;
	}
	
	public void setPoslednjiNull()
	{
		Element e = this;
		
		if (e.sledeci != null)
		{
			while (e.sledeci.sledeci != null)
				e = e.sledeci;
			
			e.sledeci = null;
		}
			
	}

	public int getI()
	{
		return i;
	}

	public int getJ()
	{
		return j;
	}

	
}
