package SChemP; 

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.math.plot.Plot2DPanel;
import org.math.plot.plots.LinePlot;

/**
 * An extensive class to display quantum mechanical properties
 * of atomic orbitals, including their wavefunctions,
 * electron densities, probability distributions, and 
 * graphical cross-sections.
 * 
 * @author Scott Kolodziej
 *
 */
public class QuantumOrbitalsPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5716488076275945192L;
	private int numberOfDataPoints;
	private Wavefunction psi;
	private int numberOfPanels;
	private double[] limits;
	Plot2DPanel wavefunctionPlot;
	Plot2DPanel electronDensityPlot;
	Plot2DPanel radialProbDistPlot;
	OrbitalCrossSectionPanel orbitalCrossSectionPanel;
	OrbitalCrossSectionPanel orbitalCrossSectionPanelAlone;
	//Plot3DPanel orbitalPlot;
	
	public QuantumOrbitalsPanel()
	{
		super();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel quadPane = new JPanel();
		//JPanel orbPane = new JPanel();
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout());
		
		psi = new Wavefunction(1,0,0,0,PI/2.0);
		numberOfPanels = 4;
		limits = new double[numberOfPanels];
		limits[0] = 5;
		limits[1] = 4;
		limits[2] = 6;
		limits[3] = 6;
		
		// create your PlotPanel (you can use it as a JPanel)
		wavefunctionPlot = getWavefunctionPlotPanel(psi,0,limits[0],200);
		electronDensityPlot = getElectronDensityPlotPanel(psi,0,limits[1],200);
		radialProbDistPlot = getRadialProbabilityDistributionPlotPanel(psi,0,limits[2],200);
		orbitalCrossSectionPanel = getOrbitalPlotPanel(psi,limits[3]);
		orbitalCrossSectionPanelAlone = getOrbitalPlotPanel(psi,limits[3]);
		//orbitalPlot = getOrbitalPlotPanel(psi);
		
		this.setLayout(new BorderLayout());
		
		center.setLayout(new GridLayout(2,2));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panelOrb = new JPanel();
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BorderLayout());
		panel3.setLayout(new BorderLayout());
		panel4.setLayout(new BorderLayout());
		panelOrb.setLayout(new BorderLayout());
		
		panel1.add(wavefunctionPlot, BorderLayout.CENTER);
		panel1.add(new PlotSettingsPanel(1), BorderLayout.SOUTH);
		panel2.add(electronDensityPlot, BorderLayout.CENTER);
		panel2.add(new PlotSettingsPanel(2), BorderLayout.SOUTH);
		panel3.add(radialProbDistPlot, BorderLayout.CENTER);
		panel3.add(new PlotSettingsPanel(3), BorderLayout.SOUTH);
		panel4.add(orbitalCrossSectionPanel, BorderLayout.CENTER);
		panel4.add(new PlotSettingsPanel(4), BorderLayout.SOUTH);
		panelOrb.add(orbitalCrossSectionPanelAlone, BorderLayout.CENTER);
		panelOrb.add(new PlotSettingsPanel(4), BorderLayout.SOUTH);
		
		center.add(panel1);
		center.add(panel2);
		center.add(panel3);
		center.add(panel4);
		
		/*center.add(wavefunctionPlot);
		center.add(electronDensityPlot);
		center.add(radialProbDistPlot);
		center.add(orbitalCrossSectionPanel);
		*/
		//center.add(orbitalPlot);
		
		quadPane.setLayout(new BorderLayout());
		quadPane.add(center, BorderLayout.CENTER);
		
		quadPane.add(new OrbitalSettingsPanel(), BorderLayout.SOUTH);
		
		tabbedPane.add("Quad", quadPane);
		tabbedPane.add("Orbital", panelOrb);
		
		this.add(tabbedPane);
	}
	
	public void graphOrbital(int n, int l, int m)
	{
		psi = new Wavefunction(n,l,m,0,0);
		wavefunctionPlot.removeAllPlots();
		electronDensityPlot.removeAllPlots();
		radialProbDistPlot.removeAllPlots();
		//orbitalPlot.removeAllPlots();
		
		double[][] wavefunctionArray = reflection(getWavefunctionPlotData(psi, 0, limits[0], wavefunctionPlot.getWidth()/2));
		double[][] wavefunctionSquaredArray = reflection(getElectronDensityPlotData(psi, 0, limits[1], electronDensityPlot.getWidth()/2));
		double[][] probDistArray = reflection(getRadialProbabilityDistributionPlotData(psi, 0, limits[2], radialProbDistPlot.getWidth()/2));
		// define the legend position
		//electronDensityPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		wavefunctionPlot.addLinePlot("Wavefunction", wavefunctionArray[0], wavefunctionArray[1]);
		electronDensityPlot.addLinePlot("Probability Density", wavefunctionSquaredArray[0], wavefunctionSquaredArray[1]);
		radialProbDistPlot.addLinePlot("Radial Probability", probDistArray[0], probDistArray[1]);
		orbitalCrossSectionPanel = getOrbitalPlotPanel(psi, limits[3]);
		orbitalCrossSectionPanelAlone = getOrbitalPlotPanel(psi, limits[3]);
		
		wavefunctionPlot.setAutoBounds();
		electronDensityPlot.setAutoBounds();
		radialProbDistPlot.setAutoBounds();
		
		// define the legend position
		//radialProbDistPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		
		//System.out.println(array[0][0]);
		//electronDensityPlot.add(makeWavefunctionSquaredPlot(psi,0,2*n,50));
		//radialProbDistPlot = radialProbabilityDistributionPlot(psi,0,10*n,50);
		
		wavefunctionPlot.repaint();
		electronDensityPlot.repaint();
		radialProbDistPlot.repaint();
		this.repaint();
	}
	
	public void graphOrbital(int n, int l, int m, double theta, double phi)
	{
		psi = new Wavefunction(n,l,m,theta,phi);
		wavefunctionPlot.removeAllPlots();
		electronDensityPlot.removeAllPlots();
		radialProbDistPlot.removeAllPlots();
		//orbitalCrossSectionPanel = null;
		//orbitalPlot.removeAllPlots();
		
		double[][] wavefunctionArray = reflection(getWavefunctionPlotData(psi, 0, limits[0], wavefunctionPlot.getWidth()/2));
		double[][] wavefunctionSquaredArray = reflection(getElectronDensityPlotData(psi, 0, limits[1], electronDensityPlot.getWidth()/2));
		double[][] probDistArray = reflection(getRadialProbabilityDistributionPlotData(psi, 0, limits[2], radialProbDistPlot.getWidth()/2));
		// define the legend position
		//electronDensityPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		wavefunctionPlot.addLinePlot("Wavefunction", wavefunctionArray[0], wavefunctionArray[1]);
		electronDensityPlot.addLinePlot("Probability Density", wavefunctionSquaredArray[0], wavefunctionSquaredArray[1]);
		radialProbDistPlot.addLinePlot("Radial Probability", probDistArray[0], probDistArray[1]);
		orbitalCrossSectionPanel.setData(getOrbitalPlotData(psi,limits[3]));// = getOrbitalPlotPanel(psi);
		orbitalCrossSectionPanelAlone.setData(getOrbitalPlotData(psi,limits[3]));
		
		wavefunctionPlot.setAutoBounds();
		electronDensityPlot.setAutoBounds();
		radialProbDistPlot.setAutoBounds();
		
		// define the legend position
		//radialProbDistPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		
		//System.out.println(array[0][0]);
		//electronDensityPlot.add(makeWavefunctionSquaredPlot(psi,0,2*n,50));
		//radialProbDistPlot = radialProbabilityDistributionPlot(psi,0,10*n,50);
		
		wavefunctionPlot.repaint();
		electronDensityPlot.repaint();
		radialProbDistPlot.repaint();
		orbitalCrossSectionPanel.repaint();
		orbitalCrossSectionPanelAlone.repaint();
		this.repaint();
	}
	
	public void graphOrbital(int n, int l, int m, double theta, double phi, double start, double end)
	{
		psi = new Wavefunction(n,l,m,theta,phi);
		wavefunctionPlot.removeAllPlots();
		electronDensityPlot.removeAllPlots();
		radialProbDistPlot.removeAllPlots();
		//orbitalCrossSectionPanel = null;
		//orbitalPlot.removeAllPlots();
		
		double[][] wavefunctionArray = reflection(getWavefunctionPlotData(psi, start, end, wavefunctionPlot.getWidth()/2));
		double[][] wavefunctionSquaredArray = reflection(getElectronDensityPlotData(psi, start, end, electronDensityPlot.getWidth()/2));
		double[][] probDistArray = reflection(getRadialProbabilityDistributionPlotData(psi, start, end, radialProbDistPlot.getWidth()/2));
		// define the legend position
		//electronDensityPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		wavefunctionPlot.addLinePlot("Wavefunction", wavefunctionArray[0], wavefunctionArray[1]);
		electronDensityPlot.addLinePlot("Probability Density", wavefunctionSquaredArray[0], wavefunctionSquaredArray[1]);
		radialProbDistPlot.addLinePlot("Radial Probability", probDistArray[0], probDistArray[1]);
		orbitalCrossSectionPanel.setData(getOrbitalPlotData(psi,end));// = getOrbitalPlotPanel(psi);
		orbitalCrossSectionPanelAlone.setData(getOrbitalPlotData(psi,end));
		
		wavefunctionPlot.setAutoBounds();
		electronDensityPlot.setAutoBounds();
		radialProbDistPlot.setAutoBounds();
		
		// define the legend position
		//radialProbDistPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		
		//System.out.println(array[0][0]);
		//electronDensityPlot.add(makeWavefunctionSquaredPlot(psi,0,2*n,50));
		//radialProbDistPlot = radialProbabilityDistributionPlot(psi,0,10*n,50);
		
		wavefunctionPlot.repaint();
		electronDensityPlot.repaint();
		radialProbDistPlot.repaint();
		orbitalCrossSectionPanel.repaint();
		orbitalCrossSectionPanelAlone.repaint();
		this.repaint();
	}
	
	private double[][] reflection(double[][] x)
	{
		double[][] reflected = new double[x[0].length][x.length];
		
		for(int r = 0; r < x.length; r++)
		{
			for(int c = 0; c < x[0].length; c++)
			{
				reflected[c][r] = x[r][c];
			}
		}
		
		return reflected;
	}
	
	public static Plot2DPanel getWavefunctionPlotPanel(Wavefunction psi, double start, double end, int numPoints)
	{
		Plot2DPanel wavefunctionPlot = new Plot2DPanel();
		
		wavefunctionPlot.addPlot(getWavefunctionPlot(psi, start, end, numPoints));
		
		// define the legend position
		wavefunctionPlot.addLegend("SOUTH");
		
		return wavefunctionPlot;
	}
	
	public static LinePlot getWavefunctionPlot(Wavefunction psi, double start, double end, int numPoints)
	{
		// define your data
		double[][] array = getWavefunctionPlotData(psi, start, end, numPoints);
		
		LinePlot wavefunctionPlot = new LinePlot("Wavefunction", Color.BLUE, array);
		
		return wavefunctionPlot;
	}
	
	public static double[][] getWavefunctionPlotData(Wavefunction psi, double start, double end, int numPoints)
	{
		double[][] wavefunctionArray = new double[numPoints][2]; 
		
		double increment = (end-start)/(numPoints-1);
		int counter = 0;
		double constant = psi.calculateWavefunctionConstant();
		
		for(double k = start; counter < numPoints; k+= increment)
		{
			wavefunctionArray[counter][0] = k;
			wavefunctionArray[counter][1] = psi.calculateWavefunction(constant,k);
			
			counter++;
		}
		
		return wavefunctionArray;
	}
	
	public static Plot2DPanel getElectronDensityPlotPanel(Wavefunction psi, double start, double end, int numPoints)
	{
		Plot2DPanel electronDensityPlot = new Plot2DPanel();
		
		electronDensityPlot.addPlot(getElectronDensityPlot(psi, start, end, numPoints));
		
		// define the legend position
		electronDensityPlot.addLegend("SOUTH");
		
		return electronDensityPlot;
	}
	
	public static LinePlot getElectronDensityPlot(Wavefunction psi, double start, double end, int numPoints)
	{
		// define your data
		double[][] array = getElectronDensityPlotData(psi, start, end, numPoints);
		
		LinePlot electronDensityPlot = new LinePlot("Electron Density", Color.BLUE, array);
		
		return electronDensityPlot;
	}
	
	public static double[][] getElectronDensityPlotData(Wavefunction psi, double start, double end, int numPoints)
	{
		double[][] electronDensityArray = new double[numPoints][2]; 
		
		double increment = (end-start)/(numPoints-1);
		int counter = 0;
		double constant = psi.calculateWavefunctionConstant();
		
		for(double k = start; counter < numPoints; k+= increment)
		{
			electronDensityArray[counter][0] = k;
			electronDensityArray[counter][1] = pow(psi.calculateWavefunction(constant, k),2);
			
			counter++;
		}
		
		return electronDensityArray;
	}
	
	public static Plot2DPanel getRadialProbabilityDistributionPlotPanel(Wavefunction psi, double start, double end, int numPoints)
	{
		Plot2DPanel radialProbabilityDistributionPlot = new Plot2DPanel();
		
		radialProbabilityDistributionPlot.addPlot(getRadialProbabilityDistributionPlot(psi, start, end, numPoints));
		
		// define the legend position
		radialProbabilityDistributionPlot.addLegend("SOUTH");
		
		return radialProbabilityDistributionPlot;
	}
	
	public static LinePlot getRadialProbabilityDistributionPlot(Wavefunction psi, double start, double end, int numPoints)
	{
		// define your data
		double[][] array = getRadialProbabilityDistributionPlotData(psi, start, end, numPoints);
		
		LinePlot radialProbabilityDistributionPlot = new LinePlot("Radial Probability", Color.BLUE, array);
		
		return radialProbabilityDistributionPlot;
	}
	
	public static double[][] getRadialProbabilityDistributionPlotData(Wavefunction psi, double start, double end, int numPoints)
	{
		double[][] radialProbabilityDistributionArray = new double[numPoints][2]; 
		
		double increment = (end-start)/(numPoints-1);
		int counter = 0;
		double constant = psi.calculateWavefunctionConstant();
		
		for(double k = start; counter < numPoints; k+= increment)
		{
			radialProbabilityDistributionArray[counter][0] = k;
			radialProbabilityDistributionArray[counter][1] = ((psi.getL() == 0)?4*PI:1)*pow(k,2)*pow(psi.calculateWavefunction(constant, k),2);
			
			counter++;
		}
		
		return radialProbabilityDistributionArray;
	}
	
	public static OrbitalCrossSectionPanel getOrbitalPlotPanel(Wavefunction psi, double maxRadius)
	{
		return new OrbitalCrossSectionPanel(getOrbitalPlotData(psi, maxRadius));
	}
	
	public static double[][] getOrbitalPlotData(Wavefunction psi, double maxRadius)
	{
		double radiusIncrement = maxRadius/150;//.02;
		//double maxRadius = 3;
		double angleIncrement = .6;
		
		ArrayList<Triplet> orbitalArray = new ArrayList<Triplet>();
		//int counter = 0;
		Random rand = new Random();
		
		for(double k = radiusIncrement; k < maxRadius; k += radiusIncrement)
		{
			for(double angle = angleIncrement/(3+rand.nextInt(5)); angle < 2*PI+0.1; angle += angleIncrement/(double)((double)3+(double)rand.nextInt(5)))
			{
				Point3D cart = sphericalToCartesian(new Point3D(k, angle, PI/2.0));
				orbitalArray.add(new Triplet(cart.x, cart.y, ((psi.getL() == 0)?4*PI:1)*pow(k,2)*pow(psi.calculateWavefunction(k, angle, 0),2)));//pow(psi.calculateWavefunction(k, angle, 0),2)));
				
				//counter++;
			}
		}
		
		double[][] array = new double[orbitalArray.size()][3];
		
		Iterator<Triplet> iter = orbitalArray.iterator();
		
		int count = 0;
		
		while(iter.hasNext())
		{
			Triplet temp = (Triplet)iter.next();
			
			array[count] = temp.data;
			count++;
		}
		
		return array;
	}
	
	public static Point3D[] sphericalToCartesian(Point3D[] sphere)
	{
		Point3D[] cart = new Point3D[sphere.length];
		
		for(int x = 0; x < sphere.length; x++)
		{
			Point3D tempPoint = sphere[x];
			
			cart[x] = sphericalToCartesian(tempPoint);
		}
		
		return cart;
	}
	
	public static Point3D sphericalToCartesian(Point3D sphere)
	{
		Point3D cart = new Point3D();
		
		Point3D tempPoint = sphere;
		
		cart.x = tempPoint.x*cos(tempPoint.y)*sin(tempPoint.z);
		cart.y = tempPoint.x*sin(tempPoint.y)*sin(tempPoint.z);
		cart.z = tempPoint.x*cos(tempPoint.z);
		
		return cart;
	}
	
	public void addOrbitalPlot(int n, int l, int m)
	{
		// define your data
		double[] xArray = new double[numberOfDataPoints]; 
		double[] probDensityArray = new double[numberOfDataPoints]; 
		double[] radialProbArray = new double[numberOfDataPoints];
		
		for(int k = 0; k < numberOfDataPoints; k++)
		{
			double x = (double)k/(double)3;
			xArray[k] = x;
			probDensityArray[k] = pow(QuantumMechanics.calculateHydrogenWavefunction(n, l, m, x, 0, 0),2);
			radialProbArray[k] = 4*PI*pow(x,2)*pow(QuantumMechanics.calculateHydrogenWavefunction(n, l, m, x, 0, 0),2);
		}
		
		
		// define the legend position
		electronDensityPlot.addLegend("SOUTH");
		radialProbDistPlot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		electronDensityPlot.addLinePlot("Probability Density", xArray, probDensityArray);
		radialProbDistPlot.addLinePlot("Radial Probability Distribution", xArray, radialProbArray);
		
		electronDensityPlot.repaint();
		radialProbDistPlot.repaint();
		this.repaint();
	}
	
	public void setStartAndEnd(int panelNum, double start, double end)
	{
		switch(panelNum)
		{
			case 1:
				wavefunctionPlot.removeAllPlots();
				double[][] wavefunctionArray = reflection(getWavefunctionPlotData(psi, start, end, wavefunctionPlot.getWidth()/2));
				wavefunctionPlot.addLinePlot("Wavefunction", wavefunctionArray[0], wavefunctionArray[1]);
				wavefunctionPlot.setAutoBounds();
				wavefunctionPlot.repaint();
				break;
			case 2:
				electronDensityPlot.removeAllPlots();
				double[][] wavefunctionSquaredArray = reflection(getElectronDensityPlotData(psi, start, end, electronDensityPlot.getWidth()/2));
				electronDensityPlot.addLinePlot("Probability Density", wavefunctionSquaredArray[0], wavefunctionSquaredArray[1]);
				electronDensityPlot.setAutoBounds();
				electronDensityPlot.repaint();
				break;
			case 3:
				radialProbDistPlot.removeAllPlots();
				double[][] probDistArray = reflection(getRadialProbabilityDistributionPlotData(psi, start, end, radialProbDistPlot.getWidth()/2));
				radialProbDistPlot.addLinePlot("Radial Probability", probDistArray[0], probDistArray[1]);
				radialProbDistPlot.setAutoBounds();
				radialProbDistPlot.repaint();
				break;
			case 4:
				orbitalCrossSectionPanel.setData(getOrbitalPlotData(psi,end));
				orbitalCrossSectionPanel.repaint();
				orbitalCrossSectionPanelAlone.setData(getOrbitalPlotData(psi,end));
				orbitalCrossSectionPanelAlone.repaint();
				break;
		}
		this.repaint();
	}
	
	public void updateLimit(int panelNum)
	{
		switch(panelNum)
		{
			case 1:
				wavefunctionPlot.removeAllPlots();
				double[][] wavefunctionArray = reflection(getWavefunctionPlotData(psi, 0, limits[0], wavefunctionPlot.getWidth()/2));
				wavefunctionPlot.addLinePlot("Wavefunction", wavefunctionArray[0], wavefunctionArray[1]);
				wavefunctionPlot.setAutoBounds();
				wavefunctionPlot.repaint();
				break;
			case 2:
				electronDensityPlot.removeAllPlots();
				double[][] wavefunctionSquaredArray = reflection(getElectronDensityPlotData(psi, 0, limits[1], electronDensityPlot.getWidth()/2));
				electronDensityPlot.addLinePlot("Probability Density", wavefunctionSquaredArray[0], wavefunctionSquaredArray[1]);
				electronDensityPlot.setAutoBounds();
				electronDensityPlot.repaint();
				break;
			case 3:
				radialProbDistPlot.removeAllPlots();
				double[][] probDistArray = reflection(getRadialProbabilityDistributionPlotData(psi, 0, limits[2], radialProbDistPlot.getWidth()/2));
				radialProbDistPlot.addLinePlot("Radial Probability", probDistArray[0], probDistArray[1]);
				radialProbDistPlot.setAutoBounds();
				radialProbDistPlot.repaint();
				break;
			case 4:
				orbitalCrossSectionPanel.setData(getOrbitalPlotData(psi,limits[3]));
				orbitalCrossSectionPanel.repaint();
				orbitalCrossSectionPanelAlone.setData(getOrbitalPlotData(psi,limits[3]));
				orbitalCrossSectionPanelAlone.repaint();
				break;
		}
		this.repaint();
	}
	
	class OrbitalSettingsPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5865466894858767649L;
		JTextField nChooser;
		JTextField lChooser;
		JTextField mChooser;
		JTextField thetaChooser;
		JTextField phiChooser;
		
		public OrbitalSettingsPanel()
		{
			//this.setLayout(new GridLayout(1,10));
			Font font = this.getFont();
			font = font.deriveFont((float)20);
			
			JLabel nLabel = new JLabel("n =");
			nLabel.setFont(font);
			nChooser = new JTextField("1", 2);
			
			JLabel lLabel = new JLabel("  l =");
			lLabel.setFont(font);
			lChooser = new JTextField("0", 2);
			
			JLabel mLabel = new JLabel("  m =");
			mLabel.setFont(font);
			mChooser = new JTextField("0", 2);
			
			JLabel thetaLabel = new JLabel("  \u03B8 =");
			thetaLabel.setFont(font);
			thetaChooser = new JTextField("0", 2);
			
			JLabel phiLabel = new JLabel("  \u03C6 =");
			phiLabel.setFont(font);
			phiChooser = new JTextField("0", 2);
			
			JButton enterButton = new JButton("Plot Orbital");
			enterButton.addActionListener(new OrbitalSettingsListener());
			
			this.add(nLabel);
			this.add(nChooser);
			this.add(lLabel);
			this.add(lChooser);
			this.add(mLabel);
			this.add(mChooser);
			this.add(thetaLabel);
			this.add(thetaChooser);
			this.add(phiLabel);
			this.add(phiChooser);
			this.add(new JLabel("   "));
			this.add(enterButton);
		}
		
		class OrbitalSettingsListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				int n = Integer.parseInt(nChooser.getText());
				int l = Integer.parseInt(lChooser.getText());
				int m = Integer.parseInt(mChooser.getText());
				double theta = Double.parseDouble(thetaChooser.getText());
				double phi = Double.parseDouble(phiChooser.getText());
				graphOrbital(n,l,m,theta,phi);
			}
		}
	}
	class PlotSettingsPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4059095280530983591L;
		JTextField endChooser;
		
		public PlotSettingsPanel(int panelNum)
		{
			//this.setLayout(new GridLayout(1,10));
			Font font = this.getFont();
			font = font.deriveFont((float)20);
			
			JLabel endLabel = new JLabel("  Bound =");
			endLabel.setFont(font);
			endChooser = new JTextField(""+limits[panelNum-1], 2);
			
			JButton enterButton = new JButton("Set Bound");
			enterButton.addActionListener(new PlotSettingsListener(panelNum));
			
			this.add(endLabel);
			this.add(endChooser);
			this.add(new JLabel("   "));
			this.add(enterButton);
		}
		class PlotSettingsListener implements ActionListener
		{
			int panelNum;
			
			public PlotSettingsListener(int panelNum)
			{
				this.panelNum = panelNum;
			}
			
			public void actionPerformed(ActionEvent e)
			{
				double end = 0;
				try{
					end = Double.parseDouble(endChooser.getText());
				}catch(NumberFormatException nfe)
				{
					new SChemPException("Invalid Input!\nPlease enter a number for the bound.");
					return;
				}
				limits[panelNum-1] = end;
				updateLimit(panelNum);
				//setStartAndEnd(panelNum, 0, end);
			}
		}
	}
}

