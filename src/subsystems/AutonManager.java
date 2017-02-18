package subsystems;

import com.kauailabs.navx.frc.AHRS;

import auton.LeftGear;
import auton.MiddleGear;
import auton.RightGear;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonManager
{
	private int caseSelector = 1;
	private double mag, angle, rotation;
	private String autoName;
	
	public AHRS navx;
	
	LeftGear leftGear;
	MiddleGear middleGear;
	RightGear rightGear;
	
	public void init()
	{
		navx = new AHRS(I2C.Port.kOnboard);
		leftGear = new LeftGear();
		middleGear = new MiddleGear();
		rightGear = new RightGear();
	}
	
	public void update(DriveTrain dt, NTHandler nettab)
	{
		if(caseSelector == 1)
			leftGear.update(dt, nettab);
		else if(caseSelector == 2)
			middleGear.update(dt, nettab);
		else if(caseSelector == 3)
			rightGear.update(dt, nettab);
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