package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;

public class Forward extends GearFrame
{
	private int caseSelector = 1;
	private double time;
	private double mag = Math.sqrt(Math.pow(0, 2) + Math.pow(0.75, 2));
	private double rotation = 0;
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		//double angle = dt.navx.getAngle();
		switch(caseSelector)
		{
		case 1:
			dt.mecanumDrive2(0.75, 270, 0, 0);
			time = System.currentTimeMillis();
			caseSelector = 2;
			
			break;
			
		case 2:
			if(System.currentTimeMillis() - time > 3000)
				caseSelector = 3;
			
			break;
		
		case 3:
			dt.mecanumDrive2(0, 0, 0, 0);
			
			break;
		}
	}
}