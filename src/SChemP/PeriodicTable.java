package SChemP;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;

/**
 * A class that initializes and stores a virtual Periodic Table of Elements.
 * Upon creation, it will attempt to find an XML file entitled "PeriodicTableData.xml"
 * and parse it into a HashMap of Elements with the Elements' names as the keys.
 * <p>
 * NOTE: The XML file must be particularly formatted to function correctly.
 * Unfortunately, there are not specific specifications that can be stated.  However,
 * here is a brief overview:
 * <p>
 *	<b>&lt;PERIODIC_TABLE&gt;</b> - Root element.<br />
 *	<b>&lt;ATOM&gt;</b> - Each ATOM element contains a one-level deep set of data, 
 *  					  including such elements as &lt;NAME&gt; and &lt;ATOMIC_WEIGHT&gt;.<br />
 *	<b>&lt;NAME&gt;, &lt;SYMBOL&gt;</b> - One level below &lt;ATOM&gt;, these are String values.  
 *  								  For example, "Sodium" would be a &lt;NAME&gt; and "Na" would be its &lt;SYMBOL&gt;.<br />
 *  <b>&lt;ATOMIC_NUMBER&gt;</b> - The only Integer value.  Atomic numbers cannot be anything
 *  							   but positive integers.<br />
 *	<b>&lt;DENSITY&gt;,&lt;BOILING_POINT&gt;,etc.</b> - The titles of most of these elements
 *  								  are self-explanatory. They are located one level deeper
 *  								  than &lt;ATOM&gt;.  &lt;NAME&gt; and 
 *  								  &lt;SYMBOL&gt; are Strings, &lt;ATOMIC_NUMBER&gt; is an
 *  								  integer, and the rest are doubles.<br />
 *	<b>&lt;OXIDATION_STATES&gt;</b> - This element is on the same level as &lt;DENSITY&gt; and
 *									  the others.  It is a String value that is parsed
 *									  into a Set of Integers.  While it is preferred
 *									  the states are in simple CSV form, such as "3, -3",
 *									  a shortened "+/-3" notation is also acceptable.<br />
 *
 * @see Element
 * @see org.jdom.Element
 * @author Scott Kolodziej
 */

public class PeriodicTable
{
	// table stores Elements in the fashion <Element name, Element class>
	private static HashMap<Object, Element> nameTable;
	private static HashMap<Object,Element> symbolTable;
	private static HashMap<Object,Element> atomicNumberTable;
	private static HashMap<Object,Element> atomicMassTable;
	private static HashMap<Object,Element> atomicVolumeTable;
	private static HashMap<Object,Element> boilingPointTable;
	private static HashMap<Object,Element> meltingPointTable;
	private static HashMap<Object,Element> specificHeatCapacityTable;
	private static HashMap<Object,Element> densityTable;
	private static HashMap<Object,Element> electronegativityTable;
	private static HashMap<Object,Element> atomicRadiusTable;
	private static HashMap<Object,Element> ionizationPotentialTable;
	private static HashMap<Object,Element> thermalConductivityTable;
	private static HashMap<Object,Element> heatOfFusionTable;
	private static HashMap<Object,Element> heatOfVaporizationTable;
	private static HashMap<Object,Element> covalentRadiusTable;
	
	private static TreeSet<String> acceptablePropertySet;
	
	public PeriodicTable()
	{
	}
	
