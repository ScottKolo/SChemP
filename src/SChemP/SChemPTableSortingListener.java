package SChemP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class SChemPTableSortingListener implements ActionListener
{
	private ScalePanel scale;
	private PeriodicTablePanel table;
	private boolean log;
	private String property;
	
	public SChemPTableSortingListener(PeriodicTablePanel table, ScalePanel scale)
	{
		this.table = table;
		this.scale = scale;
		log = false;
		property = "None";
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if (src instanceof JComboBox<?>) {
			JComboBox<?> cb = (JComboBox<?>)src;
	        property = (String)cb.getSelectedItem();
	        try{
	        	table.setColorSort(property, log);
	    		scale.setProperty(property);
	        }
			catch(SChemPException se){}
		}
	}
	
	public void refresh()
	{
        try{
        	table.setColorSort(property, log);
    		scale.setProperty(property);
        }
		catch(SChemPException se){}
	}
	
	class LogListener implements ActionListener
	{
		public LogListener()
		{
			
		}
		
		public void actionPerformed(ActionEvent e)
		{
			log = !log;
			refresh();
		}
	}
}