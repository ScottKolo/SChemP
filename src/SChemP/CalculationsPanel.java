package SChemP;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @author Scott Kolodziej
 *
 */
public class CalculationsPanel extends JPanel
{
	// Engineering: columns, "What do you know," "What do you need to know?"
	// Science: Single calculations by category, visuals... PV/T = PV/T
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2362520853756680245L;
	CalculationPanel idealGasPanel;
	CalculationPanel combinedGasPanel;
	CalculationPanel nernstPanel;
	CalculationPanel grandDaddyPanel;
	CalculationPanelListener listener;
	
	public CalculationsPanel()
	{
		super();
		this.setLayout(new BorderLayout());
		
		listener = new CalculationPanelListener();
		
		JTabbedPane tabPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		this.add(tabPane, BorderLayout.CENTER);
		
		idealGasPanel = createIdealGasPanel();
		JPanel frameIGP = new JPanel();
		frameIGP.add(idealGasPanel);
		tabPane.add(frameIGP, "Ideal Gas Law");
		
		combinedGasPanel = createCombinedGasPanel();
		JPanel frameCGP = new JPanel();
		frameCGP.add(combinedGasPanel);
		tabPane.add(frameCGP, "Combined Gas Law");
		
		/*
		nernstPanel = createNernstPanel();
		JPanel frameNernst = new JPanel();
		frameNernst.add(nernstPanel);
		tabPane.add(frameNernst, "Nernst Equation");
		*/
		
		grandDaddyPanel = createGrandDaddyPanel();
		JPanel frameGDP = new JPanel();
		frameGDP.add(grandDaddyPanel);
		tabPane.add(frameGDP, "Grand Daddy");
	}
	
	private CalculationPanel createIdealGasPanel()
	{
		//igp.setLayout(new BoxLayout(igp, BoxLayout.X_AXIS));
		//igp.add(Box.createGlue());
		CalculationPanel igp = new CalculationPanel();
		
		igp.setLayout(new GridLayout(2,6));
		JButton pButton = new JButton("Pressure (P)");
		pButton.setActionCommand("IGP-P");
		pButton.addActionListener(listener);
		JButton vButton = new JButton("Volume (V)");
		vButton.setActionCommand("IGP-V");
		vButton.addActionListener(listener);
		JButton nButton = new JButton("Moles (n)");
		nButton.setActionCommand("IGP-N");
		nButton.addActionListener(listener);
		JLabel rLabel = new JLabel("Ideal Gas Constant", SwingConstants.CENTER);
		JButton tButton = new JButton("Temperature (T)");
		tButton.setActionCommand("IGP-T");
		tButton.addActionListener(listener);
		JTextField pField = new JTextField();
		JTextField vField = new JTextField();
		JTextField nField = new JTextField();
		JLabel rField = new JLabel("R", SwingConstants.CENTER);
		JTextField tField = new JTextField();
		
		igp.add(pField);
		igp.add(vField);
		igp.add(new JLabel(" = ", SwingConstants.CENTER));
		igp.add(nField);
		igp.add(rField);
		igp.add(tField);
		igp.add(pButton);
		igp.add(vButton);
		igp.add(new JLabel());
		igp.add(nButton);
		igp.add(rLabel);
		igp.add(tButton);
		
		return igp;
		
		/*
		JButton calculate = new JButton("Calculate");
		for(int k = 0; k < 2; k++)
			igp.add(new JLabel());
		igp.add(calculate);
		for(int k = 0; k < 3; k++)
			igp.add(new JLabel());
		*/
		//igp2.add(igp);
		//igp.setFont(font.deriveFont((float)46));
		//igp.add(displayLabel);//, SwingConstants.CENTER);
		//igp.add(Box.createGlue());
	}
	