	static
	{
		//SChemPCore.progress.report("Loading Periodic Table...");
		nameTable = new HashMap<Object,Element>();
		symbolTable = new HashMap<Object,Element>();
		atomicNumberTable = new HashMap<Object,Element>();
		atomicMassTable = new HashMap<Object,Element>();
		atomicVolumeTable = new HashMap<Object,Element>();
		boilingPointTable = new HashMap<Object,Element>();
		meltingPointTable = new HashMap<Object,Element>();
		specificHeatCapacityTable = new HashMap<Object,Element>();
		densityTable = new HashMap<Object,Element>();
		electronegativityTable = new HashMap<Object,Element>();
		atomicRadiusTable = new HashMap<Object,Element>();
		ionizationPotentialTable = new HashMap<Object,Element>();
		thermalConductivityTable = new HashMap<Object,Element>();
		heatOfFusionTable = new HashMap<Object,Element>();
		heatOfVaporizationTable = new HashMap<Object,Element>();
		covalentRadiusTable = new HashMap<Object,Element>();
		
		//SChemPCore.progress.report("Loading XML...");
		
		Document doc = null;
		try{
			try{
				SAXBuilder builder = new SAXBuilder();
				doc = builder.build("xml/PeriodicTableData.xml");
			}
			catch(JDOMException jde){
				System.err.println("JDOM Exception Thrown: Error Reading Periodic Table XML File");
				throw new SChemPException("JDOM: Error Reading Periodic Table XML File");
				}
			catch(IOException ioe){
				System.err.println("IOException Thrown: Error Reading Periodic Table XML File");
				throw new SChemPException("I/O: Error Reading Periodic Table XML File");
				}
		}
		catch(SChemPException se){}
		
		
		// Creating an Iterator for the <ATOM> trees.
		IteratorIterable<org.jdom2.Element> iter = doc.getDescendants(new ElementFilter("ATOM"));
		
		while(iter.hasNext())
		{
			String name = null;
			String symbol = null;
			int atomicNumber = -1;
			double atomicMass = -1;
			double atomicVolume = -1;
			Set<Integer> oxidationStates = new HashSet<Integer>();
			String electronConfiguration = null;
			double normalBoilingPoint = -1;
			double normalMeltingPoint = -1;
			double specificHeatCapacity = -1;
			double density = -1;
			double electronegativity = -1;
			double atomicRadius = -1;
			double ionizationPotential = -1;
			double thermalConductivity = -1;
			double heatOfFusion = -1;
			double heatOfVaporization = -1;
			double covalentRadius = -1;
			
			
			org.jdom2.Element element = (org.jdom2.Element)iter.next();
			
			if(element.getChild("NAME") != null)
				name = element.getChild("NAME").getText();
			if(element.getChild("SYMBOL") != null)
				symbol = element.getChild("SYMBOL").getText();
			if(element.getChild("ATOMIC_NUMBER") != null)
				atomicNumber = Integer.parseInt(element.getChild("ATOMIC_NUMBER").getText());
			if(element.getChild("ATOMIC_WEIGHT") != null)
				atomicMass = Double.parseDouble(element.getChild("ATOMIC_WEIGHT").getText());
			if(element.getChild("ATOMIC_VOLUME") != null)
				atomicVolume = Double.parseDouble(element.getChild("ATOMIC_VOLUME").getText());
			if(element.getChild("OXIDATION_STATES") != null)
			{
				String temp = element.getChild("OXIDATION_STATES").getText();
			
				StringTokenizer token = new StringTokenizer(temp, " ,");
				while(token.hasMoreTokens())
				{
					String errorCatcher = token.nextToken();
					if(errorCatcher.length() < 2)
						oxidationStates.add(Integer.parseInt(errorCatcher));
					else if(errorCatcher.charAt(1) != '/')
						oxidationStates.add(Integer.parseInt(errorCatcher));
					else
					{
						int num = Integer.parseInt(errorCatcher.substring(3,errorCatcher.length()));
						oxidationStates.add(-num);
						oxidationStates.add(num);
					}
				}
			}
			if(element.getChild("ELECTRON_CONFIGURATION") != null)
				electronConfiguration = element.getChild("ELECTRON_CONFIGURATION").getText();
			if(element.getChild("BOILING_POINT") != null)
				normalBoilingPoint = Double.parseDouble(element.getChild("BOILING_POINT").getText());
			if(element.getChild("MELTING_POINT") != null)
				normalMeltingPoint = Double.parseDouble(element.getChild("MELTING_POINT").getText());
			if(element.getChild("SPECIFIC_HEAT_CAPACITY") != null)
				specificHeatCapacity = Double.parseDouble(element.getChild("SPECIFIC_HEAT_CAPACITY").getText());
			if(element.getChild("DENSITY") != null)
				density = Double.parseDouble((element).getChild("DENSITY").getText());
			if(element.getChild("ELECTRONEGATIVITY") != null)
				electronegativity = Double.parseDouble(element.getChild("ELECTRONEGATIVITY").getText());
			if(element.getChild("ATOMIC_RADIUS") != null)
				atomicRadius = Double.parseDouble(element.getChild("ATOMIC_RADIUS").getText());
			if(element.getChild("IONIZATION_POTENTIAL") != null)
				ionizationPotential = Double.parseDouble(element.getChild("IONIZATION_POTENTIAL").getText());
			if(element.getChild("THERMAL_CONDUCTIVITY") != null)
				thermalConductivity = Double.parseDouble(element.getChild("THERMAL_CONDUCTIVITY").getText());
			if(element.getChild("HEAT_OF_FUSION") != null)
				heatOfFusion = Double.parseDouble(element.getChild("HEAT_OF_FUSION").getText());
			if(element.getChild("HEAT_OF_VAPORIZATION") != null)
				heatOfVaporization = Double.parseDouble(element.getChild("HEAT_OF_VAPORIZATION").getText());
			if(element.getChild("COVALENT_RADIUS") != null)
				covalentRadius = Double.parseDouble(element.getChild("COVALENT_RADIUS").getText());
			
			Element newElement = new Element(name, symbol, atomicNumber, 
				atomicMass, atomicVolume, oxidationStates, electronConfiguration,
				normalBoilingPoint, normalMeltingPoint, density, electronegativity,
				atomicRadius, specificHeatCapacity, ionizationPotential,
				thermalConductivity, heatOfFusion, heatOfVaporization,
				covalentRadius);
			
			nameTable.put(name, newElement);
			symbolTable.put(symbol, newElement);
			atomicNumberTable.put(new Integer(atomicNumber), newElement);
			atomicMassTable.put(new Double(atomicMass), newElement);
			atomicVolumeTable.put(new Double(atomicVolume), newElement);
			boilingPointTable.put(new Double(normalBoilingPoint), newElement);
			meltingPointTable.put(new Double(normalMeltingPoint), newElement);
			specificHeatCapacityTable.put(new Double(specificHeatCapacity), newElement);
			densityTable.put(new Double(density), newElement);
			electronegativityTable.put(new Double(electronegativity), newElement);
			atomicRadiusTable.put(new Double(atomicRadius), newElement);
			ionizationPotentialTable.put(new Double(ionizationPotential), newElement);
			thermalConductivityTable.put(new Double(thermalConductivity), newElement);
			heatOfFusionTable.put(new Double(heatOfFusion), newElement);
			heatOfVaporizationTable.put(new Double(heatOfVaporization), newElement);
			covalentRadiusTable.put(new Double(covalentRadius), newElement);
			
			String[] acceptableProperties = {"None", "NONE", "Name", "Symbol", "Atomic Number", "ATOMIC_NUMBER",
					"Atomic Mass", "ATOMIC_MASS", "Atomic Volume", "ATOMIC_VOLUME", "Boiling Point",
					"BOILING_POINT", "Melting Point", "MELTING_POINT", "Specific Heat Capacity",
					"SPECIFIC_HEAT_CAPACITY", "Density", "Electronegativity", "Atomic Radius",
					"ATOMIC_RADIUS", "Ionization Potential", "IONIZATION_POTENTIAL", 
					"Thermal Conductivity", "THERMAL_CONDUCTIVITY", "Heat of Fusion",
					"HEAT_OF_FUSION", "Heat of Vaporization", "HEAT_OF_VAPORIZATION",
					"Covalent Radius", "COVALENT_RADIUS"};
			
			acceptablePropertySet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
			
			for(int k = 0; k < acceptableProperties.length; k++)
			{
				acceptablePropertySet.add(acceptableProperties[k]);
			}
			
			/*
			table.put(symbol, newElement);
			table.put(new Integer(atomicNumber), newElement);
			table.put(new Double(atomicMass), newElement);
			
			table.put(new Double(atomicVolume), newElement);
			table.put(oxidationStates, newElement);
			table.put(electronConfiguration, newElement);
			table.put(new Double(normalBoilingPoint), newElement);
			table.put(new Double(normalMeltingPoint), newElement);
			table.put(new Double(density), newElement);
			table.put(new Double(electronegativity), newElement);
			table.put(new Double(atomicRadius), newElement);
			table.put(new Double(specificHeatCapacity), newElement);
			table.put(new Double(ionizationPotential), newElement);
			table.put(new Double(thermalConductivity), newElement);
			table.put(new Double(heatOfFusion), newElement);
			table.put(new Double(heatOfVaporization), newElement);
			table.put(new Double(covalentRadius), newElement);
			*/
		}
		//SChemPCore.progress.report();
	}
	
