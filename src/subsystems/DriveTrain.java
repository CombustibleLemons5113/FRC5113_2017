package subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI.Port;

public class DriveTrain {
	
	//Not PWM WHYYYYYYYYYYYYYYYYYYYYYYYYY!!!!!!!!
	public CANTalon fl;
	public CANTalon fr;
	public CANTalon bl;
	public CANTalon br;
	
	public AHRS navx;
	
	private long startTime;
	private long elapsedTime;
	
	RobotDrive roboDrive;
	
	//AnalogGyro gyro;
	
	JoystickManager jm;
	
	public void init() {
		//Initialize and set to CAN IDs.
		//We can remap the IDs from within the web browser at roboRIO-5113.local

		fl = new CANTalon(14);//All of these needs to be changed
		fr = new CANTalon(1);
		bl = new CANTalon(15);
		br = new CANTalon(0);
		
		navx = new AHRS(I2C.Port.kOnboard);//idk
		
		fl.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        fl.reverseSensor(false);
        fl.setProfile(0);
        fl.setF(0.1097);//Found it on the internet - Andy
        fl.setP(0.22);//Found it on the internet - Andy
        fl.setI(0); 
        fl.setD(0);
        
        fr.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        fr.reverseSensor(false);
        fr.setProfile(0);
        fr.setF(0.1097);//Found it on the internet - Andy
        fr.setP(0.22);//Found it on the internet - Andy
        fr.setI(0); 
        fr.setD(0);
        
        bl.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        bl.reverseSensor(false);
        bl.setProfile(0);
        bl.setF(0.1097);//Found it on the internet - Andy
        bl.setP(0.22);//Found it on the internet - Andy
        bl.setI(0); 
        bl.setD(0);
        
        br.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        br.reverseSensor(false);
        br.setProfile(0);
        br.setF(0.1097);//Found it on the internet - Andy
        br.setP(0.22);//Found it on the internet - Andy
        br.setI(0); 
        br.setD(0);
		
		startTime = System.currentTimeMillis();
		
		roboDrive = new RobotDrive(bl, fl, br, fr);
		
		/*gyro = new AnalogGyro(0);
		gyro.initGyro();
		System.out.println("Gyro is now initiated\t" + gyro.getAngle());
		
		gyro.calibrate();*/
		navx.resetDisplacement(); //sets all displacement values (x,y,z) to zero
		navx.reset(); //resets Angle
	}
	
	public void update(JoystickManager jm) {
		elapsedTime = System.currentTimeMillis() - startTime;
		/*System.out.println("X: " + navx.getVelocityX() + "\nY: " + navx.getVelocityY() + "\nZ: " + navx.getVelocityZ());
		System.out.println("Angle: " + navx.getAngle());*/
	}

	//Controls the drive train
	public void mecanumDrive(double magnitude, double angle, double rotation)
	{			
		//Makes sure that magnitude fits into the range [0, 0.99] as expected. Hardware errors can otherwise cause small movement changes.
		magnitude = Math.min(Math.abs(magnitude), 0.99);
					
		//As the mecanum drive is X-Shaped, we must adjust to be at 45* angles.
		double newDirection = angle + (double) (Math.PI / 4);
		double cosine = (double) Math.cos(newDirection);
		double sine = (double) Math.sin(newDirection);
		
		double frontRightSpeed = (sine * magnitude - rotation);
		double frontLeftSpeed = -(cosine * magnitude - rotation);
		double backRightSpeed = -(cosine * magnitude + rotation);
		double backLeftSpeed = (sine * magnitude + rotation);		
		
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
		
		fl.set(frontLeftSpeed);
		fr.set(-frontRightSpeed);
		bl.set(backLeftSpeed);
		br.set(-backRightSpeed);
	}
	
	public double getXVelocity() {
		return (double)navx.getVelocityX();
	}
	
	public double getYVelocity() {
		return (double)navx.getVelocityY();
	}
	
	/*public double getGyroAngle() {
		return gyro.getAngle();
	}*/
}