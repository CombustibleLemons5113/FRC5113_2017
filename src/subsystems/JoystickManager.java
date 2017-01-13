package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class JoystickManager
{
	Joystick joystick;
	
	public void init()
	{
		joystick = new Joystick(0);
	}
	
	public void update(DriveTrain driveTrain)
	{
		double value = joystick.getAxis(AxisType.kY);
		
		driveTrain.testDrive(value);
	}
}
