package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;

public class LeftGear extends GearFrame
{
	private int caseSelector = 1;
	private double time;
	private double mag = Math.sqrt(Math.pow(0.5, 2) + Math.pow(0, 2));
	private double angle = Math.atan2(0, 0.5);
	private double rotation = 0;
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		switch(caseSelector)
		{
		case 1:
			dt.mecanumDrive(0.5, 90 * Math.PI / 180, 0);
			
			if(nettab.getZone() == 2)
				caseSelector++;
			break;
		case 2:
			if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 100 * Math.PI / 180, 0.2);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive(0.3, 90 * Math.PI / 180, 0);
			else if(nettab.getZone() == 3)
				dt.mecanumDrive(0.3, 80 * Math.PI / 180, -0.2);
			
			if(nettab.getArea() > 100)
				caseSelector++;
			
			break;
			
		case 3:
			dt.mecanumDrive(0, 0, 0);
			System.out.println("Done!");
			break;
		}
		dt.mecanumDrive(m, a, r);
	}
}
