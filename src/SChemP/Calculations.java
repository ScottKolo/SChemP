package SChemP; 

import static java.lang.Math.*;
import java.math.BigInteger;

/**
 * The Calculations class is a utility class with a collection
 * of static functions.
 * <p>
 * Most of the functions work in the same manner, which is
 * slightly counter-intuitive, but are simple to use.
 * <p>
 * In short, the argument representing the unknown variable
 * should be sent to the function as null.  For example...
 * <p>
 * If you wish you calculate the volume of a gas using the
 * ideal gas law, assuming you know the gas's pressure, 
 * temperature, and number of moles, the function would 
 * be called...
 * <p>
 * Double volume = idealGasLaw(pressure, null, moles, temperature);
 * <p>
 * 
 * @author Scott Kolodziej
 *
 */
public class Calculations
{
	//	Constants
	public static final double R = 8.314472;
	public static final double Rlatm = 0.08205746;
	public static final int F = 96500;
	public static final BigInteger Avagadro = new BigInteger("602214150000000000000000");
	
	public Calculations()
	{}
	
	public static Double combinedGasLaw(Double p1, Double v1, Double t1, Double p2, Double v2, Double t2)
	{
		boolean[] nulls = new boolean[6];
		nulls[0] = (p1 == null);
		nulls[1] = (v1 == null);
		nulls[2] = (t1 == null);
		nulls[3] = (p2 == null);
		nulls[4] = (v2 == null);
		nulls[5] = (t2 == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// p1 is unknown
			result = p2*v2*t1/(t2*v1);
		}
		else if(nulls[1])
		{
			// v1 is unknown
			result = p2*v2*t1/(t2*p1);
		}
		else if(nulls[2])
		{
			// t1 is unknown
			result = t2*p1*v1/(p2*v2);
		}
		else if(nulls[3])
		{
			// p2 is unknown
			result = p1*v1*t2/(t2*v1);
		}
		else if(nulls[4])
		{
			// v2 is unknown
			result = p1*v1*t2/(t1*p2);
		}
		else if(nulls[5])
		{
			// t2 is unknown
			result = t1*p2*v2/(p1*v1);
		}
		
		return result;
	}
	
	public static Double idealGasLaw(Double p, Double v, Double n, Double t)
	{
		boolean[] nulls = new boolean[4];
		nulls[0] = (p == null);
		nulls[1] = (v == null);
		nulls[2] = (n == null);
		nulls[3] = (t == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// p is unknown
			result = n*Rlatm*t/v;
		}
		else if(nulls[1])
		{
			// v is unknown
			result = n*Rlatm*t/p;
		}
		else if(nulls[2])
		{
			// n is unknown
			result = p*v/(Rlatm*t);
		}
		else if(nulls[3])
		{
			// t is unknown
			result = p*v/(Rlatm*n);
		}
		
		return result;
	}
	
	public static Double beersLaw(Double absorbtivity, Double constant, Double concentration)
	{
		boolean[] nulls = new boolean[3];
		nulls[0] = (absorbtivity == null);
		nulls[1] = (constant == null);
		nulls[2] = (concentration == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// absorbtivity is unknown
			result = constant*concentration;
		}
		else if(nulls[1])
		{
			// constant is unknown
			result = absorbtivity/concentration;
		}
		else if(nulls[2])
		{
			// concentration is unknown
			result = absorbtivity/constant;
		}
		
		return result;
	}
	
	public static Double transmittance(Double transmittance, Double molarAbsorbtivity, Double cuvetteWidth, Double concentration)
	{
		boolean[] nulls = new boolean[4];
		nulls[0] = (transmittance == null);
		nulls[1] = (molarAbsorbtivity == null);
		nulls[2] = (cuvetteWidth == null);
		nulls[3] = (concentration == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// transmittance is unknown
			result = 1/(pow(10,(molarAbsorbtivity*cuvetteWidth*concentration)));
		}
		else if(nulls[1])
		{
			// molarAbsorbtivity is unknown
			result = log10(1/transmittance)/(cuvetteWidth*concentration);
		}
		else if(nulls[2])
		{
			// cuvetteWidth is unknown
			result = log10(1/transmittance)/(molarAbsorbtivity*concentration);
		}
		else if(nulls[3])
		{
			// concentration is unknown
			result = log10(1/transmittance)/(molarAbsorbtivity*cuvetteWidth);
		}
		
		return result;
	}
	
