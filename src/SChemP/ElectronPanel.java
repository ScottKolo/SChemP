package SChemP;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author   Scott Kolodziej
 */
public class ElectronPanel extends JPanel
{
	private static final long serialVersionUID = 235;
	
	Element element;
	int state;
	ElementRenderer currentRenderer;
	
	public ElectronPanel()
	{
		super();
	}
	
	public ElectronPanel(Element e)
	{
		super(true);
		element = e;
		setVisualState(1);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		currentRenderer.draw(g2);
	}
	
	public void setVisualState(int s)
	{
		state = s;
		switch(s)
		{
			case 1: currentRenderer = new BohrRenderer(this, element);
		}
		
		this.repaint();
	}
}

/**
 * @author Scott Kolodziej
 */
class BohrRenderer implements ElementRenderer
{
	private JPanel panel;
	private Element element;
	private int xCenter;
	private int yCenter;
	float factor;
	ArrayList<GraphicalElectron> electrons;
	int level;
	BohrElectronRenderer ber;
	private boolean initialized;
	private boolean active;
	
	public BohrRenderer(JPanel panel, Element element)
	{
		active = true;
		initialized = false;
		factor = 0;
		this.panel = panel;
		this.panel.setLayout(new BorderLayout());
		
		this.element = element;
		
		JPanel optionPanel = new JPanel();
		JRadioButton stillButton = new JRadioButton("Still");
		JRadioButton moveButton = new JRadioButton("Animate", true);
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(moveButton);
		bGroup.add(stillButton);
		BohrRendererListener brl = new BohrRendererListener(this);
		moveButton.addActionListener(brl);
		stillButton.addActionListener(brl);
		optionPanel.add(moveButton);
		optionPanel.add(stillButton);
		this.panel.add(optionPanel, BorderLayout.SOUTH);
	}
	
	public void draw(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		xCenter = panel.getWidth()/2;
		yCenter = panel.getHeight()/2;
		
		if(!initialized)
		{
			GraphicalElectron.reset();
			String config = element.getFullElectronConfiguration();
			electrons = new ArrayList<GraphicalElectron>();
			
			// token holds the separated electron configuration
			// (Each token is in the format of "1s2")
			StringTokenizer token = new StringTokenizer(config);
			//int totalElectrons = 0;
			level = 0;
			
			int[] numPerLevel = new int[token.countTokens()];
			
			int[][] electronData = new int[token.countTokens()][3];
			int count = 0;
			
			while(token.hasMoreTokens())
			{
				// Parsing (Energy Level, Orbital, Number of Electrons), i.e. "3p4"
				StringTokenizer token2 = new StringTokenizer(token.nextToken(), "spdf", true);
				level = Integer.parseInt(token2.nextToken());
				String orb = token2.nextToken();
				int numE = Integer.parseInt(token2.nextToken());
				
				electronData[count][0] = level;
				electronData[count][1] = Electron.lValue(orb);
				electronData[count][2] = numE;
				count++;
				
				//totalElectrons += numE;
				//System.out.println(level-1 + " e: " + numE);
				numPerLevel[level-1] += numE;
				
				//for(int j = 0; j < numPerLevel.length; j++)
					//System.out.println("EnergyLevel: " + j + " " + numPerLevel[j]);
			}
			
			for(int k = 0; k < electronData.length; k++)
			{
				for(int c = 0; c < electronData[k][2]; c++)
				{
					GraphicalElectron e = new GraphicalElectron(xCenter, yCenter, 
							electronData[k][0], electronData[k][1], c, 
							numPerLevel[electronData[k][0]-1]);
					electrons.add(e);
				}
			}
			
			ber = new BohrElectronRenderer(panel);
			ber.start();
			initialized = true;
		}
		if(panel.getHeight() != ((GraphicalElectron)electrons.get(0)).getyCenter()*2)
		{
			xCenter = panel.getWidth()/2;
			yCenter = panel.getHeight()/2;
			
			Iterator<GraphicalElectron> iter = electrons.iterator();
			while(iter.hasNext())
			{
				((GraphicalElectron)iter.next()).setCenter(xCenter, yCenter);
			}
		}
		g2.setColor(Color.BLACK);
		
		g2.fillOval(xCenter-1,yCenter-1,3,3);
		for(int k = 0; k < level; k++)
			g2.drawOval(xCenter-((k+1)*30)/2, yCenter-((k+1)*30)/2, (k+1)*30, (k+1)*30);
			
		Iterator<GraphicalElectron> iter = electrons.iterator();
		
		if(active)
			factor += 0.1;
		
		while(iter.hasNext())
		{
			GraphicalElectron e = (GraphicalElectron)iter.next();
			e.step(factor);
			g2.setColor(Color.BLACK);
			g2.drawOval((int)(e.point.x-3),(int)(e.point.y-3),6,6);
			g2.setColor(Color.YELLOW);
			g2.fillOval((int)e.point.x-3,(int)e.point.y-3,6,6);
		}
	}
	
	public void setRenderMode(String mode)
	{
		if(mode.equals("Animate"))
		{
			active = true;
			ber.startThread();
		}
		else if(mode.equals("Still"))
		{
			active = false;
			factor = 0;
			ber.stopThread();
			panel.repaint();
		}
	}
	
	public class BohrRendererListener implements ActionListener
	{
		BohrRenderer renderer;
		
		public BohrRendererListener(BohrRenderer br)
		{
			renderer = br;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			renderer.setRenderMode(command);
		}
	}
	
	class BohrElectronRenderer extends Thread
	{
		JPanel panel;
		int delay;
		Thread alive;
		
		public BohrElectronRenderer(JPanel panel)
		{
			this.panel = panel;
			delay = 50;
			alive = new Thread(this);
			alive.start();
		}
		
		public void run()
		{
			if(panel.getTopLevelAncestor().isShowing())
			{
				Thread myThread = Thread.currentThread();
	    		while (alive == myThread)
	    		{
	    			panel.repaint();
					try{
						sleep(delay);
					}catch(InterruptedException e){}
	    		}
			}
		}
		
		public void stopThread()
		{
			alive = null;
		}
		
		public final void startThread()
		{
			alive = new Thread(this);
			alive.start();
		}
		
		/**
		 * @param delay   The delay to set.
		 * @uml.property   name="delay"
		 */
		public void setDelay(int d)
		{
			delay = d;
		}
	}
}
