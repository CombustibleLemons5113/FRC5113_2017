package subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain {
	
	//Not PWM WHYYYYYYYYYYYYYYYYYYYYYYYYY!!!!!!!!
	private Talon fl;
	private Talon fr;
	private Talon bl;
	private Talon br;
	
	private double df;
	
	RobotDrive roboDrive;
	
	AnalogGyro gyro;
	
	public void init() {
		//Initialize and set to CAN IDs.
		//We can remap the IDs from within the web browser at roboRIO-5113.local

		fl = new Talon(0);
		fr = new Talon(1);
		bl = new Talon(2);
		br = new Talon(3);
		
		roboDrive = new RobotDrive(bl, fl, br, fr);
		
		gyro = new AnalogGyro(0);
		gyro.initGyro();
		System.out.println("Gyro is now initiated\t" + gyro.getAngle());
	}
	
	public void update(JoystickManager jm) {
		if(jm.getGyroReset())
			gyro.reset();
	}
	
	public void mecanumDrive3(double x, double y, double rotation, double gyroAngle) {
		roboDrive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
	}
	
	public void mecanumDrive2(double magnitude, double angle, double rotation)
	{
		roboDrive.mecanumDrive_Polar(magnitude, angle, rotation);
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
		
		double frontLeftPower = (double) -(sine * magnitude - rotation);
		double frontRightPower = (double) -(cosine * magnitude - rotation);
		double backLeftPower = (double) (cosine * magnitude + rotation);
		double backRightPower = (double) (sine * magnitude + rotation);

		bl.set(backLeftPower);
		br.set(backRightPower);
		fl.set(frontLeftPower);
		fr.set(frontRightPower);
	}
	
	public double getGyroAngle() {
		return gyro.getAngle();
	}
}
