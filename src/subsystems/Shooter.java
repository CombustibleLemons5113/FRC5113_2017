package subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Shooter
{
	public Servo servo;
	//public TalonSRX shooterWheel;
	public CANTalon shooterWheel;
	public CANTalon intake;
	
	public void init()
	{
		//servo = new Servo(9);
		//shooterWheel = new CANTalon();
		//intake = new CANTalon();
		
		shooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        shooterWheel.reverseSensor(false);
        shooterWheel.setProfile(0);
        shooterWheel.setF(0.1097);//Found it on the internet - Andy
        shooterWheel.setP(0.22);//Found it on the internet - Andy
        shooterWheel.setI(0); 
        shooterWheel.setD(0);
	}
	
	public void update()
	{
		
	}
	
}