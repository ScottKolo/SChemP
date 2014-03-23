package SChemP; 

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * The Compound class represents a chemical compound.
 * This class contains a variety of output methods,
 * as chemical compounds may be easier to process in
 * different forms (with and without charges, unicode
 * or simple ASCII, etc.).
 * @author Scott Kolodziej
 *
 */
public class Compound implements Compoundable{

	private Compoundable[] elements;
	private int[] subscripts;
	int charge;
	
	public Compound()
	{
		
	}
	
	public Compound(Compoundable[] elements, int[] subscripts)
	{
		this.elements = elements;
		this.subscripts = subscripts;
	}
	
	public Compound(Compoundable[] elements, int[] subscripts, int charge)
	{
		this.elements = elements;
		this.subscripts = subscripts;
		this.charge = charge;
	}
	
	public Compoundable[] getElements()
	{
		return elements;
	}
	
	
	public int[] getSubscripts()
	{
		return subscripts;
	}
	
	/**
	 * Parses a string that represents a chemical compound
	 * into a Compound.  
	 * <p>
	 * Square brackets and parentheses are equivalent
	 * (i.e. Be(NO3)2 and Be[NO3]2 are the same).
	 * <p>
	 * To signify a charge on the compound as a whole,
	 * use curly braces (i.e. Ca{2+} and NH4{1+}).
	 * @param compoundString
	 * @return
	 * @throws SChemPException
	 */
	public static Compound parseCompound(String compoundString) throws SChemPException
	{
		if(SChemPCore.debug())
			System.out.println("Parse: "+ compoundString);
		ArrayList<Compoundable> elementArray = new ArrayList<Compoundable>();
		ArrayList<Integer> subscriptArray = new ArrayList<Integer>();
		int numElements = 0;
		for(int k = 0; k < compoundString.length(); k++)
		{
			if(Character.isUpperCase(compoundString.charAt(k)))
				numElements++;
		}
		String temp = "";
		int elementIndex = 0;
		int subscriptIndex = 0;
		int openBrackets = 0;
		int charge = 0;
		
		for(int k = 0; k < compoundString.length(); k++)
		{
			temp += compoundString.charAt(k);
			if(k+1 == compoundString.length())
			{
				if(Character.isUpperCase(compoundString.charAt(k)))
				{
					elementArray.add(new Atom(PeriodicTable.getElement(temp, "SYMBOL")));
					temp = "";
					elementIndex++;
					subscriptArray.add(new Integer(1));
					subscriptIndex++;
				}
				else if(Character.isLowerCase(compoundString.charAt(k)))
				{
					elementArray.add(new Atom(PeriodicTable.getElement(temp, "SYMBOL")));
					temp = "";
					elementIndex++;
					subscriptArray.add(new Integer(1));
					subscriptIndex++;
				}
				else if(Character.isDigit(compoundString.charAt(k)))
				{
					subscriptArray.add(new Integer(temp));
					temp = "";
					subscriptIndex++;
				}
			}
			else if(Character.isUpperCase(compoundString.charAt(k)))
			{
				if(Character.isDigit(compoundString.charAt(k+1)))
				{
					elementArray.add(new Atom(PeriodicTable.getElement(temp, "SYMBOL")));
					temp = "";
					elementIndex++;
				}
				else if(Character.isUpperCase(compoundString.charAt(k+1)) ||
						compoundString.charAt(k+1) == '(' ||
						compoundString.charAt(k+1) == '[' ||
						compoundString.charAt(k+1) == '{')
				{
					elementArray.add(new Atom(PeriodicTable.getElement(temp, "SYMBOL")));
					temp = "";
					elementIndex++;
					subscriptArray.add(new Integer(1));
					subscriptIndex++;
				}
			}
			else if(Character.isLowerCase(compoundString.charAt(k)))
			{
				if(Character.isDigit(compoundString.charAt(k+1)))
				{
					elementArray.add(new Atom(PeriodicTable.getElement(temp, "SYMBOL")));
					temp = "";
					elementIndex++;
				}
				else if(Character.isUpperCase(compoundString.charAt(k+1)) ||
						compoundString.charAt(k+1) == '(' ||
						compoundString.charAt(k+1) == '[' ||
						compoundString.charAt(k+1) == '{')
				{
					elementArray.add(new Atom(PeriodicTable.getElement(temp, "SYMBOL")));
					temp = "";
					elementIndex++;
					subscriptArray.add(new Integer(1));
					subscriptIndex++;
				}
			}
			else if(Character.isDigit(compoundString.charAt(k)))
			{
				if(Character.isUpperCase(compoundString.charAt(k+1)))
				{
					subscriptArray.add(new Integer(temp));
					temp = "";
					subscriptIndex++;
				}
				else if(compoundString.charAt(k+1) == '(' ||
					compoundString.charAt(k+1) == '[')
				{
					subscriptArray.add(new Integer(temp));
					temp = "";
					subscriptIndex++;
				}
				else if(Character.isLowerCase(compoundString.charAt(k+1)))
					throw new SChemPException("No Such Element Error: Elements Must Start With Capital Letters");
			}
			else if(compoundString.charAt(k) == '(' ||
					compoundString.charAt(k) == '[')
			{
				openBrackets++;
				temp = "";
				k++;
				while(openBrackets > 0)
				{
					if(compoundString.charAt(k) == '(' ||
							compoundString.charAt(k) == '[')
					{
						openBrackets++;
					}
					else if(compoundString.charAt(k) == ')' ||
							compoundString.charAt(k) == ']')
					{
						openBrackets--;
					}
					if(openBrackets > 0)
					{
						temp += compoundString.charAt(k);
						k++;
					}
				}
				
				elementArray.add(parseCompound(temp));
				
				if(k+1 == compoundString.length())
				{
					subscriptArray.add(new Integer(1));
				}
				else if(compoundString.charAt(k+1) == '(' ||
						compoundString.charAt(k+1) == '[')
				{
					subscriptArray.add(new Integer(1));
				}
				elementIndex++;
				temp = "";
			}
			else if(compoundString.charAt(k) == '{')
			{
				String chargeStr = "";
				k++;
				
				while(compoundString.charAt(k) != '}')
				{
					if(compoundString.charAt(k) == '*')
						chargeStr += "+";
					else 
						chargeStr += compoundString.charAt(k);
					k++;
				}
				
				char first = chargeStr.charAt(0);
				char last = chargeStr.charAt(chargeStr.length()-1);
				if(first == '-')
				{
					charge = -(Integer.parseInt(chargeStr.substring(1)));
				}
				else if(first == '+')
				{
					charge = (Integer.parseInt(chargeStr.substring(1)));
				}
				else if(last == '-')
				{
					charge = -(Integer.parseInt(chargeStr.substring(0, chargeStr.length()-1)));
				}
				else if(last == '+')
				{
					charge = (Integer.parseInt(chargeStr.substring(0, chargeStr.length()-1)));
				}
				else
				{
					charge = Integer.parseInt(chargeStr);
				}
				//chargeStr = chargeStr.charAt(chargeStr.length()-1) + chargeStr.substring(0,chargeStr.length()-1);
				//charge = Integer.parseInt(chargeStr);
				//System.out.println(charge);
			}
		}
		
		Compoundable[] staticCompoundArray = new Compoundable[elementArray.size()];
		int[] staticSubscriptArray = new int[subscriptArray.size()];
		ListIterator<Integer> iter = subscriptArray.listIterator();
		int iterations = 0;
		
		while(iter.hasNext())
		{
			staticSubscriptArray[iterations] = ((Integer)iter.next()).intValue();
			staticCompoundArray[iterations] = (Compoundable)elementArray.get(iterations);
			iterations ++;
		}
		if(SChemPCore.debug())
			System.out.println("Parsed: " + new Compound(staticCompoundArray, staticSubscriptArray, charge));
		
		return new Compound(staticCompoundArray,
				staticSubscriptArray, charge);
	}
	