	/**
	 * Returns an Element from the Periodic Table.
	 * @param nameOrSymbol The name or symbol of the element.
	 * @param property The attribute (either "NAME" or "SYMBOL") corresponding to the nameOrSymbol argument.
	 * @return The Element from the Periodic Table that matches the name or symbol supplied
	 * as the argument.
	 * 
	 * @see Element
	 */
	
	public static Element getElement(String nameOrSymbol, String property) throws SChemPException
	{
		if(!acceptablePropertySet.contains(property))
		{
			throw new SChemPException("No Such Property: " + property);
		}
		
		Element e = null;
		
		if(property.equalsIgnoreCase("NAME"))
		{
			e = nameTable.get(nameOrSymbol);
		}
		else if(property.equalsIgnoreCase("SYMBOL"))
		{
			e = symbolTable.get(nameOrSymbol);
		}
		
		if(e == null)
			throw new SChemPException("No Such Element With Property: " + nameOrSymbol + " " + property);
		
		return e;
	}
	
	/**
	 * Returns an Element from the Periodic Table.
	 * @param num The value of the attribute you are searching for.
	 * @param property The property ("ATOMIC_NUMBER", "ATOMIC_MASS", etc.) corresponding to the num argument.
	 * @return The Element from the Periodic Table that matches the name or symbol supplied
	 * as the argument.
	 * 
	 * @throws SChemPException
	 * @see Element
	 */
	public static Element getElement(double num, String property) throws SChemPException
	{
		Element e = null;
		
		Map<Object,Element> table = getTable(property);
		
		if(table == null)
			return null;
		
		if(property.equalsIgnoreCase("ATOMIC_NUMBER") ||
				property.equalsIgnoreCase("Atomic Number"))
		{
			e = (Element)table.get(new Integer((int)num));
		}
		else
		{
			e = (Element)table.get(new Double(num));
		}
		
		if(e == null)
			throw new SChemPException("No Such Element With Property: " + property + " " + num);
		
		return e;
	}
	
