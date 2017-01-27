package subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain {
	
	//Not PWM WHYYYYYYYYYYYYYYYYYYYYYYYYY!!!!!!!!
	Talon fl;
	Talon fr;
	Talon bl;
	Talon br;
	
	Encoder fle;
	Encoder fre;
	Encoder ble;
	Encoder bre;
	
	RobotDrive roboDrive;
	
	AnalogGyro gyro;
	
	public void init() {
		//Initialize and set to CAN IDs.
		//We can remap the IDs from within the web browser at roboRIO-5113.local

		fl = new Talon(0);
		fr = new Talon(1);
		bl = new Talon(2);
		br = new Talon(3);
		
		fle = new Encoder(0, 1);//What are these parameters???
		fre = new Encoder(2, 3);
		ble = new Encoder(4, 5);
		bre = new Encoder(6, 7);
		
		roboDrive = new RobotDrive(bl, fl, br, fr);
		
		gyro = new AnalogGyro(0);
		gyro.initGyro();
		System.out.println("Gyro is now initiated\t" + gyro.getAngle());
		
		gyro.calibrate();
	}
	
	public void update(JoystickManager jm) {
		//System.out.println("FL: " + -fl.getSpeed() + "\nFR: " + fr.getSpeed() + "\nBL: " + -bl.getSpeed() + "\nBR: " + br.getSpeed());
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
		newDirection = (double) (newDirection * Math.PI) / 180;
		double cosine, sine;
		cosine = (double) Math.cos(newDirection);
		sine = (double) Math.sin(newDirection);
		
		double frontLeftSpeed = -(sine * magnitude + rotation);//+
		double frontRightSpeed = (cosine * magnitude - rotation);//-
		double backLeftSpeed = -(cosine * magnitude + rotation);//+
		double backRightSpeed = (sine * magnitude - rotation);//-
		
		//System.out.println("FLS: " + frontLeftSpeed + "\nFRS: "+ frontRightSpeed + "\nBLS: " + backLeftSpeed + "\nBRS: " + backRightSpeed);

		if(frontLeftSpeed >= 1)
			frontLeftSpeed = 0.99;
		else if(frontLeftSpeed <= -1)
			frontLeftSpeed = -0.99;
		
		if(frontRightSpeed >= 1)
			frontRightSpeed = 0.99;
		else if(frontRightSpeed <= -1)
			frontRightSpeed = -0.99;
		
		if(backLeftSpeed >= 1)
			backLeftSpeed = 0.99;
		else if(backLeftSpeed <= -1)
			backLeftSpeed = -0.99;
		
		if(backRightSpeed >= 1)
			backRightSpeed = 0.99;
		else if(backRightSpeed <= -1)
			backRightSpeed = -0.99;
		
		bl.set(backLeftSpeed);
		br.set(backRightSpeed);
		fl.set(frontLeftSpeed);
		fr.set(frontRightSpeed);
//		double encoderTest = (double) -fle.getRate();
//		System.out.println (encoderTest);
	}
	
	public double checkFLE() {
		return -fle.getRate();
	}
	
	public double checkFRE() {
		return fre.getRate();
	}
	
	public double checkBLE() {
		return -ble.getRate();
	}
	
	public double checkBRE() {
		return bre.getRate();
	}
	
	public double getGyroAngle() {
		return gyro.getAngle();
	}
	
	
}
