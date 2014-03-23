package SChemP;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author   Scott Kolodziej
 */
public class Calculator
{
	private Vector<Object> infixExp;
	private Stack<String> opStack;
	private static HashMap<String, Integer> precedence;
	private static HashMap<String, Integer> numOperands;
	private JPanel panel;
	public JTextArea ioField;
	private int cursorPos;
	private double lastResult;
	
	static
	{
		precedence = new HashMap<String, Integer>();
		precedence.put("+", new Integer(1));
		precedence.put("-", new Integer(1));
		precedence.put("*", new Integer(2));
		precedence.put("/", new Integer(2));
		precedence.put("%", new Integer(2));
		precedence.put("^", new Integer(4));
		precedence.put("sin", new Integer(3));
		precedence.put("cos", new Integer(3));
		precedence.put("tan", new Integer(3));
		precedence.put("arcsin", new Integer(3));
		precedence.put("arccos", new Integer(3));
		precedence.put("arctan", new Integer(3));
		precedence.put("!", new Integer(3));
		precedence.put("log", new Integer(3));
		precedence.put("ln", new Integer(3));
		precedence.put("neg", new Integer(6));
		precedence.put("(", new Integer(0));
		precedence.put("[", new Integer(0));
		precedence.put("sqrt", new Integer(3));
		precedence.put("abs", new Integer(3));
		
		numOperands = new HashMap<String, Integer>();
		numOperands.put("+", new Integer(2));
		numOperands.put("-", new Integer(2));
		numOperands.put("*", new Integer(2));
		numOperands.put("/", new Integer(2));
		numOperands.put("%", new Integer(2));
		numOperands.put("^", new Integer(2));
		numOperands.put("sin", new Integer(1));
		numOperands.put("cos", new Integer(1));
		numOperands.put("tan", new Integer(1));
		numOperands.put("arcsin", new Integer(1));
		numOperands.put("arccos", new Integer(1));
		numOperands.put("arctan", new Integer(1));
		numOperands.put("!", new Integer(1));
		numOperands.put("log", new Integer(1));
		numOperands.put("ln", new Integer(1));
		numOperands.put("neg", new Integer(1));
		numOperands.put("abs", new Integer(1));
		numOperands.put("sqrt", new Integer(1));
	}
	
	private static boolean isHigherPrecedence(String a, String b)
	{
		return (precedence.get(a).intValue() >= precedence.get(b).intValue());
	}
	
	public Calculator()
	{
		
	}
	
	public Calculator(JFrame frame)
	{
		cursorPos = 0;
		drawCalculator(frame);
	}
	
