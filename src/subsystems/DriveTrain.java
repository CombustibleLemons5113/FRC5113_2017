package subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveTrain {
	
	//Not PWM WHYYYYYYYYYYYYYYYYYYYYYYYYY!!!!!!!!
	public Talon fl;
	public Talon fr;
	public Talon bl;
	public Talon br;
	
	Encoder fle;
	Encoder fre;
	Encoder ble;
	Encoder bre;
	
	public double fri;
	public double fli;
	public double bri;
	public double bli;
	
	public double ifl;
	public double ifr;
	public double ibl;
	public double ibr;
	
	private long startTime;
	private long elapsedTime;
	
	RobotDrive roboDrive;
	
	AnalogGyro gyro;
	
	JoystickManager jm;
	
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
		
		fri = 0;
		fli = 0;
		bri = 0;
		bli = 0;
		
		ifl = 0;
		ifr = 0;
		ibl = 0;
		ibr = 0;
		
		startTime = System.currentTimeMillis();
		
		roboDrive = new RobotDrive(bl, fl, br, fr);
		
		gyro = new AnalogGyro(0);
		gyro.initGyro();
		System.out.println("Gyro is now initiated\t" + gyro.getAngle());
		
		gyro.calibrate();
	}
	
	public void update(JoystickManager jm) {
		elapsedTime = System.currentTimeMillis() - startTime;
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
		/*(if(angle >= 1.9)
			angle = Math.PI/2;
		else if (angle <= 1.3)
			angle = Math.PI/2;*/
					
		//As the mecanum drive is X-Shaped, we must adjust to be at 45* angles.
		double newDirection = angle + (double) (Math.PI / 4);
		//newDirection = (double) (newDirection * Math.PI) / 180;
		//System.out.println(newDirection);
		double cosine = (double) Math.cos(newDirection);//(double) Math.cos(newDirection);
		double sine = (double) Math.sin(newDirection);//(double) Math.sin(newDirection);
		
		double frontRightSpeed = (sine * magnitude - rotation);
		double frontLeftSpeed = -(cosine * magnitude - rotation);
		double backRightSpeed = -(cosine * magnitude + rotation);
		double backLeftSpeed = (sine * magnitude + rotation);
		
		/*double frontLeftSpeed = (sine * magnitude + rotation);
		double frontRightSpeed = -(cosine * magnitude + rotation);
		double backLeftSpeed = -(cosine * magnitude - rotation);
		double backRightSpeed = (sine * magnitude - rotation);*/
		
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
		
		/*fl.set(frontLeftSpeed);
		fr.set(frontRightSpeed);
		bl.set(backLeftSpeed);
		br.set(backRightSpeed);*/
		
		double kp = 0.01;
		
		//These variables are in revolutions per second
		double actualFLSpeed = checkFLE() / 360;
		double actualFRSpeed = checkFRE() / 360;
		double actualBLSpeed = checkBLE() / 360;
		double actualBRSpeed = checkBRE() / 360;
		
		double maxSpeed = 2.0;
		
		double ErrYFL = (frontLeftSpeed * maxSpeed) - actualFLSpeed;
		double ErrYFR = (frontRightSpeed * maxSpeed) - actualFRSpeed;
		double ErrYBL = (backLeftSpeed * maxSpeed) - actualBLSpeed;
		double ErrYBR = (backRightSpeed * maxSpeed) - actualBRSpeed;
		
		//Proportional
		fri += (kp * ErrYFR);
		fli -= (kp * ErrYFL);
		bri += (kp * ErrYBR);
		bli -= (kp * ErrYBL);
		
		//Intergral
		double ki = .01;
		
		
		
		
		System.out.println("FR Speed: " + actualFRSpeed);
		System.out.println("FL Speed: " + actualFLSpeed);
		System.out.println("BR Speed: " + actualBRSpeed);
		System.out.println("BL Speed: " + actualBLSpeed);
		System.out.println(backRightSpeed * maxSpeed);
		
		fl.set(fli);
		fr.set(fri);
		bl.set(bli);
		br.set(bri);
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