package SChemP;

import javax.swing.JTable;

/**
 * @author Scott
 *
 */
public class EquationTable extends JTable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 477647763638189880L;

	public EquationTable()
	{
		super();
	}
	
	public EquationTable(Object[][] data, Compoundable[] columns)
	{
		super(data, columns);
	}
	
	public EquationTable(Object[][] data, String[] columns)
	{
		super(data, columns);
	}
}