	private void drawCalculator(JFrame frame)
	{
		panel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JMenu Mode = new JMenu("Mode");
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButtonMenuItem scientific = new JRadioButtonMenuItem("Scientific", true);
		JRadioButtonMenuItem graphing = new JRadioButtonMenuItem("Graphing");
		JRadioButtonMenuItem conversion = new JRadioButtonMenuItem("Conversion");
		buttonGroup.add(scientific);
		buttonGroup.add(graphing);
		buttonGroup.add(conversion);
		Mode.add(scientific);
		Mode.add(graphing);
		Mode.add(conversion);
		menuBar.add(Mode);
		frame.setJMenuBar(menuBar);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		ioField = new JTextArea(6,40);
		ioField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		ioField.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(ioField);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2;
		gbc.weighty = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 6;
		gbl.setConstraints(scrollPane, gbc);
		panel.add(scrollPane);
		frame.setContentPane(panel);
		makeButton("sin", gbl, gbc);
		makeButton("cos", gbl, gbc);
		makeButton("tan", gbl, gbc);
		makeButton("pi", gbl, gbc);
		makeButton("arcsin", gbl, gbc);
		makeButton("arccos", gbl, gbc);
		makeButton("arctan", gbl, gbc);
		makeButton("log", gbl, gbc);
		makeButton("ln", gbl, gbc);
		makeButton("e^", gbl, gbc);
		makeButton("abs", gbl, gbc);
		makeButton("!", gbl, gbc);
		makeButton("sqrt", gbl, gbc);
		makeButton("10^", gbl, gbc);
		makeButton("^", gbl, gbc);
		makeButton("%", gbl, gbc);
		makeButton("[", gbl, gbc);
		makeButton("]", gbl, gbc);
		makeButton("(", gbl, gbc);
		makeButton(")", gbl, gbc);
		makeButton("7", gbl, gbc);
		makeButton("8", gbl, gbc);
		makeButton("9", gbl, gbc);
		makeButton("+", gbl, gbc);
		makeButton("4", gbl, gbc);
		makeButton("5", gbl, gbc);
		makeButton("6", gbl, gbc);
		makeButton("-", gbl, gbc);
		makeButton("1", gbl, gbc);
		makeButton("2", gbl, gbc);
		makeButton("3", gbl, gbc);
		makeButton("*", gbl, gbc);
		makeButton("0", gbl, gbc);
		makeButton(".", gbl, gbc);
		makeButton("Ans", gbl, gbc);
		makeButton("/", gbl, gbc);
		makeButton("Clear", gbl, gbc);
		makeButton("Enter", gbl, gbc, 80, 30);
		frame.pack();
	}
	
	protected void makeButton(String name, GridBagLayout gridbag, GridBagConstraints c) {
         JButton button = new JButton(name);
         button.setPreferredSize(new Dimension(62, 30));
         button.setMargin(new Insets(0,0,0,0));
         button.addActionListener(new CalculatorListener(this));
         gridbag.setConstraints(button, c);
         panel.add(button);
     }
     
     protected void makeButton(String name, GridBagLayout gridbag, GridBagConstraints c, int x, int y) {
         JButton button = new JButton(name);
         button.setPreferredSize(new Dimension(x, y));
         button.addActionListener(new CalculatorListener(this));
         gridbag.setConstraints(button, c);
         panel.add(button);
     }
	
	public double evaluate(String expression)
	{
		infixExp = new Vector<Object>();
		opStack = new Stack<String>();
		
		parse(expression);
		
		Vector<Object> out = new Vector<Object>();
		while(infixExp.size() > 0)
		{
			Object nextToken = infixExp.remove(0);
			if(nextToken instanceof Double)
				out.add(nextToken);
			else if(((String)nextToken).equals("("))
			{
				opStack.push((String)nextToken);
			}
			else if(((String)nextToken).equals(")"))
			{
				Object popped;
				do{
					popped = opStack.pop();
					if(!((String)popped).equals("("))
						out.add((String)popped);
				}while(!((String)popped).equals("("));
			}
			else if(((String)nextToken).equals("neg"))
			{
				out.add(infixExp.remove(0));
				out.add((String)nextToken);
			}
			else if(!opStack.empty())
			{
				if(!isHigherPrecedence((String)nextToken, opStack.peek()))
				{
					out.add(opStack.pop());
					opStack.push((String)nextToken);
				}
				else
				{
					opStack.push((String)nextToken);
				}
			}
			else
				opStack.push((String)nextToken);
		}
		while(!opStack.empty())
		{
			out.add(opStack.pop());
		}
		
		while(out.size() > 1)
		{
			int indexOfOp = 0;
			for(int k = 0; k < out.size(); k++)
			{
				if(out.get(k) instanceof String)
				{
					indexOfOp = k;
					break;
				}
			}
			if(numOperands.get(out.get(indexOfOp)).intValue() == 1)
			{
				Double a = (Double)out.remove(indexOfOp - 1);
				out.setElementAt(new Double(compute((String)out.get(indexOfOp-1), a.doubleValue())), indexOfOp-1);
			}
			else if(numOperands.get(out.get(indexOfOp)).intValue() == 2)
			{
				Double a = (Double)out.remove(indexOfOp - 2);
				Double b = (Double)out.remove(indexOfOp - 2);
				
				double t = compute((String)out.get(indexOfOp-2), a.doubleValue(), b.doubleValue());
				Double y = new Double(t);
				out.setElementAt(y, indexOfOp-2);
			}
		}
		
		return ((Double)out.get(0)).doubleValue();
	}
	
