package SChemP;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * A class that represents an element on the periodic table.
 *
 * @see PeriodicTable
 * @author Scott Kolodziej
 * @version 1.0 5/13/05
 **/
public class Element
{
	public String name;
	public String symbol;
	public int atomicNumber;
	public double atomicMass;
	public double atomicVolume;
	public Set<Integer> oxidationStates;
	public String electronConfiguration;
	public double normalBoilingPoint;
	public double normalMeltingPoint;
	public double density;
	public double electronegativity;
	public double atomicRadius;
	public double specificHeatCapacity;
	public double ionizationPotential;
	public double thermalConductivity;
	public double heatOfFusion;
	public double heatOfVaporization;
	public double covalentRadius;
	
	/**
	 * Default Constructor for class Element.
	 **/
	public Element()
	{
		name = null;
		symbol = null;
		atomicNumber = -1;
		atomicMass = -1;
		atomicVolume = -1;
		oxidationStates = new HashSet<Integer>();
		normalBoilingPoint = -1;
		normalMeltingPoint = -1;
		density = -1;
		electronConfiguration = null;
		electronegativity = -1;
		atomicRadius = -1;
		specificHeatCapacity = -1;
		ionizationPotential = -1;
		thermalConductivity = -1;
		heatOfFusion = -1;
		heatOfVaporization = -1;
		covalentRadius = -1;
	}
	
	/**
	 * Basic Constructor for class Element.  Slightly more deatiled.
	 * @param n		Name of the Element
	 * @param s		Symbol of the Element
	 * @param an	Atomic Number of the Element
	 * @param am	Atomic Mass of the Element (in g/mol)
	 **/
	public Element(String n, String s, int an, int am)
	{
		name = n;
		symbol = s;
		atomicNumber = an;
		atomicMass = am;
		atomicVolume = 0;
		oxidationStates = new HashSet<Integer>();
		normalBoilingPoint = -1;
		normalMeltingPoint = -1;
		density = -1;
		electronConfiguration = null;
		electronegativity = -1;
		atomicRadius = -1;
		specificHeatCapacity = -1;
		ionizationPotential = -1;
		thermalConductivity = -1;
		heatOfFusion = -1;
		heatOfVaporization = -1;
		covalentRadius = -1;
	}
	
	/**
	 * Complete Constructor for class Element.
	 * @param n				Name of the Element
	 * @param s				Symbol of the Element
	 * @param an			Atomic Number of the Element
	 * @param am			Atomic Mass of the Element (in g/mol)
	 * @param os			A Set<Integer> of Oxidation States
	 * @param elecConfig	A String representing the electron configuration of the element in Noble Gas Notation
	 * @param bp			The Normal Boiling Point of the Element in Kelvin
	 * @param mp			The Normal Melting Point of the Element in Kelvin
	 * @param dens			The Density of the Element in g/cm??
	 * @param electroneg	The Electronegativity value of the Element
	 * @param ar			The Atomic Radius of the Element in Angstroms (???)
	 * @param specHeat		The Specific Heat Capacity of the Element in Joules/gram/??Kelvin
	 * @param ionPot		The Ionization Potential of the Element
	 * @param thermalCond	The Thermal Conductivity of the Element in Watts/meter/??Kelvin
	 * @param heatOfFus		The Heat of Fusion of the Element in kilojoules/mole
	 * @param heatOfVap		The Heat of Vaporization of the Element in kilojoules/mole
	 * @param covRad		The Covalent Radius of the Element in Angstroms (???)
	 **/
	public Element(String n, String s, int an, double am, double av, 
		Set<Integer> os, String elecConfig, double bp, double mp, double dens, 
		double electroneg, double ar, double specHeat, double ionPot, double thermalCond, 
		double heatOfFus, double heatOfVap, double covRad)
	{
		name = n;
		symbol = s;
		atomicNumber = an;
		atomicMass = am;
		atomicVolume = av;
		oxidationStates = os;
		electronConfiguration = elecConfig;
		normalBoilingPoint = bp;
		normalMeltingPoint = mp;
		density = dens;
		electronegativity = electroneg;
		atomicRadius = ar;
		specificHeatCapacity = specHeat;
		ionizationPotential = ionPot;
		thermalConductivity = thermalCond;
		heatOfFusion = heatOfFus;
		heatOfVaporization = heatOfVap;
		covalentRadius = covRad;
		
	}
	