	private CalculationPanel createCombinedGasPanel()
	{
		//igp.setLayout(new BoxLayout(igp, BoxLayout.X_AXIS));
		//igp.add(Box.createGlue());
		CalculationPanel cgp = new CalculationPanel();
		
		cgp.setLayout(new GridLayout(5,5));
		JButton p1Button = new JButton("Pressure (P1)");
		p1Button.setActionCommand("CGP-P1");
		p1Button.addActionListener(listener);
		JButton v1Button = new JButton("Volume (V1)");
		v1Button.setActionCommand("CGP-V1");
		v1Button.addActionListener(listener);
		JButton t1Button = new JButton("Temperature (T1)");
		t1Button.setActionCommand("CGP-T1");
		t1Button.addActionListener(listener);
		JButton p2Button = new JButton("Pressure (P2)");
		p2Button.setActionCommand("CGP-P2");
		p2Button.addActionListener(listener);
		JButton v2Button = new JButton("Volume (V2)");
		v2Button.setActionCommand("CGP-V2");
		v2Button.addActionListener(listener);
		JButton t2Button = new JButton("Temperature (T2)");
		t2Button.setActionCommand("CGP-T2");
		t2Button.addActionListener(listener);
		JTextField p1Field = new JTextField();
		JTextField v1Field = new JTextField();
		JTextField t1Field = new JTextField();
		JTextField p2Field = new JTextField();
		JTextField v2Field = new JTextField();
		JTextField t2Field = new JTextField();
		
		// Row 1
		cgp.add(p1Button);
		cgp.add(v1Button);
		cgp.add(new JLabel());
		cgp.add(p2Button);
		cgp.add(v2Button);
		
		// Row 2
		cgp.add(p1Field);
		cgp.add(v1Field);
		cgp.add(new JLabel());
		cgp.add(p2Field);
		cgp.add(v2Field);
		
		// Row 3
		cgp.add(new JLabel("----", SwingConstants.CENTER));
		cgp.add(new JLabel("----", SwingConstants.CENTER));
		cgp.add(new JLabel(" = ", SwingConstants.CENTER));
		cgp.add(new JLabel("----", SwingConstants.CENTER));
		cgp.add(new JLabel("----", SwingConstants.CENTER));
		
		// Row 4
		cgp.add(new JLabel());
		cgp.add(t1Field);
		cgp.add(new JLabel());
		cgp.add(t2Field);
		cgp.add(new JLabel());
		
		// Row 5
		cgp.add(new JLabel());
		cgp.add(t1Button);
		cgp.add(new JLabel());
		cgp.add(t2Button);
		cgp.add(new JLabel());
				
		return cgp;
	}
	
	private CalculationPanel createGrandDaddyPanel()
	{
		//igp.setLayout(new BoxLayout(igp, BoxLayout.X_AXIS));
		//igp.add(Box.createGlue());
		CalculationPanel gdp = new CalculationPanel();
		
		gdp.setLayout(new GridLayout(2,6));
		JButton gButton = new JButton("Gibbs Free Energy (G)");
		gButton.setActionCommand("GDP-G");
		gButton.addActionListener(listener);
		JButton hButton = new JButton("Enthalpy (H)");
		hButton.setActionCommand("GDP-H");
		hButton.addActionListener(listener);
		JButton tButton = new JButton("Temperature (T)");
		tButton.setActionCommand("GDP-T");
		tButton.addActionListener(listener);
		JButton sButton = new JButton("Entropy (S)");
		sButton.setActionCommand("GDP-S");
		sButton.addActionListener(listener);
		JTextField gField = new JTextField();
		JTextField hField = new JTextField();
		JTextField tField = new JTextField();
		JTextField sField = new JTextField();
		
		gdp.add(gField);
		gdp.add(new JLabel(" = ", SwingConstants.CENTER));
		gdp.add(hField);
		gdp.add(new JLabel(" - ", SwingConstants.CENTER));
		gdp.add(tField);
		gdp.add(sField);
		gdp.add(gButton);
		gdp.add(new JLabel());
		gdp.add(hButton);
		gdp.add(new JLabel());
		gdp.add(tButton);
		gdp.add(sButton);
		
		return gdp;
	}
	
