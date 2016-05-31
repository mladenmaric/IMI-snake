package rang;


public class RangElement
{
	private String ime;
	private int pojedeno;
	private RangElement sledeci;
	
	public RangElement(String ime, int pojedeno)
	{
		super();
		this.ime = ime;
		this.pojedeno = pojedeno;
	}

	public String getIme()
	{
		return ime;
	}

	public int getPojedeno()
	{
		return pojedeno;
	}

	public RangElement getSledeci()
	{
		return sledeci;
	}

	public void setSledeci(RangElement sledeci)
	{
		this.sledeci = sledeci;
	}
	
	

}
