package SChemP;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class SChemPTableListener implements ActionListener
{
	JFrame frame;
	
	public SChemPTableListener()
	{
	}
	
	public SChemPTableListener(JFrame f)
	{
		frame = f;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String name = e.getActionCommand();
		Element element = null;
		
		try{
			element = PeriodicTable.getElement(name, "NAME");
		}
		catch(SChemPException se){}
		
		String output = element.thoroughOutput();
		JTextArea textArea = new JTextArea(output, 16, 23);
		textArea.setEditable(false);
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		JScrollPane pane = new JScrollPane(textArea);
		pane.setMinimumSize(pane.getMaximumSize());
		
		ElectronPanel ep = new ElectronPanel(element);
		ep.setSize(300,260);
		ep.setMinimumSize(new Dimension(300,260));
		ep.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		//JRadioButton bohr = new JRadioButton("Bohr", true);
		
		// Choice of electron visuals -- Implement later.
		
		//JRadioButton cloud = new JRadioButton("Cloud", false);
		//JRadioButton orbitals = new JRadioButton("Quantum Orbitals", false);
		//ButtonGroup bGroup = new ButtonGroup();
		//bGroup.add(bohr);
		//bGroup.add(cloud);
		//bGroup.add(orbitals);
		//bohr.addActionListener(new ElectronListener(ep));
		//cloud.addActionListener(new ElectronListener(ep));
		//orbitals.addActionListener(new ElectronListener(ep));
		JPanel elecPanel = new JPanel(new BorderLayout());
		
		elecPanel.add(ep, BorderLayout.CENTER);
		//JPanel buttonPanel = new JPanel();
		//buttonPanel.add(bohr);
		//buttonPanel.add(cloud);
		//buttonPanel.add(orbitals);
		//elecPanel.add(buttonPanel, BorderLayout.NORTH);
		
		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, elecPanel, pane);
		
		panel.setDividerLocation(300);
		panel.setSize(100,100);
		
		frame.setTitle(element.name);
		frame.setContentPane(panel);
		frame.setLocation((int)(SChemPCore.frame.getX())+50, (int)(SChemPCore.frame.getY())+50);
		frame.setSize(630,300);
		panel.setSize(630,300);
		
		frame.setVisible(true);
	}
}