package SChemP; 

public class Wavefunction
{
	int n;
	int l;
	int m;
	double theta;
	double phi;
	
	public Wavefunction(Electron e)
	{
		n = e.getN();
		l = e.getL();
		m = e.getM();
		theta = 0;
		phi = 0;
	}
	
	public Wavefunction(int n, int l, int m)
	{
		this.n = n;
		this.l = l;
		this.m = m;
		theta = 0;
		phi = 0;
	}
	
	public Wavefunction(Electron e, double theta, double phi)
	{
		n = e.getN();
		l = e.getL();
		m = e.getM();
		this.theta = theta;
		this.phi = phi;
	}
	
	public Wavefunction(int n, int l, int m, double theta, double phi)
	{
		this.n = n;
		this.l = l;
		this.m = m;
		this.theta = theta;
		this.phi = phi;
	}
	
	public int getN()
	{
		return n;
	}
	
	public int getL()
	{
		return l;
	}
	
	public int getM()
	{
		return m;
	}
	
	public double calculateWavefunction(double r)
	{
		return QuantumMechanics.calculateHydrogenWavefunction(n, l, m, r, theta, phi);
	}
	
	public double calculateWavefunction(double r, double theta, double phi)
	{
		return QuantumMechanics.calculateHydrogenWavefunction(n, l, m, r, theta, phi);
	}
	
	public double calculateWavefunction(double constant, double r)
	{
		return QuantumMechanics.calculateHydrogenWavefunction(constant, n, l, m, r);
	}
	
	public double calculateWavefunctionConstant()
	{
		return QuantumMechanics.calculateHydrogenWavefunctionConstant(n, l, m, theta, phi);
	}
	
	public double calculateWavefunctionConstant(double theta, double phi)
	{
		return QuantumMechanics.calculateHydrogenWavefunctionConstant(n, l, m, theta, phi);
	}
}
