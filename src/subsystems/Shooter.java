package subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter
{
	public Servo servo;
	//public TalonSRX shooterWheel;
	public CANTalon shooterWheel;
	public CANTalon intake;
	public CANTalon agitator;
	public CANTalon climber;
	private double voltage, range;
	
	private AnalogInput usrf;
	
	public void init()
	{
		servo = new Servo(0);
		intake = new CANTalon(5);
		agitator = new CANTalon(10);
		shooterWheel = new CANTalon(11);//should be 11
		climber = new CANTalon(12);//needs to be changed
		climber.enableBrakeMode(true);
		shooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		//shooterSpeed = 0;
        shooterWheel.reverseSensor(false);
        shooterWheel.setProfile(0);
        shooterWheel.setF(0.1097);//Found it on the internet - Andy
        shooterWheel.setP(0.22);//Found it on the internet - Andy
        shooterWheel.setI(0); 
        shooterWheel.setD(0);
        
        usrf = new AnalogInput(0);
	}
	
	@SuppressWarnings("deprecation")
	public void update()
	{
		SmartDashboard.putDouble("Distance: ", getDistanceMeters());
	}
	
	public double getDistanceMeters()
	{
		voltage = usrf.getVoltage();
		range = ((voltage * 1024) / 5) / 100;

		return range;
	}
}