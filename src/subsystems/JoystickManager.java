package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;

public class JoystickManager
{
	Joystick joystick;
	
	double rotationalSensitivity = 0.5f;
	
	public void init()
	{
		joystick = new Joystick(0);
	}
	
	private void handleXboxDrive(DriveTrain dt)
	{
		double angle = joystick.getZ();
		
		double axis = joystick.getY();
		// Joystick drive control
		//The magnitude uses sin so that it will drive at about 50% speed forward,
		//And 100% speed sideways. Because mecanum requires more power sideways.
		double mag = -joystick.getX();
		
		if (Math.abs(angle) < 0.4f)
		{
			angle = 0;
		}

		dt.mecanumDrive3(mag / 2f, angle / 4f, axis / 4f);
	}
	
	public void update(DriveTrain driveTrain) {
		handleXboxDrive(driveTrain);
	}
	
	public double getDriveForward() {
		return joystick.getY();
	}
}