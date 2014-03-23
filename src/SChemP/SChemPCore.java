package SChemP;

import java.awt.Container;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class SChemPCore
{
	public static Container frame;
	public static String systemLAFName;
	public static ProgressWindow progress;
	private static final boolean debug = false;
	public static Rectangle bounds;
	
	static
	{
		GraphicsConfiguration gc = (new JFrame()).getGraphicsConfiguration();
		bounds = gc.getBounds();
	}
	
	public SChemPCore(JFrame f)
	{
		frame = f;
		frame.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent e) {
				JFrame tmp = (JFrame)e.getSource();
				if (tmp.getWidth()<600 || tmp.getHeight()<400)
				{
					tmp.setSize(600, 400);
				}
			}
		});
		
		progress = new ProgressWindow("Loading...");
		//progress.setSize(230,80);
		progress.setVisible(true);
		progress.report("Initializing SChemP...");
		
		((JFrame)frame).setJMenuBar(setUpMenu());
		
		/*
		// Setting up the Periodic Table JPanel
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		
		// Setting up the Equation Balancer JPanel
		JPanel balancerPanel = new JPanel();
		*/
		
		progress.report();
		
		// Setting up the JTabbedPane
		JTabbedPane tabbers = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbers.addTab("Periodic Table", new PeriodicTablePanel());
		tabbers.addTab("Equation", new EquationPanel());
		//tabbers.addTab("Calculations", new CalculationsPanel());
		tabbers.addTab("Quantum Orbitals", new QuantumOrbitalsPanel());
		
		((JFrame)frame).setContentPane(tabbers);
		if(f instanceof JFrame)
			((JFrame)frame).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		((JFrame)frame).setExtendedState(Frame.MAXIMIZED_BOTH);
		
		/*
		tablePanel.setSize(800,600);
		balancerPanel.setSize(800,600);
		*/
		frame.setSize(800,600);
		
		frame.setLocation(bounds.width/2-400,bounds.height/2-300);
		
		/*
		// Filling the JPanels with their content
		GraphicalPeriodicTable.drawTable(tablePanel, BorderLayout.CENTER);
		EquationBalancer.drawBalancer(balancerPanel);
		*/
		
		progress.finish();
		frame.setVisible(true);
	}
	
	public SChemPCore(JApplet f)
	{
		frame = f;
		
		//progress = new ProgressWindow("Loading...");
		//progress.setVisible(true);
		//progress.report("Initializing SChemP...");
		
		//((JApplet)frame).setJMenuBar(setUpMenu());
		
		/*
		// Setting up the Periodic Table JPanel
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		
		// Setting up the Equation Balancer JPanel
		JPanel balancerPanel = new JPanel();
		*/
		
		//progress.report();
		
		// Setting up the JTabbedPane
		JTabbedPane tabbers = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		//tabbers.addTab("Periodic Table", new PeriodicTablePanel());
		tabbers.addTab("Equation", new EquationPanel());
		//tabbers.addTab("Calculations", new CalculationsPanel());
		tabbers.addTab("Quantum Orbitals", new QuantumOrbitalsPanel());
		
		((JApplet)frame).setContentPane(tabbers);
		
		/*
		tablePanel.setSize(800,600);
		balancerPanel.setSize(800,600);
		*/
		//frame.setSize(800,600);
		
		//frame.setLocation(bounds.width/2-400,bounds.height/2-300);
		
		/*
		// Filling the JPanels with their content
		GraphicalPeriodicTable.drawTable(tablePanel, BorderLayout.CENTER);
		EquationBalancer.drawBalancer(balancerPanel);
		*/
		
		//progress.finish();
		//frame.setVisible(true);
	}
	
	private JMenuBar setUpMenu()
	{
		SChemPMenuListener listener = new SChemPMenuListener(frame);
		
		// New MenuBar
		JMenuBar menubar = new JMenuBar();
		
		// File Menu
		//JMenu file = new JMenu("File");
		
		// Edit Menu
		//JMenu edit = new JMenu("Edit");
		
		// Calculator Menu -- Seriously broken.
		/*
		JMenu calc = new JMenu("Calculator");
		JMenuItem newCalc = new JMenuItem("New Calculator", 0);
		newCalc.addActionListener(listener);
		calc.add(newCalc);
		*/
		
		// Look and Feel Menu
		/*JMenu lookAndFeel = new JMenu("Look and Feel");
		ButtonGroup LAFButtonGroup = new ButtonGroup();
		JRadioButtonMenuItem xPlatformLAF = new JRadioButtonMenuItem("Java", true);
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception ex){}
		systemLAFName = UIManager.getLookAndFeel().getID();
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception ex){}
		JRadioButtonMenuItem systemLAF = new JRadioButtonMenuItem(systemLAFName);
		LAFButtonGroup.add(xPlatformLAF);
		LAFButtonGroup.add(systemLAF);
		xPlatformLAF.addActionListener(listener);
		systemLAF.addActionListener(listener);
		lookAndFeel.add(xPlatformLAF);
		lookAndFeel.add(systemLAF);	
		*/
		// Help Menu
		/*JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About...", 0);
		about.addActionListener(listener);
		help.add(about);
		*/
		
		// Adding Menus to MenuBar
		//menubar.add(file);
		//menubar.add(edit);
		//menubar.add(calc);
		//menubar.add(lookAndFeel);
		//menubar.add(help);
		
		return menubar;
	}
	
	public static boolean debug()
	{
		return debug;
	}
}