package SChemP;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class ElementIcon implements Icon
{
	private int width;
	private int height;
	private String name;
	private String symbol;
	private int atomicNumber;
	private double atomicMass;
	private Color color;
	private char character;
	boolean charMode;
	
	public ElementIcon()
	{
	}
	
	public ElementIcon(int w, int h, String n, String s, int an, double am)
	{
		width = w;
		height = h;
		name = n;
		symbol = s;
		atomicNumber = an;
		atomicMass = am;
		charMode = false;
	}
	
	public ElementIcon(int w, int h, String n, String s, int an, double am, Color color)
	{
		width = w;
		height = h;
		name = n;
		symbol = s;
		atomicNumber = an;
		atomicMass = am;
		this.color = color;
		charMode = false;
	}
	
	public ElementIcon(int w, int h, String n, String s, int an, double am, char c)
	{
		width = w;
		height = h;
		name = n;
		symbol = s;
		atomicNumber = an;
		atomicMass = am;
		character = c;
		charMode = true;
	}
	
	public int getIconHeight()
	{
		return height;
	}
	public int getIconWidth()
	{
		return width;
	}
	
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		int cellWidth = c.getBounds().width;
		int cellHeight = c.getBounds().height;
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
		        RenderingHints.VALUE_ANTIALIAS_ON);
		Font f = g2.getFont();
		Rectangle2D bounds;
		
		if(color != null)
		{
			g2.setColor(color);
			g2.fillRect(4,4,cellWidth-8, cellHeight-8);
		}
		if(charMode)
		{
			bounds = (g2.getFont()).getStringBounds(("" + character),g2.getFontRenderContext());
			g2.setColor(new Color(0,0,0,80));
			g2.setFont(f.deriveFont((float)cellWidth));
			g2.drawString("" + character, (float)(cellWidth/2)-(float)(bounds.getWidth()/2.0),(float)(cellHeight/2)+(f.getSize()));
		}
		g2.setColor(Color.BLACK);
		
		g2.setFont(f.deriveFont((float)(cellWidth/3)));
		bounds = (g2.getFont()).getStringBounds(symbol,g2.getFontRenderContext());
		g2.drawString(symbol,(float)(cellWidth/2)-(float)(bounds.getWidth()/2.0),(float)(cellHeight/2)+(f.getSize()/3));
		g2.setFont(f.deriveFont((float)((float)cellWidth/6.3)));
		bounds = (g2.getFont()).getStringBounds((new Double(atomicMass)).toString(),g2.getFontRenderContext());
		g2.drawString((new Double(atomicMass)).toString(),(cellWidth/2)-(float)(bounds.getWidth()/2.0),(int)(1.8*cellHeight/2));
		
		if(SChemPCore.frame.getSize().width * SChemPCore.frame.getSize().height > 700000)
		{
			bounds = (g2.getFont()).getStringBounds(name,g2.getFontRenderContext());
			g2.drawString(name,(cellWidth/2)-(float)(bounds.getWidth()/2.0),(int)(1.4*cellHeight/2));
		}
		
		g2.setFont(f.deriveFont((float)(cellWidth/4)));
		bounds = (g2.getFont()).getStringBounds(name,g2.getFontRenderContext());
		g2.drawString((new Integer(atomicNumber)).toString(),4,2+(int)bounds.getHeight());
	}
}