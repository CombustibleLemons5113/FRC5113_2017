package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class JoystickManager
{
	Joystick joystick;
	
	private JoystickButton resetGyro;
	
	public void init()
	{
		joystick = new Joystick(0);
		resetGyro = new JoystickButton(joystick, 2);
	}
	
	private void handleJoystickDrive(DriveTrain dt)
	{
		double magX = joystick.getX();
		double magY = joystick.getZ();
		double rotation = joystick.getY();
		double gyroAngle = dt.getGyroAngle();
		System.out.println(gyroAngle);
		
		if (magX > 0.99)
			magX = 0.99;
		else if (magX < -0.99)
			magX = -0.99;
		
		if (magY > 0.99)
			magY = 0.99;
		else if (magY < -0.99)
			magY = -0.99;
		
		if (rotation > 0.99)
			rotation = 0.99;
		else if (rotation < -0.99)
			rotation = -0.99;
		
		dt.mecanumDrive3(magX, magY, rotation, gyroAngle);
	}
	
	public void update(DriveTrain driveTrain) {
		handleJoystickDrive(driveTrain);
	}
	
	public boolean getGyroReset() {
		return resetGyro.get();
	}
}