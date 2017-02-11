package subsystems;

import auton.LeftGear;
import auton.MiddleGear;
import auton.RightGear;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonManager
{
	private int caseSelector = 1;
	private double mag, angle, rotation;
	private String autoName;
	
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
			leftGear.update(dt);
		else if(caseSelector == 2)
			middleGear.update(dt);
		else if(caseSelector == 3)
			rightGear.update(dt);
	}
	
	public void driveRight(DriveTrain dt)
	{
		mag = Math.sqrt(Math.pow(0.5, 2) + Math.pow(0, 2));
		angle = Math.atan2(0, 0.5);
		rotation = 0;
		
		dt.mecanumDrive(mag, angle, rotation);
	}
	
	public void rotateRight(DriveTrain dt)
	{
		mag = Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2));
		angle = Math.atan2(0, 0);
		rotation = 0.5;
		
		dt.mecanumDrive(mag, angle, rotation);
	}
	
	public void rotateLeft(DriveTrain dt)
	{
		mag = Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2));
		angle = Math.atan2(0, 0);
		rotation = -0.5;
		
		dt.mecanumDrive(mag, angle, rotation);
	}
	
	public void stop(DriveTrain dt)
	{
		mag = Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2));
		angle = Math.atan2(0, 0);
		rotation = 0;
		
		dt.mecanumDrive(mag, angle, rotation);
	}
	
	public void changeMode(boolean switchMode)
	{
		if(switchMode)
		{
			if(caseSelector == 3)
				caseSelector = 1;
			else
				caseSelector++;
		}
		
		if(caseSelector == 1)
			autoName = "Left Gear";
		else if(caseSelector == 2)
			autoName = "Middle Gear";
		else if(caseSelector == 3)
			autoName = "Right Gear";
		
		SmartDashboard.putString("Auto Mode", autoName);
	}
}