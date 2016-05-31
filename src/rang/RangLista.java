package rang;


public class RangLista
{
	private RangElement prvi;

	public void dodaj(RangElement element)
	{
		if (prvi == null)
			prvi = element;
		else if (element.getPojedeno() > prvi.getPojedeno())
		{
			element.setSledeci(prvi);
			prvi = element;
		}
		else
		{
			RangElement tren = prvi;
			
			while (tren.getSledeci() != null && tren.getSledeci().getPojedeno() > element.getPojedeno())
				tren = tren.getSledeci();
			
			element.setSledeci(tren.getSledeci());
			tren.setSledeci(element);
		}
	}

	public RangElement getPrvi()
	{
		return prvi;
	}
	
}