	public boolean containsCompound()
	{
		boolean found = false;
		for(int k = 0; k < elements.length; k++)
		{
			if(elements[k] instanceof Compound)
				found = true;
		}
		
		return found;
	}
	
	public String toString()
	{
		String compoundString = "";
		
		for(int k = 0; k < elements.length; k++)
		{
			if(elements[k] instanceof Compound && ((Compound)elements[k]).containsCompound())
				compoundString += "[" + elements[k].toString() + "]" + ((subscripts[k] == 1)? "" : "" + subscripts[k]);
			else if(elements[k] instanceof Compound)
				compoundString += "(" + elements[k].toString() + ")" + ((subscripts[k] == 1)? "" : "" + subscripts[k]);
			else
				compoundString += elements[k].toString() + ((subscripts[k] == 1)? "" : "" + subscripts[k]);
		}
		
		return compoundString;
	}
	
	public String toStringWithCharge()
	{
		String string = toString();
		if(charge < 0)
		{
			string += "{" + -charge + "-}";
		}
		else
		{
			string += "{" + charge + "+}";
		}
		
		return string;
	}
	
	public static String toUnicodeSubscript(int number)
	{
		int[] array = new int[(int)Math.log10(number)+1];
		for(int k = array.length-1; k >= 0; k--)
		{
			array[array.length-1-k] = number/((int)Math.pow(10,k));
			number %= (int)Math.pow(10,k);
		}
		
		String unicodeSubscript = "";
		
		for(int k = 0; k < array.length; k++)
		{
			if(array[k] == 0)
			{
				unicodeSubscript += "\u2080";
			}
			else if(array[k] == 1)
			{
				unicodeSubscript += "\u2081";
			}
			else if(array[k] == 2)
			{
				unicodeSubscript += "\u2082";
			}
			else if(array[k] == 3)
			{
				unicodeSubscript += "\u2083";
			}
			else if(array[k] == 4)
			{
				unicodeSubscript += "\u2084";
			}
			else if(array[k] == 5)
			{
				unicodeSubscript += "\u2085";
			}
			else if(array[k] == 6)
			{
				unicodeSubscript += "\u2086";
			}
			else if(array[k] == 7)
			{
				unicodeSubscript += "\u2087";
			}
			else if(array[k] == 8)
			{
				unicodeSubscript += "\u2088";
			}
			else if(array[k] == 9)
			{
				unicodeSubscript += "\u2089";
			}
		}
		
		return unicodeSubscript;
	}
	
