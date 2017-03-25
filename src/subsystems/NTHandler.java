package subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NTHandler
{
	private NetworkTable table;
	private int mode, distance, zone;
	private boolean connected = false;
	
	public void init()
	{
		table = NetworkTable.getTable("contoursReport");	zone = (int) table.getNumber("zone", -1);
		mode = (int) table.getNumber("mode", -1);
		distance = (int) table.getNumber("distance", -1);
		connected = table.getBoolean("connected", false);
	}
	
	public void print()
	{
		System.out.println("Zone: " + zone);
		System.out.println("Mode: " + mode);
		System.out.println("Distance: " + distance);
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
	
	public boolean getConnected()
	{
		return connected;
	}
}
