package auton;

import subsystems.DriveTrain;

public class MiddleGear extends GearFrame
{
	private int caseSelector = 1;
	private double time;
	private double mag = Math.sqrt(Math.pow(0.5, 2) + Math.pow(0, 2));
	private double angle = Math.atan2(0, 0.5);
	private double rotation = 0;
	
	public void update(DriveTrain dt)
	{
		switch(caseSelector)
		{
		case 1:
			drive(mag, angle, rotation);
			time = System.currentTimeMillis();
			caseSelector = 2;
			
			break;
			
		case 2:
			if(System.currentTimeMillis() - time > 5000)
				caseSelector = 3;
			
			break;
		
		case 3:
			drive(0, 0, 0);
			System.out.println("Done!");
			
			break;
		}
		dt.mecanumDrive(m, a, r);
	}
}