	/**
	 * Determines if the given property is a valid property of an Element.
	 * @param property
	 * @return A boolean value corresponding to the validity of the property
	 */
	public static boolean isAcceptableProperty(String property)
	{
		boolean acceptable = false;
		
		if(property != null && acceptablePropertySet.contains(property))
			acceptable = true;
		
		return acceptable;
	}
	
	/**
	 * Finds the highest (or largest) value for the given property in the Periodic Table.
	 * @param property The property of the Elements to be considered.
	 * @return The highest (or largest) value for the given property.
	 * @throws SChemPException
	 */
	public static double getHigh(String property) throws SChemPException
	{
		double high = 0;
		
		HashMap<Object,Element> propertyTable = getTable(property);
		if(propertyTable == null)
			return 1;
		
		Object[] array = propertyTable.keySet().toArray();
		Arrays.sort(array);
		if(array[0] instanceof Double)
			high = ((Double)array[array.length-1]).doubleValue();
		else
			high = ((Integer)array[array.length-1]).intValue();
		
		return high;
	}
	
	/**
	 * Finds the lowest (or smallest) value for the given property in the Periodic Table.
	 * @param property The property of the Elements to be considered.
	 * @return The lowest (or smallest) value for the given property.
	 * @throws SChemPException
	 */
	public static double getLow(String property) throws SChemPException
	{
		double low = 0;
		
		HashMap<Object,Element> propertyTable = getTable(property);
		if(propertyTable == null)
			return 1;
		
		Object[] array = propertyTable.keySet().toArray();
		Arrays.sort(array);
		
		if(array[0] instanceof Double)
		{
			Double[] dArray = new Double[array.length];
			
			for(int k = 0; k < dArray.length; k++)
			{
				dArray[k] = (Double)array[k];
			}
			
			int index = 0;
			while(dArray[index].equals(new Double(-1)) && index < dArray.length)
			{
				index++;
			}
			low = ((Double)dArray[index]).doubleValue();
		}
		else
		{
			Integer[] iArray = new Integer[array.length];
			
			for(int k = 0; k < iArray.length; k++)
			{
				iArray[k] = (Integer)array[k];
			}
			
			int index = 0;
			while(iArray[index].equals(new Integer(-1)) && index < iArray.length)
			{
				index++;
			}
			low = ((Integer)iArray[index]).intValue();
		}
		
		return low;
	}
	
