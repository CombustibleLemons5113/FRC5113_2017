package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;
import subsystems.NTHandler2;

public class Forward extends GearFrame
{
	private int caseSelector = 1;
	private double time;
	private double mag = Math.sqrt(Math.pow(0, 2) + Math.pow(0.75, 2));
	private double angle = Math.atan2(0.75, 0);
	private double rotation = 0;
	
	public void update(DriveTrain dt, NTHandler2 nettab)
	{
		switch(caseSelector)
		{
		case 1:
			drive(mag, angle, rotation);
			time = System.currentTimeMillis();
			caseSelector = 2;
			
			break;
			
		case 2:
			if(System.currentTimeMillis() - time > 4000)
				caseSelector = 3;
			
			break;
		
		case 3:
			drive(0, 0, 0);
			
			break;
		}
		dt.mecanumDrive(m, a, r);
	}
}
