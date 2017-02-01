package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class JoystickManager
{
	Joystick joystick;
	
	private double mag, angle, rotation, gyroAngle;
	
	private JoystickButton resetGyro;
	
	public void init()
	{
		joystick = new Joystick(0);
		resetGyro = new JoystickButton(joystick, 2);
	}
	
	private void handleJoystickDrive(DriveTrain dt)
	{
		double x = getXAxis();
		double y = getYAxis();
		double z = getZAxis();
		
		if (x > 0.99)
			x = 0.99;
		else if (x < -0.99)
			x = -0.99;
		
		if (Math.abs(x) < 0.05)
			x = 0;
		
		if (y > 0.99)
			y = 0.99;
		else if (y < -0.99)
			y = -0.99;
		
		if (Math.abs(y) < 0.05)
			y = 0;
		
		if (z > 0.99)
			z = 0.99;
		else if (z < -0.99)
			z = -0.99;
		
		if (Math.abs(z) < 0.4)
			z = 0;
		
		mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		angle = Math.atan2(y, x);
		rotation = z;
		//gyroAngle = dt.getGyroAngle();
		//System.out.println(gyroAngle);
		//System.out.println((angle / Math.PI) * 180);
		//System.out.println("Mag: " + mag + "\nAngle: " + angle + "\nRotation: " + rotation);
		
		
		
		dt.mecanumDrive(mag, angle, rotation / 2);
	}
	
	public void update(DriveTrain driveTrain) {
		handleJoystickDrive(driveTrain);
		
		//System.out.println("FLE :" + driveTrain.checkFLE() / 360 + "\nFRE :" + driveTrain.checkFRE() / 360 + "\nBLE :" + driveTrain.checkBLE() / 360 + "\nBRE :" + driveTrain.checkBRE() / 360);
		
		if(getGyroReset())
			driveTrain.gyro.reset();
	}
	
	public double getXAxis() {
		return -joystick.getX();
	}
	
	public double getYAxis() {
		return -joystick.getY();
	}
	
	public double getZAxis() {
		return joystick.getZ();
	}
	
	public boolean getGyroReset() {
		return resetGyro.get();
	}
}