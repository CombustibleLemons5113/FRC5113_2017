package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;

public class MiddleGear extends GearFrame
{
	private int caseSelector = 1;
	private double time;
	private double mag = Math.sqrt(Math.pow(-0.5, 2) + Math.pow(-0.05, 2));
	private double angle = Math.atan2(-0.05, -0.5);
	private double rotation = .04;
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		switch(caseSelector)
		{
		case 1:
			drive(mag, angle, rotation);
			time = System.currentTimeMillis();
			caseSelector = 2;
			
			break;
			
		case 2:
			if(System.currentTimeMillis() - time > 6000)
				caseSelector = 3;
			
			break;
		
		case 3:
			drive(0, 0, 0);
			
			break;
		}
		dt.mecanumDrive(m, a, r);
	}
}
