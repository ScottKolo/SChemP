package SChemP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import static java.lang.Math.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 */

/**
 * @author Scott Kolodziej
 *
 */
public class PeriodicTablePanel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5096527709752247370L;
	private static ArrayList<JButton> buttonList;
	
	
	/**
	 * Default (and only) constructor for a PeriodicTablePanel.  Automatically
	 * draws the Periodic Table onto itself.  Highly dependent on the PeriodicTable
	 * class.
	 * @see PeriodicTable
	 */
	public PeriodicTablePanel()
	{
		super();
		setLayout(new BorderLayout());
		drawPanel(this, BorderLayout.CENTER);
	}
	
	public void drawPanel(JPanel panel2, Object constraints)
	{
		JPanel panel = new JPanel();
		buttonList = new ArrayList<JButton>();
		for(int p = 0; p < PeriodicTable.getSize(); p++)
		{
			buttonList.add(new JButton());
		}
		panel.setLayout(new GridLayout(12,20));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		SChemPCore.progress.report("Loading Periodic Table Graphics");
		addBlanks(panel,1);
		panel.add(new JLabel("1",SwingConstants.CENTER));
		addBlanks(panel,16);
		panel.add(new JLabel("18",SwingConstants.CENTER));
		addBlanks(panel,2);
		addElements(panel,1);
		panel.add(new JLabel("2",SwingConstants.CENTER));
		addBlanks(panel,10);
		panel.add(new JLabel("13",SwingConstants.CENTER));
		panel.add(new JLabel("14",SwingConstants.CENTER));
		panel.add(new JLabel("15",SwingConstants.CENTER));
		panel.add(new JLabel("16",SwingConstants.CENTER));
		panel.add(new JLabel("17",SwingConstants.CENTER));
		addElements(panel,2);
		addBlanks(panel,2);
		for(int k = 3; k < 5; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,10);
		for(int k = 5; k < 11; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,2);
		for(int k = 11; k < 13; k++)
		{
			addElements(panel,k);
		}
		panel.add(new JLabel("3",SwingConstants.CENTER));
		panel.add(new JLabel("4",SwingConstants.CENTER));
		panel.add(new JLabel("5",SwingConstants.CENTER));
		panel.add(new JLabel("6",SwingConstants.CENTER));
		panel.add(new JLabel("7",SwingConstants.CENTER));
		panel.add(new JLabel("3",SwingConstants.CENTER));
		panel.add(new JLabel("4",SwingConstants.CENTER));
		panel.add(new JLabel("5",SwingConstants.CENTER));
		panel.add(new JLabel("6",SwingConstants.CENTER));
		panel.add(new JLabel("7",SwingConstants.CENTER));
		for(int k = 13; k < 19; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,2);
		for(int k = 19; k < 37; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,2);
		for(int k = 37; k < 55; k++)
		{
			addElements(panel,k);
		}
		
		SChemPCore.progress.report();
		
		addBlanks(panel,2);
		for(int k = 55; k < 57; k++)
		{
			addElements(panel,k);
		}
		panel.add(new JLabel("*", JLabel.CENTER));
		for(int k = 72; k < 87; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,2);
		for(int k = 87; k < 89; k++)
		{
			addElements(panel,k);
		}
		panel.add(new JLabel("**", JLabel.CENTER));
		for(int k = 104; k < 113; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,29);
		panel.add(new JLabel("*", JLabel.CENTER));
		for(int k = 57; k < 72; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,4);
		panel.add(new JLabel("**", JLabel.CENTER));
		for(int k = 89; k < 104; k++)
		{
			addElements(panel,k);
		}
		addBlanks(panel,21);
		
		panel2.add(panel, constraints);
		
		SChemPCore.progress.report("Loading Periodic Table Graphics");
		
		String[] options = {"None", "Atomic Number", "Atomic Mass", "Atomic Volume", "Boiling Point", "Melting Point",
			"Specific Heat Capacity", "Density", "Electronegativity", "Atomic Radius", "Ionization Potential",
			"Thermal Conductivity", "Heat of Fusion", "Heat of Vaporization", "Covalent Radius"};
		JComboBox sortOptions = new JComboBox(options);
		sortOptions.setPreferredSize(new Dimension(150, 30));
		
		ScalePanel sortColors = new ScalePanel();
		SChemPTableSortingListener listener = new SChemPTableSortingListener(this, sortColors);
		sortOptions.addActionListener(listener);
		JPanel panelSouth = new JPanel();
		panelSouth.add(new JLabel("Sort: "));
		panelSouth.add(sortOptions);
		panelSouth.add(new JLabel("          "));
		
		sortColors.add(new JLabel("                                           " +
				"                 "));
		sortColors.setSize(300,100);
		panelSouth.add(sortColors);
		
		JCheckBox logCheck = new JCheckBox("Logarithmically Transform");
		logCheck.addActionListener(listener.new LogListener());
		panelSouth.add(logCheck);
		panel2.add(panelSouth, BorderLayout.SOUTH);
		
		SChemPCore.progress.report("Loading Panels");
	}
	
	private static void addBlanks(JPanel panel, int k)
	{
		for(int j = 0; j < k; j++)
		{
			panel.add(new JLabel());
		}
	}
	
	private static void addElements(JPanel panel, int k)
	{
		JButton c = new JButton();
		buttonList.set(k-1, c);
		try{
			c.setActionCommand(PeriodicTable.getElement(k,"ATOMIC_NUMBER").name);
			c.addActionListener(new SChemPTableListener(new JFrame()));
			Dimension d = c.getSize();
			c.setIcon(PeriodicTable.getElement(k,"ATOMIC_NUMBER").getIcon(d.width,d.height));
			panel.add(c);
		}
		catch(SChemPException e){};
	}
	
	public void setColorSort(String property, boolean logarithmic) throws SChemPException
	{
		Element[] elementList = new Element[buttonList.size()];
		double[] values = new double[buttonList.size()];
		double min = 0;
		double max = 0;
		boolean clear = false;
		Element e = null;
		
		if(property.equals("None"))
		{
			min = 0;
			max = 1;
			
			for(int k = 0; k < buttonList.size(); k++)
			{
				e = PeriodicTable.getElement((k+1), "ATOMIC_NUMBER");
				elementList[k] = e;
				values[k] = 0;
			}
			clear = true;
		}
		else
		{
			if(!PeriodicTable.isAcceptableProperty(property))
				throw new SChemPException("No Such Property: " + property);
		
			if(logarithmic)
			{
				min = ((Number)PeriodicTable.getElement(1, "ATOMIC_NUMBER").getProperty(property)).doubleValue();
				min = log10(min+1)/log10(min+2);
			
				for(int k = 0; k < buttonList.size(); k++)
				{
					e = PeriodicTable.getElement((k+1), "ATOMIC_NUMBER");
					elementList[k] = e;
					values[k] = ((Number)e.getProperty(property)).doubleValue();
					values[k] = log10(1+values[k])/log10(2+values[k]);
					if(values[k] < min && values[k] >= 0)
						min = values[k];
					else if(values[k] > max)
						max = values[k];
				}
			}
			else
			{
				min = ((Number)PeriodicTable.getElement(1, "ATOMIC_NUMBER").getProperty(property)).doubleValue();
				
				for(int k = 0; k < buttonList.size(); k++)
				{
					e = PeriodicTable.getElement((k+1), "ATOMIC_NUMBER");
					elementList[k] = e;
					values[k] = ((Number)e.getProperty(property)).doubleValue();
					//System.out.println(e + " " + values[k]);
					if(values[k] < min && values[k] >= 0)
						min = values[k];
					else if(values[k] > max)
						max = values[k];
				}
			}
		}
		max -= min;
		for(int k = 0; k < buttonList.size(); k++)
		{
			//double temp = values[k];
			values[k] -= min;
			values[k] /= max;
			
			JButton button = (JButton)buttonList.get(k);
			
			if(clear)
			{
				button.setIcon(elementList[k].getColoredIcon(
					button.getWidth(), button.getHeight(), 
						new Color(0,0,0,0)));
			}
			else if(values[k] < 0 || Double.isNaN(values[k]))
			{
				button.setIcon(
						elementList[k].getCharacterIcon(button.getWidth(), button.getHeight(), '?'));
			}
			else
			{//System.out.println("Values: " + values[k]);
				button.setIcon(elementList[k].getColoredIcon(
					button.getWidth(), button.getHeight(), 
						//new Color(255,0,0,(int)(values[k]*255))));
						new Color((int)(values[k]*255),0,(int)(255-values[k]*255),(int)(127+Math.abs((values[k]-.5)*256)))));
			}
		}
	}
}
