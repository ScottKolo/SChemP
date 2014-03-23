package SChemP; 

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

public class ChemicalEquation
{
	private String[] reactantStrings;
	private String[] productStrings;
	private Compoundable[] reactants;
	private Compoundable[] products;
	private int[] rCoefficients;
	private int[] pCoefficients;
	
	public ChemicalEquation()
	{
		reactants = new Compoundable[0];
		products = new Compoundable[0];
		rCoefficients = new int[reactants.length];
		pCoefficients = new int[products.length];
	}
	
	public ChemicalEquation(String equation)
	{
		try{
			parseEquation(equation);
			balance();
		}
		catch(SChemPException se){}
	}
	
	public ChemicalEquation(String[] reactants, String[] products) throws SChemPException
	{
		reactantStrings = reactants;
		productStrings = products;
		this.reactants = new Compoundable[reactants.length];
		this.products = new Compoundable[products.length];
		for(int k = 0; k < reactants.length; k++)
		{
			this.reactants[k] = Compound.parseCompound(reactants[k]);
		}
		for(int k = 0; k < products.length; k++)
		{
			this.products[k] = Compound.parseCompound(products[k]);
		}
		rCoefficients = new int[reactants.length];
		pCoefficients = new int[products.length];
		balance();
	}
	
	public ChemicalEquation(int[] rCoefficients, String[] reactants, int[] pCoefficients, String[] products)
	{
		reactantStrings = reactants;
		productStrings = products;
		this.rCoefficients = rCoefficients;
		this.pCoefficients = pCoefficients;
	}
	
	private void parseEquation(String equation) throws SChemPException
	{
		String reactantSide = "";
		String productSide = "";
		boolean left = true;
		
		for(int k = 0; k < equation.length(); k++)
		{
			if(k+1 < equation.length() && equation.charAt(k) == '+' && equation.charAt(k+1) == '}')
			{
				if(left)
					reactantSide += "*";
				else
					productSide += "*";
				k++;
			}
			else if(k+1 < equation.length() && equation.charAt(k) == '}' && equation.charAt(k+1) == '+')
			{
				if(left)
					reactantSide += "*";
				else
					productSide += "*";
				k++;
			}
				
			if(left)
			{
				if(equation.charAt(k) != ' ')
				{
					if(equation.charAt(k) != '=')
						reactantSide += equation.charAt(k);
					else
						left = false;
				}
			}
			else
			{
				if(equation.charAt(k) != ' ')
				{
					productSide += equation.charAt(k);
				}
			}
		}
		
		splitCompounds(reactantSide, true);
		splitCompounds(productSide, false);
	}
	
	private void splitCompounds(String equation, boolean reactantSide) throws SChemPException
	{
		StringTokenizer token = new StringTokenizer(equation, "+");
		
		if(reactantSide)
		{
			reactantStrings = new String[token.countTokens()];
			reactants = new Compound[reactantStrings.length];
			rCoefficients = new int[reactantStrings.length];
		}
		else
		{
			productStrings = new String[token.countTokens()];
			products = new Compound[productStrings.length];
			pCoefficients = new int[productStrings.length];
		}
		
		int c = 0;
		
		while(token.hasMoreTokens())
		{
			String temp = token.nextToken();
			String coef = "";
			String compound = "";
			boolean doneWithCoef = false;
			for(int k = 0; k < temp.length(); k++)
			{
				if(Character.isDigit(temp.charAt(k)) && !doneWithCoef)
				{
					coef += temp.charAt(k);
				}
				else
				{
					doneWithCoef = true;
					compound += temp.charAt(k);
				}
			}
			
			if(reactantSide)
			{
				if(!coef.equals(""))
					rCoefficients[c] = Integer.parseInt(coef);
				
				reactantStrings[c] = compound;
				reactants[c] = Compound.parseCompound(compound);
			}
			else
			{
				if(!coef.equals(""))
					pCoefficients[c] = Integer.parseInt(coef);
				
				productStrings[c] = compound;
				products[c] = Compound.parseCompound(compound);
			}
			c++;
		}
	}
	
	public void balance() throws SChemPException
	{
		int[] coefficients = balance(reactants, products);
		
		for(int k = 0; k < reactants.length; k++)
		{
			rCoefficients[k] = coefficients[k];
		}
		for(int k = reactants.length; k < products.length+reactants.length; k++)
		{
			pCoefficients[k-reactants.length] = coefficients[k];
		}
	}
	