	private String parse(String exp)
	{
		StringBuffer temp = new StringBuffer("");
		
		for(int k = 0; k < exp.length(); k++)
		{
			if(exp.charAt(k) == '-' && k+1 < exp.length() && exp.charAt(k+1) == '-')
			{
				temp.append("+");
				k++;
			}
			else if(exp.charAt(k) == '-' && 
				(
					(k-1 >= 0 && 
						(!Character.isDigit
							(exp.charAt
								(k-1)
							) && exp.charAt(k-1)!=')'
						)
					) || k == 0
				))
			{
				temp.append("neg");
			}
			else if(exp.charAt(k) == '+' && k+1 < exp.length() && exp.charAt(k+1) == '-')
			{
				temp.append("-");
				k++;
			}
			else if(exp.charAt(k) == ' ')
			{
			}
			else if(exp.charAt(k) == 'e' && k+1 < exp.length() && exp.charAt(k+1) == '^')
			{
				temp.append(Math.E);
			}
			else if(exp.charAt(k) == 'p' && k+1 < exp.length() && exp.charAt(k+1) == 'i')
			{
				temp.append(Math.PI);
			}
			else if(exp.charAt(k) == 'A' && k+1 < exp.length() && exp.charAt(k+1) == 'n')
			{
				temp.append(lastResult);
				k+=2;
			}
			else
			{
				temp.append(exp.charAt(k));
			}
		}
		
		int parCount = 0;
		for(int k = 0; k < temp.length(); k++)
		{
			if(temp.charAt(k) == '(')
			{
				parCount++;
			}
		}
		
		StringTokenizer token = new StringTokenizer(temp.toString(),"abcdefghijklmnopqrstuvwxyz+-*/!^%()[]",true);
		while(token.hasMoreTokens())
		{
			String nextToken = token.nextToken();
			try{
				infixExp.add(new Double(Double.parseDouble(nextToken)));
			}catch(NumberFormatException e)
			{
				infixExp.add((String)nextToken);
			}
		}
		infixExp = findBigOps(infixExp);
		
		return temp.toString();
	}
	
	private Vector<Object> findBigOps(Vector<Object> ops)
	{
		for(int k = 0; k < ops.size()-1; k++)
		{
			if(ops.get(k) instanceof String)
			{
				if(k+5 < ops.size())
				{
					if(ops.get(k).equals("a") && ops.get(k+1).equals("r")
						&& ops.get(k+2).equals("c") && ops.get(k+3).equals("s")
						&& ops.get(k+4).equals("i") && ops.get(k+5).equals("n"))
					{
						ops.set(k, "arcsin");
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
					}
					else if(ops.get(k).equals("a") && ops.get(k+1).equals("r")
						&& ops.get(k+2).equals("c") && ops.get(k+3).equals("c")
						&& ops.get(k+4).equals("o") && ops.get(k+5).equals("s"))
					{
						ops.set(k, "arccos");
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
					}
					else if(ops.get(k).equals("a") && ops.get(k+1).equals("r")
						&& ops.get(k+2).equals("c") && ops.get(k+3).equals("t")
						&& ops.get(k+4).equals("a") && ops.get(k+5).equals("n"))
					{
						ops.set(k, "arctan");
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
						ops.remove(k+1);
					}
				}
				if(k+2 < ops.size())
				{
					if(ops.get(k).equals("s"))
					{
						if(ops.get(k+1).equals("i"))
						{
							ops.set(k, "sin");
							ops.remove(k+1);
							ops.remove(k+1);
						}
					}
					else if(ops.get(k).equals("c"))
					{
						if(ops.get(k+1).equals("o"))
						{
							ops.set(k, "cos");
							ops.remove(k+1);
							ops.remove(k+1);
						}
					}
					else if(ops.get(k).equals("t"))
					{
						if(ops.get(k+1).equals("a"))
						{
							ops.set(k, "tan");
							ops.remove(k+1);
							ops.remove(k+1);
						}
					}
					else if(ops.get(k).equals("l") && ops.get(k+1).equals("o") && ops.get(k+2).equals("g"))
					{
						ops.set(k, "log");
						ops.remove(k+1);
						ops.remove(k+1);
					}
					else if(ops.get(k).equals("n"))
					{
						if(ops.get(k+1).equals("e"))
						{
							ops.set(k, "neg");
							ops.remove(k+1);
							ops.remove(k+1);
						}
					}
				}
			}
			if(k+1 < ops.size())
			{
				if(ops.get(k).equals("l") && ops.get(k+1).equals("n"))
				{
					ops.set(k, "ln");
					ops.remove(k+1);
				}
			}
			
		}
		
		return ops;
	}
	
