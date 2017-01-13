package subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain {
	
	private PWM fl;
	private PWM fr;
	private PWM bl;
	private PWM br;
	
	public void init() {
		//Initialize and set to CAN IDs.
		//We can remap the IDs from within the web browser at roboRIO-5113.local

		fl = new PWM(0);
		fr = new PWM(1);
		bl = new PWM(2);
		br = new PWM(3);

	}

	//Controls the drive train
	public void mecanumDrive(double magnitude, double angle, double rotation)
	{			
		//Makes sure that magnitude fits into the range [0, 0.99] as expected. Hardware errors can otherwise cause small movement changes.
		magnitude = Math.min(Math.abs(magnitude), 0.99);

		//As the mecanum drive is X-Shaped, we must adjust to be at 45* angles.
		double newDirection = (double) (angle + 45);
		newDirection = (double) (newDirection * Math.PI) / 180f;
		double cosine, sine;
		cosine = (double) Math.cos(newDirection);
		sine = (double) Math.sin(newDirection);
		
		double frontLeftPower = (double) -(sine * magnitude + rotation);//+
		double frontRightPower = (double) (cosine * magnitude - rotation);//-
		double backLeftPower = (double) -(cosine * magnitude + rotation);//+
		double backRightPower = (double) (sine * magnitude - rotation);//-

		bl.setSpeed(backLeftPower);
		br.setSpeed(backRightPower);
		fl.setSpeed(frontLeftPower);
		fr.setSpeed(frontRightPower);
	}
}
