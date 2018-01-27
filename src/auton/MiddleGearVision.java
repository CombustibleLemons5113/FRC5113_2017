package auton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import subsystems.DriveTrain;
import subsystems.NTHandler;

public class MiddleGearVision extends GearFrame
{
	private int caseSelector = 1;
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		int distance = nettab.getDistance();
		int mode = nettab.getMode();
		double angle = dt.navx.getAngle();
		
		nettab.print();
		
		switch(caseSelector)
		{
		case 1:
			System.out.println("Driving forward - middle");
			SmartDashboard.putString("Auton Distance", "Driving forward - middle");
			dt.mecanumDrive2(0.3, 0, 0, angle); //set back to 0.5
			
			if(nettab.getZone() == 1 || nettab.getZone() == 2 || nettab.getZone() == 3)
				caseSelector++;
			break;
		case 2:
			System.out.println("Driving to peg - coarse");
			SmartDashboard.putString("Auton Distance", "Driving to peg - coarse");
			if(nettab.getZone() == 3)
				dt.mecanumDrive2(0.15, 40, 0, angle);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive2(0.15, 0, 0, angle);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.15, 320, 0, angle);
			
			if(mode == 2)
				caseSelector++;
			
			break;
		case 3:
			System.out.println("Driving to peg - fine");
			SmartDashboard.putString("Auton Distance", "Driving to peg - fine");
			if(nettab.getZone() == 3)
				dt.mecanumDrive2(0.1, 90, 0, angle);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive2(0.1, 0, 0, angle);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.1, 270, 0, angle);
			
			if(distance < 16)
				caseSelector++;
			
			break;
		case 4:
			dt.mecanumDrive2(0, 0, 0, angle);
			System.out.println("Done!");
			break;
		}
	}
}
