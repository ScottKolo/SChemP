package SChemP; 

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class CalculationPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4397041374893267285L;
	ArrayList<JTextField> fields;
	
	public CalculationPanel()
	{
		super();
		fields = new ArrayList<JTextField>();
	}
	
	public Double[] getData()
	{
		Component[] components = getComponents();
		ArrayList<Double> data = new ArrayList<Double>();
		
		for(int k = 0; k < components.length; k++)
		{
			//System.out.println(components[k]);
			if(components[k] instanceof JTextField)
			{
				fields.add((JTextField)components[k]);
				try{
					String temp = ((JTextField)components[k]).getText();
					if(temp == null || temp.length() == 0)
					{
						data.add(null);
					}
					else
					{
						data.add(new Double(Double.parseDouble(temp)));
					}
				}
				catch(NumberFormatException e){}
			}
		}
		
		//System.out.println(data.size());
		
		Double[] dataDouble = new Double[data.size()];
		for(int k = 0; k < data.size(); k++)
		{
			dataDouble[k] = data.get(k);
		}
		
		return dataDouble;
	}
	
	public void setData(Double data, int index)
	{
		fields.get(index).setText(""+data);
	}
}
