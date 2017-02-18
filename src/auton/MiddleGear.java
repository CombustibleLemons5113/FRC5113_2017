package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;

public class MiddleGear extends GearFrame
{
	private int caseSelector = 1;
	private long time;
	private double desiredDistance = 1;
	private double mag = Math.sqrt(Math.pow(0.5, 2) + Math.pow(0, 2));
	private double angle = Math.atan2(0, 0.5);
	private double rotation = 0;
	
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
			System.out.println("Time :" + (time / 1000) + "\nVelocity :" + manager.navx.getVelocityX() + "\nDistance :" + distance(time));
			if((distance(time) >= desiredDistance * 0.99) && (distance(time) <= desiredDistance * 1.01))
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
