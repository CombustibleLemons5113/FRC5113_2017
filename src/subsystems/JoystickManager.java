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
		double x = joystick.getX();
		double y = joystick.getY();
		double z = joystick.getZ();
		
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
		
		if (Math.abs(z) < 0.05)
			z = 0;
		
		mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		angle = Math.atan2(y, x);
		rotation = z * 180;
		//gyroAngle = dt.getGyroAngle();
		//System.out.println(gyroAngle);
		
		System.out.println("Mag: " + mag + "\nAngle: " + angle + "\nRotation: " + rotation);
		
		dt.mecanumDrive(mag, angle, rotation);
	}
	
	public void update(DriveTrain driveTrain) {
		handleJoystickDrive(driveTrain);
		
		//System.out.println("FLE :" + driveTrain.checkFLE() + "\nFRE :" + driveTrain.checkFRE() + "\nBLE :" + driveTrain.checkBLE() + "\nBRE :" + driveTrain.checkBRE());
		
		/*if((driveTrain.checkFLE() > 0) && (driveTrain.checkFRE() > 0) && (driveTrain.checkBLE() > 0) && (driveTrain.checkBRE() > 0)) {
			if(driveTrain.checkFLE() != driveTrain.checkFRE())
				driveTrain.fr.set(driveTrain.fl.getSpeed());
			if(driveTrain.checkBLE() != driveTrain.checkBRE())
				driveTrain.br.set(driveTrain.bl.getSpeed());
		}
		
		if((driveTrain.checkFLE() < 0) && (driveTrain.checkFRE() < 0) && (driveTrain.checkBLE() < 0) && (driveTrain.checkBRE() < 0)) {
			if(driveTrain.checkFLE() != driveTrain.checkFRE())
				driveTrain.fr.set(driveTrain.fl.getSpeed());
			if(driveTrain.checkBLE() != driveTrain.checkBRE())
				driveTrain.br.set(driveTrain.bl.getSpeed());
		}*/
		
		if(getGyroReset())
			driveTrain.gyro.reset();
	}
	
	public boolean getGyroReset() {
		return resetGyro.get();
	}
}