	/**
	 * Finds the Element that corresponds to the highest (or largest) value for the 
	 * given property in the Periodic Table.  Equivalent to getElement(getHigh(property), property);
	 * @param property The property considered.
	 * @return The Element with the highest value for the given property.
	 * @throws SChemPException
	 * @see #getHigh(String property)
	 */
	public static Element getHighElement(String property) throws SChemPException
	{
		double high = getHigh(property);
		Element highElement = getElement(high, property);
		
		return highElement;
	}
	
	/**
	 * Finds the Element that corresponds to the lowest (or smallest) value for the
	 * given property in the Periodic Table.  Equivalent to getElement(getLow(property), property);
	 * @param property The property considered.
	 * @return The Element with the lowest value for the given property.
	 * @throws SChemPException
	 */
	public static Element getLowElement(String property) throws SChemPException
	{
		double low = getLow(property);
		Element lowElement = getElement(low, property);
		
		return lowElement;
	}
	
	/**
	 * Helper method to determine which Map to use with a given property.
	 * @param property
	 * @return HashMap<Value, Element> keyed to the values of the property given.
	 * @throws SChemPException
	 */
	private static HashMap<Object, Element> getTable(String property) throws SChemPException
	{
		if(!acceptablePropertySet.contains(property))
			throw new SChemPException("No Such Property: " + property);
		
		HashMap<Object, Element> propertyTable = null;
		
		if(property.equalsIgnoreCase("Name"))
		{
			propertyTable = nameTable;
		}
		else if(property.equalsIgnoreCase("Symbol"))
		{
			propertyTable = symbolTable;
		}
		else if(property.equalsIgnoreCase("Atomic Number") ||
				property.equalsIgnoreCase("ATOMIC_NUMBER"))
		{
			propertyTable = atomicNumberTable;
		}
		else if(property.equalsIgnoreCase("Atomic Mass") ||
				property.equalsIgnoreCase("ATOMIC_MASS"))
		{
			propertyTable = atomicMassTable;
		}
		else if(property.equalsIgnoreCase("Atomic Volume") ||
				property.equalsIgnoreCase("ATOMIC_VOLUME"))
		{
			propertyTable = atomicVolumeTable;
		}
		else if(property.equalsIgnoreCase("Boiling Point") ||
				property.equalsIgnoreCase("BOILING_POINT"))
		{
			propertyTable = boilingPointTable;
		}
		else if(property.equalsIgnoreCase("Melting Point") ||
				property.equalsIgnoreCase("MELTING_POINT"))
		{
			propertyTable = meltingPointTable;
		}
		else if(property.equalsIgnoreCase("Specific Heat Capacity") ||
				property.equalsIgnoreCase("SPECIFIC_HEAT_CAPACITY"))
		{
			propertyTable = specificHeatCapacityTable;
		}
		else if(property.equalsIgnoreCase("Density"))
		{
			propertyTable = densityTable;
		}
		else if(property.equalsIgnoreCase("Electronegativity"))
		{
			propertyTable = electronegativityTable;
		}
		else if(property.equalsIgnoreCase("Atomic Radius") ||
				property.equalsIgnoreCase("ATOMIC_RADIUS"))
		{
			propertyTable = atomicRadiusTable;
		}
		else if(property.equalsIgnoreCase("Ionization Potential") ||
				property.equalsIgnoreCase("IONIZATION_POTENTIAL"))
		{
			propertyTable = ionizationPotentialTable;
		}
		else if(property.equalsIgnoreCase("Thermal Conductivity") ||
				property.equalsIgnoreCase("THERMAL_CONDUCTIVITY"))
		{
			propertyTable = thermalConductivityTable;
		}
		else if(property.equalsIgnoreCase("Heat of Fusion") ||
				property.equalsIgnoreCase("HEAT_OF_FUSION"))
		{
			propertyTable = heatOfFusionTable;
		}
		else if(property.equalsIgnoreCase("Heat of Vaporization") ||
				property.equalsIgnoreCase("HEAT_OF_VAPORIZATION"))
		{
			propertyTable = heatOfVaporizationTable;
		}
		else if(property.equalsIgnoreCase("Covalent Radius") ||
				property.equalsIgnoreCase("COVALENT_RADIUS"))
		{
			propertyTable = covalentRadiusTable;
		}
		
		return propertyTable;
	}
	
	/**
	 * The number of elements in the Periodic Table.
	 * @return The Periodic Table's size (number of elements).
	 */
	public static int getSize()
	{
		return symbolTable.size();
	}
}