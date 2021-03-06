package SChemP;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

//import org.jdom2.JDOMException;
//import org.jdom2.input.SAXBuilder;
//import org.jdom2.Document;

public class ProgressWindow extends JFrame
{
	public static final long serialVersionUID = 43L;
	private JProgressBar progressBar;
	private int progress;
	private JPanel progressPanel;
	private JLabel label;
	private int loadingSteps = 6; // Eventually store this in a config file
	private double delta;
	//private Document doc;
	//private File config;
	
	public ProgressWindow(String title)
	{
		super(title);
		
		//doc = null;
		/*
		config = new File("config.xml");
		if(config.exists() && config.length() > 0)
		{
			try{
				try{
					SAXBuilder builder = new SAXBuilder();  // parameters control validation, etc
					doc = builder.build("config.xml");
				}
				catch(JDOMException jde){
					System.err.println(jde);
					throw new SChemPException("JDOM: Error Reading Configuration XML File");
					}
				catch(IOException ioe){
					System.err.println(ioe);
					throw new SChemPException("I/O: Error Reading Configuration XML File");
					}
			}
			catch(SChemPException se){}
		}
		else
		{
			try{
				config.createNewFile();
				BufferedWriter writer = new BufferedWriter(new FileWriter(config));
				writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				writer.newLine();
				writer.append("<LOADING_STEPS>1</LOADING_STEPS>");
				writer.flush();
				writer.close();
			}catch (IOException ioe){}
			
		}
		*/
		/*
		try{
		Iterator iter = doc.getDescendants(new ElementFilter("LOADING_STEPS"));
		org.jdom.Element element = (org.jdom.Element)iter.next();
		loadingSteps = Integer.parseInt(((org.jdom.Element)doc.getDescendants(new ElementFilter("LOADING_STEPS")).next()).getText());
		}
		catch (Exception e){}
		*/
		
		/*loadingSteps = 10;
		
		try{
			Scanner input = new Scanner(new File("config.ini"));
			try{
				loadingSteps = Integer.parseInt(input.next());
			}
			catch(Exception e){}
		}catch(IOException ioe)
		{
			loadingSteps = 10;
		}
		*/
		
		delta = (double)100/(double)loadingSteps;
		
		JPanel p = new JPanel();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		p1.add(new JLabel(new ImageIcon("img/splash.png")));
		
		progressBar = new JProgressBar(0,(int)delta*loadingSteps);
		
		loadingSteps = 0;
		
		progressPanel = new JPanel();
		progressPanel.setPreferredSize(new Dimension(300,30));
		progressPanel.setLayout(new GridLayout(2,1));
		progressPanel.add(progressBar);
		
		label = new JLabel("", SwingConstants.CENTER);
		progressPanel.add(label);
		
		p2.add(progressPanel);
		
		p.setLayout(new BorderLayout());
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		p.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		this.setContentPane(p);
		this.setSize(400,160);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setLocation(SChemPCore.bounds.width/2-this.getWidth()/2,SChemPCore.bounds.height/2-this.getHeight()/2);
		
		addMouseListener(new MouseAdapter()
	            {
	                public void mousePressed(MouseEvent e)
	                {
	                    setVisible(false);
	                    dispose();
	                }
	            });
	}
	
	public void report()
	{
		loadingSteps ++;
		progress += delta;
		if(progress > 100)
			progress = 100;
		progressBar.setValue(progress);
	}
	
	public void report(String s)
	{
		loadingSteps ++;
		progress += delta;
		if(progress > 100)
			progress = 100;
		progressBar.setValue(progress);
		label.setText(s);
	}
	
	public void finish()
	{
		/*File loadingStepsFile = new File("config.ini");
		loadingStepsFile.delete();
		
		try{
			loadingStepsFile.createNewFile();
			FileWriter writer = new FileWriter(loadingStepsFile);
			writer.write(new Integer(loadingSteps).toString());
			writer.flush();
		}catch(IOException ioe){}
		*/
		
		/*try{
			Iterator iter = doc.getDescendants(new ElementFilter("LOADING_STEPS"));
			org.jdom.Element element = (org.jdom.Element)iter.next();
			element.setText(""+loadingSteps);
			
			//File loadingStepsFile = new File("config.xml");
			if(config.canWrite())
			{
				FileWriter writer = new FileWriter(config);
				
				XMLOutputter out = new XMLOutputter(); // Causes error!
				out.output(doc, writer);
			}
		}
		catch (Exception e){System.err.println(e);}
		*/
		
		label.setText("Click to Continue");
	}
}