	/**
	 * A String representation of the Element, just its Symbol.
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return symbol;
	}
	
	/* (non-Javadoc)
	 * @see SChemP.Compoundable#toUnicodeString()
	 */
	public String toUnicodeString()
	{
		return symbol;
	}
	
	/**
	 * Returns a detailed description of the data contained in this Element,
	 * including name, symbol, atomic number, and other physical properties.
	 * The returned string is formatted with units and a new line (\n) character
	 * after each property's output.
	 * @return A detailed String representation of the Element
	 */
	public String thoroughOutput()
	{
		String output = "";
		
		if(name != null)
			output += ("Name: " + name + "\n");
		if(symbol != null)
			output += ("Symbol: " + symbol + "\n");
		if(atomicNumber != -1)
			output += ("Atomic Number: " + atomicNumber + "\n");
		if(atomicMass != -1)
			output += ("Atomic Mass: " + atomicMass + " g/mol\n");
		if(atomicVolume != -1)
			output += ("Atomic Volume: " + atomicVolume + " cm\u00B3/mol\n");
		if(oxidationStates.size() != 0)
		{
			Object[] states = oxidationStates.toArray();
			
			output += "Oxidation States: ";
			
			for(int k = 0; k < states.length; k++)
			{
				if(((Integer)states[k]).intValue() > 0)
					output += ("+");
				output += (states[k]);
				if(k != states.length-1)
					output += ", ";
			}
			
			output += ("\n");
		}
		if(normalBoilingPoint != -1)
			output += ("Normal Boiling Point: " + normalBoilingPoint + " K\n");
		if(normalMeltingPoint != -1)
			output += ("Normal Melting Point: " + normalMeltingPoint + " K\n");
		if(density != -1)
			output += ("Density: " + density + " g/cm\u00B3\n");
		if(electronConfiguration != null)
		{
			output += ("Electron Configuration: " + electronConfiguration + "\n");
			output += ("Full Electron Configuration: " + getFullElectronConfiguration() + "\n");
		}
		if(electronegativity != -1)
			output += ("Electronegativity: " + electronegativity + "\n");
		if(atomicRadius != -1)
			output += ("Atomic Radius: " + atomicRadius + " \u212B\n");
		if(specificHeatCapacity != -1)
			output += ("Specific Heat Capacity: " + specificHeatCapacity + " J/g/K\n");
		if(ionizationPotential != -1)
			output += ("Ionization Potential: " + ionizationPotential + "\n");
		if(thermalConductivity != -1)
			output += ("Thermal Conductivity: " + thermalConductivity + " W/m/K\n");
		if(heatOfFusion != -1)
			output += ("Heat of Fusion: " + heatOfFusion + " kJ/mol\n");
		if(heatOfVaporization != -1)
			output += ("Heat of Vaporization: " + heatOfVaporization + " kJ/mol\n");
		if(covalentRadius != -1)
			output += ("Covalent Radius: " + covalentRadius + " \u212B\n");
		
		return output;
	}
	
