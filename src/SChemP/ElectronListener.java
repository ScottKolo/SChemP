package SChemP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

/**
 * @author   Scott
 */
public class ElectronListener implements ActionListener
{
	ElectronPanel panel;
	
	public ElectronListener()
	{
		
	}
	
	public ElectronListener(ElectronPanel p)
	{
		panel = p;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String temp = ((JRadioButton)e.getSource()).getText();
		if(temp.equals("Bohr"))
			panel.setVisualState(1);
		else if (temp.equals("Nucleus"))
			panel.setVisualState(2);
		else if (temp.equals("Quantum Orbitals"))
			panel.setVisualState(3);
	}
}