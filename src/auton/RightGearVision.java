package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;
import subsystems.NTHandler2;

public class RightGearVision extends GearFrame
{
	private int caseSelector = 1;
	
	public void update(DriveTrain dt, NTHandler2 nettab)
	{
		int distance = nettab.getDistance();
		int mode = nettab.getMode();
		
		switch(caseSelector)
		{
		case 1:
			System.out.println("Driving forward");
			dt.mecanumDrive2(0.5, 270 * Math.PI / 180, 0);
			
			if(nettab.getZone() == 1 || nettab.getZone() == 2 || nettab.getZone() == 3)
				caseSelector++;
			break;
		case 2:
			System.out.println("Driving to peg - coarse");
			if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.3, 20 * Math.PI / 180, .2);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive2(0.3, 0 * Math.PI / 180, 0);
			else if(nettab.getZone() == 3)
				dt.mecanumDrive2(0.3, 340 * Math.PI / 180, -.2);
			
			if(mode == 2)
				caseSelector++;
			
			break;
		case 3:
			System.out.println("Driving to peg - fine");
			if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.3, 20, 0);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive2(0.3, 0, 0);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.3, 340, 0);
			
			if(distance < 16)
				caseSelector++;
			
			break;
		case 4:
			dt.mecanumDrive2(0, 0, 0);
			System.out.println("Done!");
			break;
		}
	}
}
