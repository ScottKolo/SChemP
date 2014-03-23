package SChemP;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import javax.swing.JPanel;

/**
 * @author Scott Kolodziej
 *
 */
public class ScalePanel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7080859353354850438L;
	double low, high;
	Element lowElement, highElement;
	String property;
	
	public ScalePanel()
	{
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
				
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int k = 0; k < PeriodicTable.getSize(); k++)
		{
			g2.setColor(new Color((int)((double)k/(double)PeriodicTable.getSize()*255),0,(int)((1.0-(double)k/(double)PeriodicTable.getSize())*255),(int)(127+Math.abs(((double)k/(double)PeriodicTable.getSize()-.5)*256))));
			g2.drawLine(k+20, 10, k+20, 70);
		}
		
		g2.setColor(Color.BLACK);
		
		DecimalFormat format = new DecimalFormat("####0.##");
		String temp = "";
		
		if(lowElement != null)
			temp = lowElement.getPropertyWithUnitsAndFormatted(property, format) + " (" + lowElement + ")";
		
		Rectangle2D bounds = (g2.getFont()).getStringBounds(temp,g2.getFontRenderContext());
		g2.setFont(g2.getFont().deriveFont((float)10));
		g2.drawString(temp,0,10);
		
		if(highElement != null)
			temp = highElement.getPropertyWithUnitsAndFormatted(property, format) + " (" + highElement + ")";
		
		bounds = (g2.getFont()).getStringBounds(temp,g2.getFontRenderContext());
		g2.drawString(temp, (int)(PeriodicTable.getSize()-bounds.getWidth()+40), 10);
	}
	
	public void setProperty(String property) throws SChemPException
	{
		this.property = property;
		
		lowElement = PeriodicTable.getLowElement(property);
		highElement = PeriodicTable.getHighElement(property);
	}
}
