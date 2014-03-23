package SChemP; 

import static java.lang.Math.*;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.complex.*;

public class QuantumMechanics
{
	public static final double h = 6.6260693*pow(10,-34);
	public static final double hBar = 1.0545726*pow(10,-34);
	
	/**
	 * Calculates the Hydrogenic wavefunction for an electron, given its quantum
	 * numbers and spherical angles (in radians) at a specified radius.
	 * @param n Principal quantum number
	 * @param l Azimunthal quantum number
	 * @param m Magnetic quantum number
	 * @param r Radius (from the origin/nucleus in Bohr radii)
	 * @param theta Angle in the x-y plane, in radians (0 \u2264 theta < 2\u03C0)
	 * @param phi Angle from the z axis, in radians (0 \u2264 phi < \u03C0)
	 * @return The value of the wavefunction given the above parameters.
	 */
	public static double calculateHydrogenWavefunction(int n, int l, int m, double r, double theta, double phi)
	{
		double result = 0;
		//double massE = 1;
		//double nuclearMass = 1;
		//double mu = ((massE)*(nuclearMass))/(massE + nuclearMass);
		double aNaught = 1;//hBar/(mu*pow(E,2));
		double x = 2*r/(n*aNaught);
		
		Complex e = new Complex(E,0);
		Complex mphi = new Complex(m*phi,0);
		mphi = mphi.multiply(Complex.I);
		
		result = -(2/(pow(n,2)*pow(aNaught,(double)(2.0/3.0)))) *
			pow(((factorial(n-l-1) * (2*l + 1)*factorial(l - abs(m))) /
					pow(factorial(n+l),3)*4*PI*factorial(l + abs(m))), 0.5) *
					pow(E, -x/2)*pow(x,l)*(calculateLaguerrePolynomial(2*l+1,n+l,x))*
					associatedLegendreFunction(l,m,cos(theta))*(e.pow(mphi)).getReal();
		
		
		return result;
	}
	
	public static double calculateHydrogenWavefunctionConstant(int n, int l, int m, double theta, double phi)
	{
		double result = 0;
		//double massE = 1;
		//double nuclearMass = 1;
		//double mu = ((massE)*(nuclearMass))/(massE + nuclearMass);
		double aNaught = 1;//hBar/(mu*pow(E,2));
		
		Complex e = new Complex(E,0);
		Complex mphi = new Complex(m*phi,0);
		mphi = mphi.multiply(Complex.I);
		
		result = -(2/(pow(n,2)*pow(aNaught,(double)(2.0/3.0)))) *
			pow(((factorial(n-l-1) * (2*l + 1)*factorial(l - abs(m))) /
					pow(factorial(n+l),3)*4*PI*factorial(l + abs(m))), 0.5)*
					associatedLegendreFunction(l,m,cos(theta))*(e.pow(mphi)).getReal();
		
		
		return result;
	}
	
	public static double calculateHydrogenWavefunction(double constant, int n, int l, int m, double r)
	{
		double aNaught = 1;//hBar/(mu*pow(E,2));
		double x = 2*r/(n*aNaught);

		return (constant*pow(E, -x/2)*pow(x,l)*(calculateLaguerrePolynomial(2*l+1,n+l,x)));
	}
	
	/**
	 * Calculates the Hydrogenic wavefunction for an Electron and 
	 * spherical angles (in radians) at a specified radius.
	 * @param e The given Electron
	 * @param r Radius (from the origin/nucleus in Bohr radii)
	 * @param theta Angle in the x-y plane, in radians (0 \u2264 theta < 2\u03C0)
	 * @param phi Angle from the z axis, in radians (0 \u2264 phi < \u03C0)
	 * @return The value of the wavefunction given the above parameters.
	 */
	public static double calculateHydrogenWavefunction(Electron e, double r, double theta, double phi)
	{
		return calculateHydrogenWavefunction(e.getN(), e.getL(), e.getM(), r, theta, phi);
	}
	
	public static double legendreFunction(int n, double xi)
	{
		int limit = 0;
		if(n%2 == 0)
		{
			limit = n/2;
		}
		else
		{
			limit = (n-1)/2;
		}
		
		double result = 0;
		
		for(int k = 0; k < limit; k++)
		{
			result += pow(-1,k) * factorial(2*n-2*k) * pow(xi,n-2*k) /
				(pow(2,n)*factorial(n-k)*factorial(k)*factorial(n-2*k));
		}
		
		return result;
	}
	
	public static double associatedLegendreFunction(int n, int m, double xi)
	{
		int limit = 0;
		m = abs(m);
		
		if((n-m)%2 == 0)
		{
			limit = (n-m)/2;
		}
		else
		{
			limit = (n-m-1)/2;
		}
		
		double result = 0;
		
		for(int k = 0; k <= limit; k++)
		{
			result += pow(-1,k) * factorial(2*n-2*k) * pow(xi,n-m-2*k) /
				(pow(2,n)*factorial(n-k)*factorial(k)*factorial(n-m-2*k));
		}
		
		return (result * pow(1-pow(xi,2),m/2));
	}
	
	public static double calculateRadialWavefunction(int n, int l, double r)
	{
		double aNaught = 1;
		double x = 2*r/(n*aNaught);
		
		double result = (-2/(pow(n,2)*pow(aNaught,(double)(2.0/3.0)))) *
			(pow(factorial(n-l-1) / pow(factorial(n+l),3),0.5)) *
				pow(E,-x/2) * pow(x,l) * calculateLaguerrePolynomial(2*l+1, n+l, x);
		
		return result;
	}
	
	
	public static double calculateLaguerrePolynomial(int t, int s, double x)
	{
		double result = 0;
		
		int difference = (s-t);
		
		for(int k = 0; k <= difference; k++)
		{
			result += pow(-1,k)*pow(factorial(s),2)*pow(x,k)/
				(factorial(s-t-k)*factorial(t+k)*factorial(k));
		}
		
		return -result;
	}
	
	/**
	 * A simple function to calculate the factorial of an integer.
	 * @param x
	 * @return
	 * @see org.apache.commons.math.util.MathUtils#factorial(int)
	 */
	private static double factorial(int x)
	{
		return ArithmeticUtils.factorialDouble(x);
	}
}
