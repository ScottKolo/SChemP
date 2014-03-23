package SChemP; 

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Scott Kolodziej
 *
 */
public class SChemPException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4768940056297713132L;
	protected JFrame frame;
	protected String error;
	
	public SChemPException()
	{
		error = "Error";
		display();
	}
	
	public SChemPException(String error)
	{
		this.error = error;
		display();
	}
	
	public void display()
	{
		JOptionPane.showMessageDialog(frame,
			    error,
			    "SChemP Error",
			    JOptionPane.ERROR_MESSAGE);
	}
}