	/**
	 * Returns a String containing the value (with units) of the element's
	 * given property.
	 * @param property
	 * @return A String with the value and units of the given property
	 */
	public String getPropertyWithUnits(String property)
	{
		String output = "";
		
		if(property == null)
			return "";
		
		if(property.equalsIgnoreCase("name"))
			output = name;
		if(property.equalsIgnoreCase("symbol"))
			output = symbol;
		if(property.equalsIgnoreCase("Atomic Number"))
			output = "" + atomicNumber;
		if(property.equalsIgnoreCase("Atomic Mass"))
			output = (atomicMass + " g/mol");
		if(property.equalsIgnoreCase("Atomic Volume"))
			output = (atomicVolume + " cm\u00B3/mol");
		if(property.equalsIgnoreCase("Oxidation States"))
			output = oxidationStates.toString();
		if(property.equalsIgnoreCase("Normal Boiling Point"))
			output = (normalBoilingPoint + " K");
		if(property.equalsIgnoreCase("Normal Melting Point"))
			output = (normalMeltingPoint + " K");
		if(property.equalsIgnoreCase("Density"))
			output = (density + " g/cm\u00B3");
		if(property.equalsIgnoreCase("Electron Configuration"))
			output = (electronConfiguration);
		if(property.equalsIgnoreCase("Full Electron Configuration"))
			output = (getFullElectronConfiguration());
		if(property.equalsIgnoreCase("Electronegativity"))
			output = "" + electronegativity;
		if(property.equalsIgnoreCase("Atomic Radius"))
			output = (atomicRadius + " \u212B");
		if(property.equalsIgnoreCase("Specific Heat Capacity"))
			output = (specificHeatCapacity + " J/g/K");
		if(property.equalsIgnoreCase("Ionization Potential"))
			output = "" + ionizationPotential;
		if(property.equalsIgnoreCase("Thermal Conductivity"))
			output = (thermalConductivity + " W/m/K");
		if(property.equalsIgnoreCase("Heat of Fusion"))
			output = (heatOfFusion + " kJ/mol");
		if(property.equalsIgnoreCase("Heat of Vaporization"))
			output = (heatOfVaporization + " kJ/mol");
		if(property.equalsIgnoreCase("Covalent Radius"))
			output = (covalentRadius + " \u212B");
		
		return output;
	}
	
	public String getPropertyWithUnitsAndFormatted(String property, DecimalFormat format)
	{
		String output = "";
		
		if(property == null)
			return "";
		
		if(property.equalsIgnoreCase("name"))
			output = name;
		if(property.equalsIgnoreCase("symbol"))
			output = symbol;
		if(property.equalsIgnoreCase("Atomic Number"))
			output = format.format(atomicNumber);
		if(property.equalsIgnoreCase("Atomic Mass"))
			output = (format.format(atomicMass) + " g/mol");
		if(property.equalsIgnoreCase("Atomic Volume"))
			output = (format.format(atomicVolume) + " cm\u00B3/mol");
		if(property.equalsIgnoreCase("Oxidation States"))
			output = oxidationStates.toString();
		if(property.equalsIgnoreCase("Normal Boiling Point") ||
				property.equalsIgnoreCase("Boiling Point"))
			output = (format.format(normalBoilingPoint) + " K");
		if(property.equalsIgnoreCase("Normal Melting Point") ||
				property.equalsIgnoreCase("Melting Point"))
			output = (format.format(normalMeltingPoint) + " K");
		if(property.equalsIgnoreCase("Density"))
			output = (format.format(density) + " g/cm\u00B3");
		if(property.equalsIgnoreCase("Electron Configuration"))
			output = format.format(electronConfiguration);
		if(property.equalsIgnoreCase("Full Electron Configuration"))
			output = format.format(getFullElectronConfiguration());
		if(property.equalsIgnoreCase("Electronegativity"))
			output =  format.format(electronegativity);
		if(property.equalsIgnoreCase("Atomic Radius"))
			output = (format.format(atomicRadius) + " \u212B");
		if(property.equalsIgnoreCase("Specific Heat Capacity"))
			output = (format.format(specificHeatCapacity) + " J/g/K");
		if(property.equalsIgnoreCase("Ionization Potential"))
			output = format.format(ionizationPotential);
		if(property.equalsIgnoreCase("Thermal Conductivity"))
			output = (format.format(thermalConductivity) + " W/m/K");
		if(property.equalsIgnoreCase("Heat of Fusion"))
			output = (format.format(heatOfFusion) + " kJ/mol");
		if(property.equalsIgnoreCase("Heat of Vaporization"))
			output = (format.format(heatOfVaporization) + " kJ/mol");
		if(property.equalsIgnoreCase("Covalent Radius"))
			output = (format.format(covalentRadius) + " \u212B");
		
		return output;
	}
	