	public static String toUnicodeSuperscript(int number)
	{
		int[] array = new int[(int)Math.log10(number)+1];
		for(int k = array.length-1; k >= 0; k--)
		{
			array[array.length-1-k] = number/((int)Math.pow(10,k));
			number %= (int)Math.pow(10,k);
		}
		
		String unicodeSuperscript = "";
		
		for(int k = 0; k < array.length; k++)
		{
			if(array[k] == 0)
			{
				unicodeSuperscript += "\u2070";
			}
			else if(array[k] == 1)
			{
				unicodeSuperscript += "\u00B9";
			}
			else if(array[k] == 2)
			{
				unicodeSuperscript += "\u00B2";
			}
			else if(array[k] == 3)
			{
				unicodeSuperscript += "\u00B3";
			}
			else if(array[k] == 4)
			{
				unicodeSuperscript += "\u2074";
			}
			else if(array[k] == 5)
			{
				unicodeSuperscript += "\u2075";
			}
			else if(array[k] == 6)
			{
				unicodeSuperscript += "\u2076";
			}
			else if(array[k] == 7)
			{
				unicodeSuperscript += "\u2077";
			}
			else if(array[k] == 8)
			{
				unicodeSuperscript += "\u2078";
			}
			else if(array[k] == 9)
			{
				unicodeSuperscript += "\u2079";
			}
		}
		
		return unicodeSuperscript;
	}
	
	public String toUnicodeString()
	{
		String compoundString = "";
		
		for(int k = 0; k < elements.length; k++)
		{
			if(elements[k] instanceof Compound && ((Compound)elements[k]).containsCompound())
			{
				compoundString += "[" + elements[k].toUnicodeString() + "]" + ((subscripts[k] == 1)? "" : "" + toUnicodeSubscript(subscripts[k]));
				if(charge != 0)
					compoundString += toUnicodeSuperscript(charge) + ((charge > 0)?("\u207A"):("\u207B"));
			}
			else if(elements[k] instanceof Compound)
			{
				compoundString += "(" + elements[k].toUnicodeString() + ")" + ((subscripts[k] == 1)? "" : "" + toUnicodeSubscript(subscripts[k]));
				if(charge != 0)
					compoundString += toUnicodeSuperscript(charge) + ((charge > 0)?("\u207A"):("\u207B"));
			}
			else
			{
				compoundString += elements[k].toString() + ((subscripts[k] == 1)? "" : "" + toUnicodeSubscript(subscripts[k]));
				if(charge != 0)
					compoundString += toUnicodeSuperscript(charge) + ((charge > 0)?("\u207A"):("\u207B"));
			}
		}
		
		return compoundString;
	}
	
	public int getCharge()
	{
		return charge;
	}
}
