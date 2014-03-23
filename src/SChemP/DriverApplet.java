package SChemP; 

import javax.swing.JApplet;
import javax.swing.JFrame;

public class DriverApplet extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1263938946998609507L;

	public void init()
	{
		new SChemPCore(new JFrame("SChemP v1.0"));
	}
}