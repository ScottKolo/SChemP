package SChemP; 

/**
 * A class representing an electron.  While not used
 * alone often, it contains several helpful methods,
 * including orbital capacities and basic quantum
 * number utilities.
 * @author Scott Kolodziej
 *
 */
public class Electron
{
	// Constants
	static final int S_Capacity = 2;
	static final int P_Capacity = 6;
	static final int D_Capacity = 10;
	static final int F_Capacity = 14;
	
	static final int S_L_Value = 0;
	static final int P_L_Value = 1;
	static final int D_L_Value = 2;
	static final int F_L_Value = 3;
	
	// Quantum Numbers
	private int n;
	private int l;
	private int m;
	private double s;
	
	public Electron()
	{
	}
	
	public Electron(Electron e)
	{
		n = e.getN();
		l = e.getL();
		m = e.getM();
		s = e.getS();
	}
	
	public Electron(int n, int l, int m, double s)
	{
		this.n = n;
		this.l = l;
		this.m = m;
		this.s = s;
	}
	
	public Electron(int n, String l, int m, double s)
	{
		this.n = n;
		this.l = lValue(l);
		this.m = m;
		this.s = s;
	}
	
	public int getN()
	{
		return n;
	}
	
	public int getL()
	{
		return l;
	}
	
	public int getM()
	{
		return m;
	}
	
	public double getS()
	{
		return s;
	}
	
	protected static int orbCapacity(String o)
	{
		if(o.equalsIgnoreCase("s"))
			return S_Capacity;
		else if(o.equalsIgnoreCase("p"))
			return P_Capacity;
		else if(o.equalsIgnoreCase("d"))
			return D_Capacity;
		else if(o.equalsIgnoreCase("f"))
			return F_Capacity;
			
		return 0;
	}
	
	/**
	 * Given an l value (0 < l \u2264 4), returns 
	 * how many electrons fit in the set of orbitals
	 * (i.e. the set of p orbitals in any given energy
	 * level can hold 6 electrons).
	 * @param l
	 * @return
	 */
	protected static int orbCapacity(int l)
	{
		if(l == 0)
			return S_Capacity;
		else if(l == 1)
			return P_Capacity;
		else if(l == 2)
			return D_Capacity;
		else if(l == 3)
			return F_Capacity;
			
		return 0;
	}
	
	/**
	 * Given an energy level (or n+1), returns the 
	 * total electron capacity of the level.  In other 
	 * words, ignoring orbitals, how many electrons can
	 * fit in this energy level.
	 * @param lev
	 * @return
	 */
	protected static int levelCapacity(int lev)
	{
		if(lev == 1)
			return 2;
		else if(lev == 2)
			return 8;
		else if(lev == 3)
			return 18;
		else if(lev == 4)
			return 32;
		else if(lev == 5)
			return 32;
		else if(lev == 6)
			return 18;
		else if(lev == 7)
			return 8;
			
		return 0;
	}
	
	/**
	 * Returns the corresponding l value for the given
	 * orbital (i.e. 0 for "s" and 2 for "d").
	 * @param orbital
	 * @return
	 */
	public static int lValue(String orbital)
	{
		if(orbital.equalsIgnoreCase("s"))
			return S_L_Value;
		else if(orbital.equalsIgnoreCase("p"))
			return P_L_Value;
		else if(orbital.equalsIgnoreCase("d"))
			return D_L_Value;
		else if(orbital.equalsIgnoreCase("f"))
			return F_L_Value;
			
		return 0;
	}
}