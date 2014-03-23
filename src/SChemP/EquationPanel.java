package SChemP; 

import java.text.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

/**
 * A JPanel that displays a chemical equation balancer.
 * 
 * @author Scott Kolodziej
 *
 */
public class EquationPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2521723345445328424L;
	private JTextField field;
	private JPanel displayPanel;
	private SmoothLabel displayLabel;
	
	public EquationPanel()
	{
		super();
		
		//String[] temp1 = {"Enter an Unbalanced Equation to Begin"};
		//String[][] temp = new String[1][1];
		//for(int r = 0; r < 5; r++)
		//	for(int c = 0; c < 5; c++)
		//		temp[r][c] = "Element";
		
		//equationTable = new JTable(temp, temp1);
		//equationTable.setColumnSelectionAllowed(true);
		
		drawPanel(this);
	}
	
	public void drawPanel(JPanel panel)
	{
		panel.setLayout(new BorderLayout());
		field = new JTextField(30);
		JButton balanceButton = new JButton("Balance");
		balanceButton.addActionListener(new BalanceListener(this));
		JPanel balancePanel = new JPanel();
		balancePanel.add(field);
		balancePanel.add(balanceButton);
		panel.add(balancePanel, BorderLayout.NORTH);
		
		displayPanel = new JPanel();
		//displayPanel.setLayout(new GridLayout(1,1));
		
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.X_AXIS));
		displayPanel.add(Box.createGlue(), SwingConstants.CENTER);
		displayLabel = new SmoothLabel("Please Enter a Chemical Equation.");
		Font font = this.getFont();
		displayLabel.setFont(font.deriveFont((float)46));
		displayPanel.add(displayLabel);//, SwingConstants.CENTER);
		displayPanel.add(Box.createGlue());
		
		//displayPanel.add(panel, BorderLayout.CENTER);
		
		panel.add(displayPanel, BorderLayout.CENTER);
	}
	
	public String getFieldText()
	{
		return field.getText();
	}
	
	public void setFieldText(String text)
	{
		field.setText(text);
	}
	
	public void setDisplayText(String text)
	{
		//displayLabel.setText(text);
		
		Graphics2D g2 = (Graphics2D)this.getGraphics();
		Rectangle2D stringBounds = (g2.getFont().deriveFont((float)46)).getStringBounds(text,g2.getFontRenderContext());
		Rectangle panelBounds = displayPanel.getVisibleRect();
		
		System.out.println(stringBounds.getWidth());
		System.out.println(panelBounds.getWidth());
		
		if(stringBounds.getWidth() <= panelBounds.getWidth())
		{
			displayLabel.setText(text);
		}
		else
		{
			wrapLabelText(displayLabel, text);
		}
		
		
		
		
		//displayPanel.add(Box.createGlue());
		//System.out.println(displayLabel.getBounds().getWidth() + " " + SChemP.bounds.getWidth());
	}
	
	private void wrapLabelText(JLabel label, String text) {
		FontMetrics fm = label.getFontMetrics(label.getFont());
		Container container = label.getParent();
		int containerWidth = container.getWidth();

		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);

		StringBuffer trial = new StringBuffer();
		StringBuffer real = new StringBuffer("<html><center>");

		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE;
			start = end, end = boundary.next()) {
			String word = text.substring(start,end);
			trial.append(word);
			int trialWidth = SwingUtilities.computeStringWidth(fm,
				trial.toString());
			if (trialWidth > containerWidth) {
				trial = new StringBuffer(word);
				real.append("<br>");
			}
			real.append(word);
		}

		real.append("</center></html>");
		System.out.println(real.toString());
		label.setText(real.toString());
		//label = new SmoothLabel(real.toString());
	}
	
	/*
	public void setTableData(Compoundable[] compounds)
	{
		String[][] temp = new String[5][compounds.length];
		for(int r = 0; r < 5; r++)
			for(int c = 0; c < compounds.length; c++)
				temp[r][c] = "Element";
		
		String[] columns = new String[compounds.length];
		for(int k = 0; k < compounds.length; k++)
			columns[k] = compounds[k].toUnicodeString();
		
		equationTable = new EquationTable(temp, columns);//compounds);
		
		tablePanel.removeAll();
		
		tablePanel.setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(equationTable);
		equationTable.setPreferredScrollableViewportSize(new Dimension(800, 500));
		tablePanel.add(equationTable.getTableHeader(), BorderLayout.PAGE_START);
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		
		//tablePanel.add(equationTable, BorderLayout.CENTER);
		tablePanel.validate();
		tablePanel.repaint();
		//this.add(equationTable, BorderLayout.SOUTH);
		this.validate();
		this.repaint();
	}
	*/
	
	class BalanceListener implements ActionListener {

		EquationPanel panel;
		
		public BalanceListener(EquationPanel panel)
		{
			this.panel = panel;
		}
		
		public void actionPerformed(ActionEvent event) 
		{
			ChemicalEquation equation = new ChemicalEquation(panel.getFieldText());
			setDisplayText(equation.toUnicodeString());
			
			//panel.setTableData(equation.getElements());
		}
	}
	
	class SmoothLabel extends JLabel
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 8625233443526051409L;

		public SmoothLabel() {}
	 
	    public SmoothLabel(String text)
	    {
	        super(text);
	    }
	 
	    public void paintComponent(Graphics g)
	    {
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        super.paintComponent(g);
	    }
	}
}