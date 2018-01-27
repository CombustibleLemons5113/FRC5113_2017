package subsystems;

import com.kauailabs.navx.frc.AHRS;

import auton.Forward;
import auton.LeftGear;
import auton.LeftGearVision;
import auton.MiddleGear;
import auton.MiddleGearVision;
import auton.RightGear;
import auton.RightGearVision;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonManager
{
	private int caseSelector = 1;
	private double mag, angle, rotation;
	private String autoName;
	
	LeftGear leftGear;
	MiddleGear middleGear;
	RightGear rightGear;
	LeftGearVision leftGearVision;
	MiddleGearVision middleGearVision;
	RightGearVision rightGearVision;
	Forward forward;
	
	public void init()
	{
		leftGear = new LeftGear();
		middleGear = new MiddleGear();
		rightGear = new RightGear();
		leftGearVision = new LeftGearVision();
		middleGearVision = new MiddleGearVision();
		rightGearVision = new RightGearVision();
		rightGearVision.init();
		forward = new Forward();
	}
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		if(caseSelector == 1)
			leftGear.update(dt, nettab);
		else if(caseSelector == 2)
			middleGear.update(dt, nettab);
		else if(caseSelector == 3)
			rightGear.update(dt, nettab);
		else if(caseSelector == 4)
			leftGearVision.update(dt, nettab);
		else if(caseSelector == 5)
			middleGearVision.update(dt, nettab);
		else if(caseSelector == 6)
			rightGearVision.update(dt, nettab);
		else if(caseSelector == 7)
			forward.update(dt, nettab);
	}
	
	public void changeMode(boolean switchMode)
	{
		if(switchMode)
		{
			if(caseSelector == 7)
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
		else if(caseSelector == 4)
			autoName = "Left Gear Vision";
		else if(caseSelector == 5)
			autoName = "Middle Gear Vision";
		else if(caseSelector == 6)
			autoName = "Right Gear Vision";
		else if(caseSelector == 7)
			autoName = "Forward";
		
		SmartDashboard.putString("Auto Mode", autoName + " (" + caseSelector + ")");
	}
}