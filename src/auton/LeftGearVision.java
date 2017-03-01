package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;
import subsystems.NTHandler2;

public class LeftGearVision extends GearFrame
{
	private int caseSelector = 1;
	
	public void update(DriveTrain dt, NTHandler2 nettab)
	{
		int distance = nettab.getDistance();
		int mode = nettab.getMode();
		
		switch(caseSelector)
		{
		case 1:
			dt.mecanumDrive(0.5, 90 * Math.PI / 180, 0);
			
			if(nettab.getZone() == 3)
				caseSelector++;
			break;
		case 2:
			if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 100 * Math.PI / 180, .2);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive(0.3, 90 * Math.PI / 180, 0);
			else if(nettab.getZone() == 3)
				dt.mecanumDrive(0.3, 80 * Math.PI / 180, -.2);
			
			if(mode == 2)
				caseSelector++;
			
			break;
		case 3:
			if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 90, 0);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive(0.3, 0, 0);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 270, 0);
			
			if(distance < 16)
				caseSelector++;
			
			break;
		case 4:
			dt.mecanumDrive(0, 0, 0);
			System.out.println("Done!");
			break;
		}
	}
}
