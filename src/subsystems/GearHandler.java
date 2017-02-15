package subsystems;

public class GearHandler
{
	private int zone;
	
	public void init()
	{
		zone = 2;
	}
	
	public void drive(DriveTrain dt, NTHandler nettab)
	{
		zone = nettab.getZone();
		System.out.println(zone + "\t" + nettab.getArea());
		
		if(nettab.getArea() < 20000)
		{
			if(zone == 1)
				dt.mecanumDrive(0.2, 170 * Math.PI / 180, -0.2);
			else if(zone == 2)
				dt.mecanumDrive(0.2, 180 * Math.PI / 180, 0);
			else if(zone == 3)
				dt.mecanumDrive(0.2, 190 * Math.PI / 180, 0.2);
		}
		else
			System.out.println("Done!");
	}
}
