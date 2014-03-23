package SChemP; 

/**
 * An interface to describe all classes that can be placed in a Compound
 * (i.e. Elements, other Compounds, etc.)
 * @author Scott
 *
 */
public interface Compoundable {

	/**
	 * @return An array of type Compoundable which includes all Compoundables in the
	 * Compoundable.  The order of the elements should match the order of subscripts
	 * given by getSubscripts.
	 * @see #getSubscripts()
	 */
	public Compoundable[] getElements();
	
	/**
	 * @return An array of type int which represents the subscripts of the Compoundables
	 * included in getElements.
	 * @see #getElements();
	 */
	public int[] getSubscripts();
	
	/**
	 * @return A String representation of the Compoundable.
	 */
	public String toString();
	
	/**
	 * @return A String representation of the Compoundable formatted using unicode
	 * (specifically for subscripts and superscripts).
	 */
	public String toUnicodeString();
	
	/**
	 * @return The net charge of the Compoundable object.  If only an Atom,
	 * returns 0.
	 */
	public int getCharge();
}
