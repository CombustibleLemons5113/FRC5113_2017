package subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NTHandler2
{
	private NetworkTable table;
	private int mode, distance, zone;
	private double avgArea;
	
	public void init()
	{
		table = NetworkTable.getTable("contoursReport");
		zone = 2;
		avgArea = 0;
		distance = -1;
	}
	
	public void update()
	{
		zone = (int) table.getNumber("zone", -1);
		mode = (int) table.getNumber("mode", -1);
		distance = (int) table.getNumber("distance", -1);
	}
	
	public int getZone()
	{
		return zone;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public int getDistance()
	{
		return distance;
	}
	
	public double getArea()
	{
		return avgArea;
	}
}