	public Object getProperty(String property) throws SChemPException
	{
		Object result = null;
		
		if(!PeriodicTable.isAcceptableProperty(property))
			throw new SChemPException("No Such Property: " + property);
		
		if(property.equalsIgnoreCase("Name"))
		{
			result = name;
		}
		else if(property.equalsIgnoreCase("Symbol"))
		{
			result = symbol;
		}
		else if(property.equalsIgnoreCase("Atomic Number") ||
				property.equalsIgnoreCase("ATOMIC_NUMBER"))
		{
			result = atomicNumber;
		}
		else if(property.equalsIgnoreCase("Atomic Mass") ||
				property.equalsIgnoreCase("ATOMIC_MASS"))
		{
			result = atomicMass;
		}
		else if(property.equalsIgnoreCase("Atomic Volume") ||
				property.equalsIgnoreCase("ATOMIC_VOLUME"))
		{
			result = atomicVolume;
		}
		else if(property.equalsIgnoreCase("Boiling Point") ||
				property.equalsIgnoreCase("BOILING_POINT"))
		{
			result = normalBoilingPoint;
		}
		else if(property.equalsIgnoreCase("Melting Point") ||
				property.equalsIgnoreCase("MELTING_POINT"))
		{
			result = normalMeltingPoint;
		}
		else if(property.equalsIgnoreCase("Specific Heat Capacity") ||
				property.equalsIgnoreCase("SPECIFIC_HEAT_CAPACITY"))
		{
			result = specificHeatCapacity;
		}
		else if(property.equalsIgnoreCase("Density"))
		{
			result = density;
		}
		else if(property.equalsIgnoreCase("Electronegativity"))
		{
			result = electronegativity;
		}
		else if(property.equalsIgnoreCase("Atomic Radius") ||
				property.equalsIgnoreCase("ATOMIC_RADIUS"))
		{
			result = atomicRadius;
		}
		else if(property.equalsIgnoreCase("Ionization Potential") ||
				property.equalsIgnoreCase("IONIZATION_POTENTIAL"))
		{
			result = ionizationPotential;
		}
		else if(property.equalsIgnoreCase("Thermal Conductivity") ||
				property.equalsIgnoreCase("THERMAL_CONDUCTIVITY"))
		{
			result = thermalConductivity;
		}
		else if(property.equalsIgnoreCase("Heat of Fusion") ||
				property.equalsIgnoreCase("HEAT_OF_FUSION"))
		{
			result = heatOfFusion;
		}
		else if(property.equalsIgnoreCase("Heat of Vaporization") ||
				property.equalsIgnoreCase("HEAT_OF_VAPORIZATION"))
		{
			result = heatOfVaporization;
		}
		else if(property.equalsIgnoreCase("Covalent Radius") ||
				property.equalsIgnoreCase("COVALENT_RADIUS"))
		{
			result = covalentRadius;
		}
		
		return result;
	}
	
	/**
	 * Provides the full electron configuration of the ground state of the
	 * Element.  Noble Gas notation is not used.
	 * @return
	 */
	public String getFullElectronConfiguration()
	{
		String fullEC = "";
		String temp;
		
		StringTokenizer token = new StringTokenizer(electronConfiguration);
		while(token.hasMoreTokens())
		{
			if((temp = token.nextToken()).charAt(0) == '[')
			{
				try{
					fullEC += PeriodicTable.getElement(temp.substring(1,3),"SYMBOL").getFullElectronConfiguration();
				}
				catch(SChemPException se){}
			}
			else
				fullEC += temp + " ";
		}
		
		return fullEC;
	}
	
	public ElementIcon getIcon(int height, int width)
	{
		return new ElementIcon(height, width, name, symbol, atomicNumber, atomicMass);
	}
	
	public ElementIcon getColoredIcon(int height, int width, Color color)
	{
		return new ElementIcon(height, width, name, symbol, atomicNumber, atomicMass, color);
	}
	
	public ElementIcon getCharacterIcon(int height, int width, char c)
	{
		return new ElementIcon(height, width, name, symbol, atomicNumber, atomicMass, c);
	}
	
	public boolean equals(Compoundable c)
	{
		return (c instanceof Element && (c.toString()).equals(this.toString()));
	}
	
	public static String wordCheck(String word)
	{
		String output = "";
		
		
		
		return output;
	}
}