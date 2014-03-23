package SChemP; 

import javax.swing.JApplet;

public class AppletDriver extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8226881480947028085L;

	public void init()
	{
		this.setSize(800,600);
		new SChemPCore(this);
	}
	
	public static void main(String[] args)
	{
		AppletDriver driver = new AppletDriver();
		driver.init();
	}
}