	/**
	 * @return   Returns the lastResult.
	 * @uml.property   name="lastResult"
	 */
	public double getLastResult()
	{
		return lastResult;
	}
	
	public int getCursorPosition()
	{
		return cursorPos;
	}
	
	public void enter()
	{
		try{
			double answer = evaluate(ioField.getText(cursorPos, ioField.getCaretPosition()-cursorPos));//Text().length()-cursorPos));
			DecimalFormat deciForm;
			if(answer >= (Math.pow(10,25)))
			{
				deciForm = new DecimalFormat("#.####################E0##");
			}
			else
			{
				deciForm = new DecimalFormat("############.#############");
			}
			
			String answerStr = deciForm.format(answer);
			ioField.append("\n");
			for(int k = 0; k < (36-answerStr.length()); k++)
				ioField.append(" ");
			ioField.append(answerStr + "\n");
			cursorPos = ioField.getText().length();
			lastResult = answer;
		}catch(javax.swing.text.BadLocationException ble){}
	}
	
	public void clear()
	{
		cursorPos = 0;
		ioField.selectAll();
		ioField.replaceSelection("");
	}
	
	public double compute(String op, double a, double b)
	{
		double num = 0;
		if(op.equals("+"))
		{
			num = a+b;
		}
		else if(op.equals("-"))
		{
			num = a-b;
		}
		else if(op.equals("*"))
		{
			num = a*b;
		}
		else if(op.equals("/"))
		{
			num = a/b;
		}
		else if(op.equals("^"))
		{
			num = Math.pow(a,b);
		}
		else if(op.equals("%"))
		{
			num = a%b;
		}
		
		return num;
	}
	
	public double compute(String op, double a)
	{
		double num = 0;
		if(op.equals("!"))
		{
			num = a;
			for(int k = (int)a-1; k > 1; k--)
			{
				num *= k;
			}
		}
		else if(op.equals("sin"))
		{
			num = Math.sin(a);
		}
		else if(op.equals("cos"))
		{
			num = Math.cos(a);
		}
		else if(op.equals("tan"))
		{
			num = Math.tan(a);
		}
		else if(op.equals("arcsin"))
		{
			num = Math.asin(a);
		}
		else if(op.equals("arccos"))
		{
			num = Math.acos(a);
		}
		else if(op.equals("arctan"))
		{
			num = Math.atan(a);
		}
		else if(op.equals("log"))
		{
			num = Math.log10(a);
		}
		else if(op.equals("ln"))
		{
			num = Math.log(a);
		}
		else if(op.equals("neg"))
		{
			num = -a;
		}
		else if(op.equals("sqrt"))
		{
			num = Math.sqrt(a);
		}
		else if(op.equals("abs"))
		{
			num = Math.abs(a);
		}
		
		return num;
	}
}