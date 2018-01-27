package subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter
{
	public Servo servo;
	//public TalonSRX shooterWheel;
	public WPI_TalonSRX shooterWheel;
	public WPI_TalonSRX intake;
	public WPI_TalonSRX agitator;
	public WPI_TalonSRX climber;
	private double voltage, range;
	
	private AnalogInput usrf;
	
	public void init()
	{
		servo = new Servo(0);
		intake = new WPI_TalonSRX(5);
		agitator = new WPI_TalonSRX(10);
		shooterWheel = new WPI_TalonSRX(11);//should be 11
		climber = new WPI_TalonSRX(4);
		climber.setNeutralMode(NeutralMode.Brake);
		shooterWheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		//shooterSpeed = 0;
        shooterWheel.setSensorPhase(false);
        shooterWheel.selectProfileSlot(0, 0);
        shooterWheel.config_kF(0, 0.1097, 0);//Found it on the internet - Andy
        shooterWheel.config_kP(0, 0.22, 0);//Found it on the internet - Andy
        shooterWheel.config_kI(0, 0, 0); 
        shooterWheel.config_kD(0, 0, 0);
        
        usrf = new AnalogInput(0);
	}
	
	public void update()
	{
		voltage = usrf.getVoltage();
		range = ((voltage * 1024) / 5) / 100;
		
		SmartDashboard.putNumber("Range (meters) (Shooter)", range);
	}
	
	public double getDistanceMeters()
	{
		voltage = usrf.getVoltage();
		range = ((voltage * 1024) / 5) / 100;
		return range;
	}
}