	/*
	private CalculationPanel createNernstPanel()
	{
		//igp.setLayout(new BoxLayout(igp, BoxLayout.X_AXIS));
		//igp.add(Box.createGlue());
		CalculationPanel np = new CalculationPanel();
		
		np.setLayout(new GridLayout(2,6));
		JButton gButton = new JButton("Gibbs Free Energy (G)");
		gButton.setActionCommand("NER-G");
		gButton.addActionListener(listener);
		JButton nButton = new JButton("Moles (n)");
		nButton.setActionCommand("NER-N");
		nButton.addActionListener(listener);
		JButton eButton = new JButton("Cell Potential (E)");
		eButton.setActionCommand("NER-E");
		eButton.addActionListener(listener);
		JTextField gField = new JTextField();
		JTextField nField = new JTextField();
		JTextField eField = new JTextField();
		
		np.add(gField);
		np.add(new JLabel(" = ", SwingConstants.CENTER));
		np.add(new JLabel(" - ", SwingConstants.CENTER));
		np.add(nField);
		np.add(new JLabel(" F ", SwingConstants.CENTER));
		np.add(eField);
		np.add(gButton);
		np.add(new JLabel());
		np.add(new JLabel());
		np.add(nButton);
		np.add(new JLabel("Faraday's Constant", SwingConstants.CENTER));
		np.add(eButton);
		
		return np;
	}
	*/
	
	class CalculationPanelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String c = e.getActionCommand();
			if(c.substring(0,3).equals("IGP"))
			{
				Double[] data = idealGasPanel.getData();
				
				double result = Calculations.idealGasLaw(data[0], data[1], data[2], data[3]);
				
				if(c.equals("IGP-P"))
				{
					idealGasPanel.setData(result, 0);
				}
				else if(c.equals("IGP-V"))
				{
					idealGasPanel.setData(result, 1);
				}
				else if(c.equals("IGP-N"))
				{
					idealGasPanel.setData(result, 2);
				}
				else if(c.equals("IGP-T"))
				{
					idealGasPanel.setData(result, 3);
				}
			}
			else if(c.substring(0,3).equals("CGP"))
			{
				Double[] data = combinedGasPanel.getData();
				
				double result = Calculations.combinedGasLaw(data[0], data[1], data[4], data[2], data[3], data[5]);
				
				if(c.equals("CGP-P1"))
				{
					combinedGasPanel.setData(result, 0);
				}
				else if(c.equals("CGP-V1"))
				{
					combinedGasPanel.setData(result, 1);
				}
				else if(c.equals("CGP-T1"))
				{
					combinedGasPanel.setData(result, 4);
				}
				else if(c.equals("CGP-P2"))
				{
					combinedGasPanel.setData(result, 2);
				}
				else if(c.equals("CGP-V2"))
				{
					combinedGasPanel.setData(result, 3);
				}
				else if(c.equals("CGP-T2"))
				{
					combinedGasPanel.setData(result, 5);
				}
			}
			else if(c.substring(0,3).equals("GDP"))
			{
				Double[] data = grandDaddyPanel.getData();
				
				double result = Calculations.grandDaddy(data[0], data[1], data[2], data[3]);
				
				if(c.equals("GDP-G"))
				{
					grandDaddyPanel.setData(result, 0);
				}
				else if(c.equals("GDP-H"))
				{
					grandDaddyPanel.setData(result, 1);
				}
				else if(c.equals("GDP-T"))
				{
					grandDaddyPanel.setData(result, 2);
				}
				else if(c.equals("GDP-S"))
				{
					grandDaddyPanel.setData(result, 3);
				}
			}
			else if(c.substring(0,3).equals("NER"))
			{
				Double[] data = nernstPanel.getData();
				
				double result = Calculations.nernst(data[0], data[1], data[2]);
				
				if(c.equals("NER-G"))
				{
					nernstPanel.setData(result, 0);
				}
				else if(c.equals("NER-N"))
				{
					nernstPanel.setData(result, 1);
				}
				else if(c.equals("NER-E"))
				{
					nernstPanel.setData(result, 2);
				}
			}
		}
	}
}