	public static int[] balance(Compoundable[] reactants, Compoundable[] products) throws SChemPException
	{
		int[] coefficients = new int[reactants.length+products.length];
		
		Compoundable[] equation = new Compoundable[reactants.length+products.length];
		for(int k = 0; k < reactants.length; k++)
		{
			equation[k] = reactants[k];
		}
		for(int k = 0; k < products.length; k++)
		{
			equation[k+reactants.length] = products[k];
		}
		
		Set<Element> elements = getUniqueElements(reactants);
		Iterator<Element> iter = elements.iterator();
		int iterations = 0;
		double[][] tempMatrix = new double[elements.size()][reactants.length+products.length];
		//double[][] tempMatrixCharges = new double[elements.size()][reactants.length+products.length];
		
		while(iter.hasNext())
		{
			Element element = (Element)iter.next();
			
			for(int k = 0; k < equation.length; k++)
			{
				Compoundable[] temp = equation[k].getElements();
				Set<Element> elementsInCompound = getUniqueElements(temp);
				
				//int charge = 0;
				int subscript = 0;
				if(elementsInCompound.contains(element))
				{
					subscript = getElementCount(equation[k], element);
					//charge = equation[k].getCharge();
				}
				if(k < reactants.length || k+1 == equation.length)
				{
					tempMatrix[iterations][k] = subscript;
					//tempMatrixCharges[iterations][k] = Math.abs(charge)+1;
				}
				else
				{
					tempMatrix[iterations][k] = -subscript;
					//tempMatrixCharges[iterations][k] = -Math.abs(charge)-1;
				}
			}
			
			iterations++;
		}
		
		if(SChemPCore.debug())
		{
			System.out.println("Matrix before RREF:");
			print(tempMatrix);
		}
		Matrix matrix = new Matrix(tempMatrix, tempMatrix.length, tempMatrix[0].length);
		matrix = matrix.rref();
		if(SChemPCore.debug())
		{
			System.out.println("Matrix after RREF:");
			PrintWriter out = new PrintWriter(System.out);
			try{
				matrix.print(out);
			}
			catch(IOException ioe){}
		}
		
		//print(tempMatrixCharges);
		//Matrix chargeMatrix = new Matrix(tempMatrixCharges, tempMatrixCharges.length, tempMatrixCharges[0].length);
		//chargeMatrix.rref();
		
		//PrintWriter outc = new PrintWriter(System.out);
		//try{
		//	chargeMatrix.print(outc);
		//}
		//catch(IOException ioe){}
		
		//Make more efficient!
		boolean found = false;
		for(int k = 1; k < 1000 && !found; k++)
		{
			Matrix temp = matrix.scalerMul(k);
			//Matrix temp2 = chargeMatrix.scalerMul(k);
			if(temp.isAllPositiveIntegers())// && temp2.isAllPositiveIntegers())
			{
				matrix = temp;
				found = true;
				if(SChemPCore.debug())
					System.out.println("Multiple: " + k);
			}
		}
		
		if(SChemPCore.debug())
		{
			PrintWriter out = new PrintWriter(System.out);
			try{
				matrix.print(out);
			}
			catch(IOException ioe){}
		}
		
		if((int)Math.round(matrix.get(0,0)) == 0 || !found)
			throw new SChemPException("Unbalancable Equation");
		coefficients[coefficients.length-1] = (int)Math.round(matrix.get(0,0));
		for(int k = 0; k < matrix.rows(); k++)
		{
			if((int)Math.round(matrix.get(k,matrix.cols()-1)) == 0)
			{
				boolean identity = false;
				
				for(int c = 0; c < matrix.cols(); c++)
				{
					if(Math.abs(matrix.sumColumn(c)) == 1)
					{
						identity = true;
						for(int j = 0; j < coefficients.length; j++)
							coefficients[j] = 1;
					}
				}
				if(identity)
					throw new SChemPException("Unbalancable Equation, Identity");
			}
			else
				coefficients[k] = (int)Math.round(matrix.get(k,matrix.cols()-1));
		}
		
		return coefficients;
	}
	
	public static int numElements(Compoundable[] reactants)
	{
		int numElements = 0;
		
		Set<Element> elements = new HashSet<Element>();
		
		for(int k = 0; k < reactants.length; k++)
		{
			Compoundable[] temp = reactants[k].getElements();
			
			for(int j = 0; j < temp.length; j++)
			{
				if(temp[j] instanceof Compound)
				{
					numElements += numElements(temp[j].getElements(), elements);
				}
				else
				{
					elements.add((Element)temp[j]);
				}
			}
		}
		
		return elements.size() + numElements;
	}
	
	/**
	 * Returns the number of Elements in the Compound.
	 * @return elements The number of elements in the compound.
	 */
	public static int numElements(Compoundable[] reactants, Set<Element> elements)
	{
		int numElements = 0;
		
		for(int k = 0; k < reactants.length; k++)
		{
			Compoundable[] temp = reactants[k].getElements();
			
			for(int j = 0; j < temp.length; j++)
			{
				if(temp[j] instanceof Compound)
				{
					numElements += numElements(temp[j].getElements(), elements);
				}
				else
				{
					elements.add((Element)temp[j]);
				}
			}
		}
		
		return (elements.size() + numElements);
	}
	
	
	/**
	 * Returns a Set of all Elements in the array of Compoundables.
	 * @return elements A Set of Elements
	 */
	public static Set<Element> getUniqueElements(Compoundable[] array)
	{
		Set<Element> elements = new HashSet<Element>();
		
		for(int k = 0; k < array.length; k++)
		{//System.out.println(array[k]);
			if(array[k] instanceof Compound)
			{
				elements.addAll(getUniqueElements(array[k].getElements()));
			}
			else
			{
				elements.add(((Atom)array[k]).getElement());
			}
		}
		
		return elements;
	}
	
