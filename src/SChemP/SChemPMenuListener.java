package SChemP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SChemPMenuListener implements ActionListener
{
	Container frame;
	
	public SChemPMenuListener(Container f)
	{
		frame = f;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		
		if(command.equals("About..."))
		{
			JFrame aboutFrame = new JFrame("About SChemP");
			aboutFrame.setLocation(frame.getX()+250,frame.getY()+150);
			aboutFrame.setSize(300,300);
			aboutFrame.setVisible(true);
		}
		else if(command.equals("New Calculator"))
		{
			JFrame calcFrame = new JFrame("SChemP Calculator");
			//Calculator c = new Calculator(calcFrame);
			calcFrame.setLocation(frame.getX()+250,frame.getY()+150);
			calcFrame.setSize(300,600);
			calcFrame.setVisible(true);
		}
		else if(command.equals(SChemPCore.systemLAFName))
		{
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}catch(Exception ex){}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		else if(command.equals("Java"))
		{
			try{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}catch(Exception ex){}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		else if(command.equals("Motif"))
		{
			try{
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			}catch(Exception ex){}
			SwingUtilities.updateComponentTreeUI(frame);
		}
	}
}