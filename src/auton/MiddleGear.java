package auton;

import subsystems.DriveTrain;
import subsystems.NTHandler;

public class MiddleGear extends GearFrame
{
	private int caseSelector;
	private double time;
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		caseSelector = 1;
		switch(caseSelector)
		{
		case 1:
			manager.driveRight(dt);
			time = System.currentTimeMillis();
			caseSelector = 2;
			
			break;
			
		case 2:
			if(System.currentTimeMillis() - time > 500)
				caseSelector = 3;
			
			break;
		
		case 3:
			manager.stop(dt);
			System.out.println("Done!");
			
			break;
		}
	}
}
