package SChemP;

/**
 * A class representing an atom or ion.
 * @author Scott Kolodziej
 *
 */
public class Atom implements Compoundable
{
	Element element;
	int charge;
	
	public Atom()
	{
		
	}
	
	public Atom(Element e)
	{
		element = e;
	}
	
	/**
	 * Creates an Atom for the given name or symbol in
	 * String form.  For example, if the argument is
	 * "He" or "Helium" the atom returned will represent helium.
	 * @param nameOrSymbol
	 * @throws SChemPException
	 */
	public Atom(String nameOrSymbol) throws SChemPException
	{
		Element e = null;
		
		try{
		e = PeriodicTable.getElement(nameOrSymbol, "NAME");
		}catch(SChemPException se){}
		
		if(e == null)
			try{
			e = PeriodicTable.getElement(nameOrSymbol, "SYMBOL");
			}catch(SChemPException se){}
		
		if(e == null)
		{
			throw new SChemPException("No Such Element: " + nameOrSymbol);
		}
		
		element = e;
	}
	
	public Atom(Element e, int charge)
	{
		element = e;
		this.charge = charge;
	}
	
	public Atom(String nameOrSymbol, int charge) throws SChemPException
	{
		Element e = null;
		
		e = PeriodicTable.getElement(nameOrSymbol, "NAME");
		
		if(e == null)
			e = PeriodicTable.getElement(nameOrSymbol, "SYMBOL");
		
		if(e == null)
		{
			throw new SChemPException("No Such Element: " + nameOrSymbol);
		}
		
		element = e;
		this.charge = charge;
	}
	
	/**
	 * A String representation of the Element, just its Symbol.
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return element.symbol;
	}
	
	/* (non-Javadoc)
	 * @see SChemP.Compoundable#toUnicodeString()
	 */
	public String toUnicodeString()
	{
		return element.symbol;
	}
	
	public Compoundable[] getElements()
	{
		Compoundable[] elements = new Compoundable[1];
		elements[0] = this;
		
		return elements;
	}
	
	public int[] getSubscripts()
	{
		int[] subscripts = new int[1];
		subscripts[0] = 1;
		
		return subscripts;
	}
	
	public boolean equals(Compoundable c)
	{
		return (c instanceof Atom && (c.toString()).equals(this.toString()));
	}
	
	public Element getElement()
	{
		return element;
	}
	
	public int getCharge()
	{
		return charge;
	}
}
