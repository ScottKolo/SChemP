package SChemP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author   Scott
 */
public class CalculatorListener implements ActionListener
{
	Calculator calc;
	
	public CalculatorListener(Calculator c)
	{
		calc = c;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command.equals("Enter"))
			calc.enter();
		else if(command.equals("Clear"))
			calc.clear();
		else
		{
			calc.ioField.append(command);
		}
	}
}