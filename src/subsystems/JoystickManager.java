package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class JoystickManager
{
	Joystick joystick;
	
	private double magX, magY, rotation, gyroAngle;
	
	private JoystickButton resetGyro;
	
	public void init()
	{
		joystick = new Joystick(0);
		resetGyro = new JoystickButton(joystick, 2);
	}
	
	private void handleJoystickDrive(DriveTrain dt)
	{
		magX = joystick.getX();
		magY = joystick.getZ();
		rotation = joystick.getY();
		gyroAngle = dt.getGyroAngle();
		//System.out.println(gyroAngle);
		
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
		
		dt.mecanumDrive3(magX / 2, magY / 4, rotation / 4, gyroAngle);
	}
	
	public void update(DriveTrain driveTrain) {
		handleJoystickDrive(driveTrain);
		
		System.out.println("FLE :" + driveTrain.checkFLE() + "\nFRE :" + driveTrain.checkFRE() + "\nBLE :" + driveTrain.checkBLE() + "\nBRE :" + driveTrain.checkBRE());
		
		if(getGyroReset())
			driveTrain.gyro.reset();
	}
	
	public boolean getGyroReset() {
		return resetGyro.get();
	}
}