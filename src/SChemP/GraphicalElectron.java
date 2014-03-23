package SChemP;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class GraphicalElectron extends Electron
	{
		static final float DegToRad = (float)Math.PI/180;
		public Point2D.Float point;
		int level;
		int xCenter;
		int yCenter;
		int numInOrb;
		float angularMultiplier;
		float orbitMultiplier;
		static HashMap<String, Integer> orbLevels;
		static int[] levelCount;
		
		static
		{
			orbLevels = new HashMap<String, Integer>();
			orbLevels.put("1s",new Integer(0));
			orbLevels.put("2s",new Integer(1));
			orbLevels.put("2p",new Integer(2));
			orbLevels.put("3s",new Integer(3));
			orbLevels.put("3p",new Integer(4));
			orbLevels.put("3d",new Integer(6));
			orbLevels.put("4s",new Integer(5));
			orbLevels.put("4p",new Integer(7));
			orbLevels.put("4d",new Integer(9));
			orbLevels.put("4f",new Integer(12));
			orbLevels.put("5s",new Integer(8));
			orbLevels.put("5p",new Integer(10));
			orbLevels.put("5d",new Integer(13));
			orbLevels.put("5f",new Integer(16));
			orbLevels.put("6s",new Integer(11));
			orbLevels.put("6p",new Integer(14));
			orbLevels.put("6d",new Integer(17));
			orbLevels.put("7s",new Integer(15));
			orbLevels.put("7p",new Integer(18));
			levelCount = new int[7];
			for(int z = 0; z < 7; z++)
				levelCount[z] = 0;
		}
		
		public GraphicalElectron()
		{
			super();
		}
		
		public GraphicalElectron(float x, float y)
		{
			super();
			point.setLocation(x,y);
			this.level = 0;
		}
		
		public GraphicalElectron(int xCenter, int yCenter, int level, String orb, int e, int totalInLevel)
		{
			super(level, lValue(orb), (e%2 == 0)? (int)Math.ceil(e/2): (int)Math.floor(-e/2), (e%2 == 0)? .5 : -.5);
			this.xCenter = xCenter;
			this.yCenter = yCenter;
			
			this.level = level;
			numInOrb = e;
			float levCount = getLevelCount(level);
			orbitMultiplier = 15f * level;
			angularMultiplier = DegToRad*(float)levCount*(float)360/(float)orbCapacity(orb);
			float y = (float)(yCenter + ((orbitMultiplier)*Math.sin(angularMultiplier)));
			float x = (float)(xCenter + ((orbitMultiplier)*Math.cos(angularMultiplier)));
			
			point = new Point2D.Float(x, y);
		}
		
		public GraphicalElectron(int xCenter, int yCenter, int level, int l, int e, int totalInLevel)
		{
			super(level, l, (e%2 == 0)? (int)Math.ceil(e/2): (int)Math.floor(-e/2), (e%2 == 0)? .5 : -.5);
			this.xCenter = xCenter;
			this.yCenter = yCenter;
			
			//System.out.println(level + " " + totalInLevel);
			this.level = level;
			numInOrb = e;
			float levCount = getLevelCount(level);
			orbitMultiplier = 15f * level;
			angularMultiplier = DegToRad*(float)levCount*(float)360/(float)totalInLevel;
			float y = (float)(yCenter + ((orbitMultiplier)*Math.sin(angularMultiplier)));
			float x = (float)(xCenter + ((orbitMultiplier)*Math.cos(angularMultiplier)));
			
			point = new Point2D.Float(x, y);
		}
		
		public GraphicalElectron(int xCenter, int yCenter, Electron electron, int e, int totalInOrb)
		{
			super(electron);
			this.xCenter = xCenter;
			this.yCenter = yCenter;
			
			numInOrb = e;
			float levCount = getLevelCount(level);
			orbitMultiplier = 15f * level;
			angularMultiplier = DegToRad*(float)levCount*(float)360/(float)totalInOrb;
			float y = (float)(yCenter + ((orbitMultiplier)*Math.sin(angularMultiplier)));
			float x = (float)(xCenter + ((orbitMultiplier)*Math.cos(angularMultiplier)));
			
			point = new Point2D.Float(x, y);
		}
		
		public static void reset()
		{
			for(int z = 0; z < 7; z++)
				levelCount[z] = 0;
		}
		
		public void step(float factor)
		{
			float x, y;
			if(numInOrb >= super.orbCapacity(getL())/2)
			{
				y = (float)(yCenter + ((orbitMultiplier)*Math.sin(factor/level+angularMultiplier)));
				x = (float)(xCenter + ((orbitMultiplier)*Math.cos(factor/level+angularMultiplier)));
			}
			else
			{
				y = (float)(yCenter + ((orbitMultiplier)*Math.sin(-factor/level+angularMultiplier)));
				x = (float)(xCenter + ((orbitMultiplier)*Math.cos(-factor/level+angularMultiplier)));
			}
			
			point.setLocation(x, y);
		}
		
		public void setCenter(int xCenter, int yCenter)
		{
			this.xCenter = xCenter;
			this.yCenter = yCenter;
		}
		
		public int getyCenter()
		{
			return yCenter;
		}
		
		private static int getLevelCount(int lev)
		{
			int count = 0;
			
			count = levelCount[lev-1];
			if(levelCount[lev-1] <= levelCapacity(lev))
				levelCount[lev-1]++;
			else
				levelCount[lev-1] = 0;
				
			return count;
		}
	}