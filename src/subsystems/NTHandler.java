package subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NTHandler
{
	private NetworkTable table;
	private int zone;
	
	public void init()
	{
		table = NetworkTable.getTable("GRIP/contoursReport");
	}
	
	public void update()
	{
		double[] ys = table.getNumberArray("centerY", new double[]{0});
		double[] xs = table.getNumberArray("centerX", new double[]{0});
		double[] areas = table.getNumberArray("area", new double[]{0});
		
		ArrayList<Double> validXs = new ArrayList<Double>();
		
		for(int i = 0; i < ys.length; i++)
		{
			if(ys[i] > 180 && ys[i] < 300)
				validXs.add(xs[i]);
		}
		
		if(validXs.size() == 2)
		{
			for(int i = 0; i < ys.length; i++)
			{
				double y = ys[i];
				double mdpt = (validXs.get(0) + validXs.get(1)) / 2;
					
				System.out.print("y: " + y + ", ");
				
				if(mdpt < 220)
					zone = 1;
				else if(mdpt > 420)
					zone = 3;
				else
					zone = 2;
			}
		}
		else
			System.out.println("Carry on.");
		
		System.out.println();
	}
	
	public int getZone()
	{
		return zone;
	}
}
