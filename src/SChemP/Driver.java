package SChemP;

import javax.swing.JFrame;

public class Driver
{
	public static void main(String[] args) throws Exception
	{
		new SChemPCore(new JFrame("SChemP v1.0"));
		//System.out.println(Calculations.Avagadro);
		//System.out.println(Calculations.idealGasLaw(1.0,null,1.0,273.0));
		/*
		JFrame test = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,8));
		
		for(int k = 0; k < 9; k++)
		{
			panel.add(new JLabel(""));
		}
		
		int w = 400/8;
		int h = 200/3;
		
		panel.add(new JButton(new ElementIcon(w,h,"","S",1,PeriodicTable.getElement(1,"Atomic Number").atomicMass)));
		panel.add(new JButton(new ElementIcon(w,h,"","C",2,PeriodicTable.getElement(2,"Atomic Number").atomicMass)));
		panel.add(new JButton(new ElementIcon(w,h,"","h",3,PeriodicTable.getElement(3,"Atomic Number").atomicMass)));
		panel.add(new JButton(new ElementIcon(w,h,"","e",4,PeriodicTable.getElement(4,"Atomic Number").atomicMass)));
		panel.add(new JButton(new ElementIcon(w,h,"","m",5,PeriodicTable.getElement(5,"Atomic Number").atomicMass)));
		panel.add(new JButton(new ElementIcon(w,h,"","P",6,PeriodicTable.getElement(6,"Atomic Number").atomicMass)));
		
		for(int k = 0; k < 9; k++)
		{
			panel.add(new JLabel(""));
		}
		
		test.setContentPane(panel);
		test.setSize(400,200);
		test.setVisible(true);
		*/
	}
}