	public static Set<Element> getUniqueElements(Compoundable compound)
	{
		Set<Element> elements = new HashSet<Element>();
		
		if(compound instanceof Compound)
		{
			elements.addAll(getUniqueElements(compound.getElements()));
		}
		else
		{
			elements.add((Element)compound);
		}
			
		return elements;
	}
	
	/**
	 * 
	 * @param compound
	 * @param e
	 * @return The number of atoms of Element e in one molecule of compound.
	 */
	public static int getElementCount(Compoundable compound, Element e)
	{
		int count = 0;
		//System.out.println(compound);
		
		if(compound instanceof Atom && ((Atom)compound).getElement().equals(e))
		{
			count = 1;
		}
		else if(compound instanceof Compound)
		{
			Compoundable[] temp = compound.getElements();
			for(int k = 0; k < temp.length; k++)
			{
				if(temp[k] instanceof Compound)
				{
					Set<Element> uniques = getUniqueElements(temp[k]);
					if(uniques.contains(e))
					{
						count += (compound.getSubscripts())[k] * getElementCount(temp[k], e);
					}
				}
				else if(temp[k] instanceof Atom && e.equals(((Atom)temp[k]).getElement()))
				{
					count += (compound.getSubscripts())[k];
				}
			}
		}
		return count;
	}
	
	public String toString()
	{
		String equation = "";
		
		for(int k = 0; k < reactants.length; k++)
		{
			if(k < reactants.length-1)
				equation += (rCoefficients[k] + " " + reactants[k] + " + ");
			else
				equation += rCoefficients[k] + " " + reactants[k];
		}
		equation += " = ";
		for(int k = 0; k < products.length; k++)
		{
			if(k < products.length-1)
				equation += (pCoefficients[k] + " " + products[k] + " + ");
			else
				equation += pCoefficients[k] + " " + products[k];
		}
		
		return equation;
	}
	
	public String toStringWithCharges()
	{
		String equation = "";
		
		for(int k = 0; k < reactants.length; k++)
		{
			if(k < reactants.length-1)
			{
				equation += rCoefficients[k] + " " + reactants[k];
				if(reactants[k].getCharge() != 0)
					equation += "{" + 
						((reactants[k].getCharge() < 0)? (-reactants[k].getCharge() + "-}"):
							(reactants[k].getCharge() + "+}")) + " + ";
			}
			else
			{
				equation += rCoefficients[k] + " " + reactants[k];
				if(reactants[k].getCharge() != 0)
					equation += "{" + 
						((reactants[k].getCharge() < 0)? (-reactants[k].getCharge() + "-}"):
							(reactants[k].getCharge() + "+}"));
			}
		}
		
		equation += " = ";
		
		for(int k = 0; k < products.length; k++)
		{
			if(k < products.length-1)
			{
				equation += pCoefficients[k] + " " + products[k];
				if(products[k].getCharge() != 0)
					equation += "{" + 
						((products[k].getCharge() < 0)? (-products[k].getCharge() + "-}"):
							(products[k].getCharge() + "+}")) + " + ";
			}
			else
			{
				equation += pCoefficients[k] + " " + products[k]; 
				if(products[k].getCharge() != 0) equation += "{" + 
				((products[k].getCharge() < 0)? (-products[k].getCharge() + "-}"):
					(products[k].getCharge() + "+}"));
			}
		}
		
		return equation;
	}
	
	public String toUnicodeString()
	{
		String equation = "";
		
		for(int k = 0; k < reactants.length; k++)
		{
			if(k < reactants.length-1)
				equation += rCoefficients[k] + " " + reactants[k].toUnicodeString() + " + ";
			
			else
				equation += rCoefficients[k] + " " + reactants[k].toUnicodeString();
		}
		equation += " \u2192 ";
		for(int k = 0; k < products.length; k++)
		{
			if(k < products.length-1)
				equation += pCoefficients[k] + " " + products[k].toUnicodeString() + " + ";
			else
				equation += pCoefficients[k] + " " + products[k].toUnicodeString();
		}
		
		return equation;
	}
	
	public Compoundable[] getElements()
	{
		Compoundable[] list = new Compoundable[reactants.length + products.length];
		for(int k = 0; k < reactants.length; k++)
		{
			list[k] = reactants[k];
		}
		for(int k = 0; k < products.length; k++)
		{
			list[k+reactants.length] = products[k];
		}
		
		return list;
	}
	
	private static void print(double[][] x)
	{
		for(int r = 0; r < x.length; r++)
		{
			for(int c = 0; c < x[0].length; c++)
			{
				System.out.print(x[r][c] + " ");
			}
			System.out.println();
		}
	}
}