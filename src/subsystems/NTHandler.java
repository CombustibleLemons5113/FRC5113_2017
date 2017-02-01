package subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class NTHandler
{
	NetworkTable table;
	
	public void init()
	{
		table = NetworkTable.getTable("vision");
	}
	
	public void update()
	{
		boolean test = table.getBoolean("test", false);
		System.out.println(test);
	}
}