class Point3D
{
	public double x;
	public double y;
	public double z;
	
	public Point3D()
	{
		
	}
	
	public Point3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}

class Triplet
{
	public double[] data;
	
	public Triplet()
	{
		data = new double[3];
	}
	
	public Triplet(double x, double y, double z)
	{
		data = new double[3];
		data[0] = x;
		data[1] = y;
		data[2] = z;
	}
}

class OrbitalCrossSectionPanel extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4137509034362230326L;
	private int xBorder;
	private int yBorder;
	private double xScale;
	private double yScale;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	//private double xRes; //in pixels
	//private double yRes; //not used
	private boolean showAxes;
	private double[][] data;
	private double maxData;
	
	public OrbitalCrossSectionPanel()
	{
		xBorder = 10;
		yBorder = 10;
		xScale = 1;
		yScale = 1;
		xMin = -1.1;//0;
		xMax = 1.1;//15;
		yMin = -1.1;//0;
		yMax = 1.1;//0.1;
		//xRes = 1;
		//yRes = 2;
		showAxes = false;
	}
	
	public OrbitalCrossSectionPanel(double[][] data)
	{
		this.data = data;
		maxData = findMaxData(data);
		xBorder = 0;
		yBorder = 0;
		xMin = -findXMax(data);//0;
		xMax = -xMin;//15;
		yMin = xMin;//0;
		yMax = -xMin;//0.1;
		xScale = (int)pow(10,xMax/getGraphWidth()+1);
		//System.out.println(xScale);
		yScale = xScale;
		//xRes = 1;
		//yRes = 2;
		showAxes = false;
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.WHITE);
		g2.fillRect(xBorder,yBorder,getGraphWidth(), getGraphHeight());
		
		g2.setColor(Color.BLACK);
		g2.drawRect(xBorder-1,yBorder-1,getGraphWidth()+2,getGraphHeight()+2);
		
		if(xMin <= 0 && xMax >= 0 && showAxes)
			drawXAxis(g2);
		if(yMin <= 0 && yMax >= 0 && showAxes)
			drawYAxis(g2);
		
		graph(g2);
	}
	
	public void setData(double[][] data)
	{
		this.data = data;
		maxData = findMaxData(data);
		xMin = -findXMax(data);//0;
		xMax = -xMin;//15;
		yMin = xMin;//0;
		yMax = -xMin;//0.1;
		xScale = (int)pow(10,xMax/getGraphWidth()+1);
		yScale = xScale;
	}
	
	private double findMaxData(double[][] data)
	{
		double max = data[0][2];
		
		for(int k = 1; k < data.length; k++)
		{
			if(data[k][2] > max)
			{
				max = data[k][2];
			}
		}
		
		return max;
	}
	
	private double findXMax(double[][] data)
	{
		//double max = data[0][0];
		
		/*for(int k = 1; k < data.length; k++)
		{
			if(data[k][0] > max)
			{
				max = data[k][0];
			}
		}*/
		double max = data[data.length-1][0];
		
		return max;
	}
	
	private void graph(Graphics2D g2)
	{
		for(int k = 0; k < data.length; k++)
		{
			Point2D.Double point = cartesianToAWT(new Point2D.Double(data[k][0],data[k][1]));
			//System.out.println(data[k][2] + " " + data[k][1] + " " + point.x + " " + point.y);
			g2.setColor(new Color(1,0,0,(float)(data[k][2]/maxData)));
			int size = (int)((double)getGraphWidth()/(double)100);
			g2.fillOval((int)point.x-size/2, (int)point.y-size/2, size, size);
		}
	}
	
	/*
	private boolean onScreen(Point2D.Double point)
	{
		Point2D temp = awtToCartesian(point);
		//System.out.println(temp);
		boolean truth = true;
		
		if(temp.getX() < xMin || temp.getX() > xMax
				|| temp.getY() < yMin || temp.getY() > yMax)
		{
			truth = false;
		}
		
		System.out.println(xMin + " " + temp + " " + truth);
		return truth;
	}
	*/
	
	private void drawXAxis(Graphics g)
	{
		int x = yBorder+(int)(getYUnit()*Math.abs(xMax));
		g.drawLine(xBorder,x,getGraphWidth()+xBorder,x);
		
		for(int k = (int)Math.ceil(xMin); k <= Math.floor(xMax); k+= xScale)
		{
			double kPrime = (k+(-xMin));
			g.drawLine((int)(xBorder+kPrime*getXUnit()), (int)x-5,(int)(xBorder+kPrime*getXUnit()), (int)x+5);
		}
	}
	
	private void drawYAxis(Graphics g)
	{
		int y = xBorder+(int)(getXUnit()*Math.abs(xMin));
		g.drawLine(y,yBorder,y,getGraphHeight()+yBorder);
		
		for(int k = (int)Math.ceil(yMin); k <= Math.floor(yMax); k+= yScale)
		{
			double kPrime = (k+(-yMin));
			g.drawLine((int)y-5,(int)(xBorder+kPrime*getYUnit()), (int)y+5, (int)(xBorder+kPrime*getYUnit()));
		}
	}
	
	private double getYUnit()
	{
		return getGraphHeight()/(yMax-yMin);
	}
	
	private double getXUnit()
	{
		return getGraphWidth()/(xMax-xMin);
	}
	
	public int getGraphWidth()
	{
		return this.getWidth()-xBorder*2;
	}
	
	public int getGraphHeight()
	{
		return this.getHeight()-yBorder*2;
	}
	
	public Point2D.Double cartesianToAWT(Point2D.Double cartPoint)
	{
		double x,y;
		
		x = (xBorder + getXUnit()*(-(xMin)+cartPoint.getX()));
		y = (yBorder + getYUnit()*((yMax)-cartPoint.getY()));
		
		Point2D.Double awtPoint = new Point2D.Double(x,y);
		return awtPoint;
	}
	
	public Point2D.Double awtToCartesian(Point2D.Double awtPoint)
	{
		double x,y;
		
		x = ((awtPoint.getX()-xBorder)/getXUnit()+xMin);
		y = -((awtPoint.getY()-yBorder)/getYUnit()-yMax);
		
		Point2D.Double cartPoint = new Point2D.Double(x,y);
		return cartPoint;
	}
}
