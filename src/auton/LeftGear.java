package auton;

import subsystems.DriveTrain;

public class LeftGear extends GearFrame
{
	private int caseSelector;
	private double time;
	
	public void update(DriveTrain dt)
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
			manager.rotateRight(dt);
			time = System.currentTimeMillis();
			caseSelector = 4;
			
			break;
			
		case 4:
			if(System.currentTimeMillis() - time > 500)
			{
				manager.stop(dt);
				manager.driveRight(dt);
				time = System.currentTimeMillis();
				caseSelector = 5;
			}
			
			break;
			
		case 5:
			if(System.currentTimeMillis() - time > 500)
			{
				manager.stop(dt);
				System.out.println("Done!");
			}
			
			break;
		}
	}
}