	public static Double absorbtivity(Double absorbtivity, Double molarAbsorbtivity, Double cuvetteWidth, Double concentration)
	{
		boolean[] nulls = new boolean[4];
		nulls[0] = (absorbtivity == null);
		nulls[1] = (molarAbsorbtivity == null);
		nulls[2] = (cuvetteWidth == null);
		nulls[3] = (concentration == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// absorbtivity is unknown
			result = molarAbsorbtivity*cuvetteWidth*concentration;
		}
		else if(nulls[1])
		{
			// molarAbsorbtivity is unknown
			result = absorbtivity/(cuvetteWidth*concentration);
		}
		else if(nulls[2])
		{
			// cuvetteWidth is unknown
			result = absorbtivity/(molarAbsorbtivity*concentration);
		}
		else if(nulls[3])
		{
			// concentration is unknown
			result = absorbtivity/(molarAbsorbtivity*cuvetteWidth);
		}
		
		return result;
	}
	
	public static Double grandDaddy(Double freeEnergy, Double enthalpy, Double temperature, Double entropy)
	{
		boolean[] nulls = new boolean[4];
		nulls[0] = (freeEnergy == null);
		nulls[1] = (enthalpy == null);
		nulls[2] = (temperature == null);
		nulls[3] = (entropy == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// freeEnergy is unknown
			result = enthalpy - temperature*entropy;
		}
		else if(nulls[1])
		{
			// enthalpy is unknown
			result = freeEnergy + temperature*entropy;
		}
		else if(nulls[2])
		{
			// temperature is unknown
			result = -(freeEnergy - enthalpy)/entropy;
		}
		else if(nulls[3])
		{
			// entropy is unknown
			result = -(freeEnergy - enthalpy)/temperature;
		}
		
		return result;
	}
	
	public static Double nernst(Double freeEnergy, Double moles, Double cellPotential)
	{
		//G = -nFE
		
		boolean[] nulls = new boolean[4];
		nulls[0] = (freeEnergy == null);
		nulls[1] = (moles == null);
		nulls[2] = (cellPotential == null);
		
		int nullCount = 0;
		for(int k = 0; k < nulls.length; k++)
		{
			if(nulls[k])
				nullCount++;
		}
		
		if(nullCount != 1)
			return null;
		
		double result = 0;
		
		if(nulls[0])
		{
			// freeEnergy is unknown
			result = -moles*F*cellPotential;
		}
		else if(nulls[1])
		{
			// moles is unknown
			result = -freeEnergy/(F*cellPotential);
		}
		else if(nulls[2])
		{
			// cellPotential is unknown
			result = -freeEnergy/(F*moles);
		}
				
		return result;
	}
	
	public static Double equilibriumConstant(Double[] reactantConcentrations, Double[] reactantCoefficients,
			Double[] productConcentrations, Double[] productCoefficients) throws SChemPException
	{
		int rSize = reactantConcentrations.length;
		int pSize = productConcentrations.length;
		if(reactantCoefficients.length != rSize || productCoefficients.length != pSize)
		{
			throw new SChemPException("Array Size Mismatch in method Calculations.equilibriumConstant");
		}
		
		double keq = 0;
		
		for(int k = 0; k < pSize; k++)
		{
			keq *= pow(productConcentrations[k], productCoefficients[k]);
		}
		for(int k = 0; k < rSize; k++)
		{
			keq /= pow(reactantConcentrations[k], reactantCoefficients[k]);
		}
		
		return keq;
	}
	
	public static Double reactionQuotient(Double[] reactantConcentrations, Double[] reactantCoefficients,
			Double[] productConcentrations, Double[] productCoefficients) throws SChemPException
	{
		return equilibriumConstant(reactantConcentrations, reactantCoefficients,
				productConcentrations, productCoefficients);
	}
	
	//public static Double nernst()
}
