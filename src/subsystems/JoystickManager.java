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
		
		if (Math.abs(magX) < 0.05)
			magX = 0;
		
		if (magY > 0.99)
			magY = 0.99;
		else if (magY < -0.99)
			magY = -0.99;
		
		if (Math.abs(magY) < 0.1)
			magY = 0;
		
		if (rotation > 0.99)
			rotation = 0.99;
		else if (rotation < -0.99)
			rotation = -0.99;
		
		if (Math.abs(rotation) < 0.05)
			rotation = 0;
		
		dt.mecanumDrive3(magX, magY, rotation, gyroAngle);
	}
	
	public void update(DriveTrain driveTrain) {
		handleJoystickDrive(driveTrain);
		
		System.out.println("FLE :" + driveTrain.checkFLE() + "\nFRE :" + driveTrain.checkFRE() + "\nBLE :" + driveTrain.checkBLE() + "\nBRE :" + driveTrain.checkBRE());
		
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