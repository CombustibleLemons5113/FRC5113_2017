package subsystems;

import auton.LeftGear;
import auton.MiddleGear;
import auton.RightGear;

public class AutonManager
{
	private int caseSelector;
	private double mag, angle, rotation;
	
	DriveTrain dt;
	
	LeftGear leftGear;
	MiddleGear middleGear;
	RightGear rightGear;
	
	public void init()
	{
		leftGear = new LeftGear();
		middleGear = new MiddleGear();
		rightGear = new RightGear();
	}
	
	public void update(DriveTrain dt)
	{
		if(caseSelector == 1)
			middleGear.update(dt);
	}
	
	public void driveRight()
	{
		mag = Math.sqrt(Math.pow(0.5, 2) + Math.pow(0, 2));
		angle = Math.atan2(0, 0.5);
		rotation = 0;
		
		dt.mecanumDrive(mag, angle, rotation);
	}
	
	public void stop()
	{
		mag = Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2));
		angle = Math.atan2(0, 0);
		rotation = 0;
		
		dt.mecanumDrive(mag, angle, rotation);
	}
}
