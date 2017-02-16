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
	private double shooterSpeed;
	private double speedThresehold;
	private double startTime;
	private double endTime;
	private boolean isStart;
	public void init()
	{
		//servo = new Servo(9);
		shooterWheel = new CANTalon(11);
		shooterWheel.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		speedThresehold = 0;
		isStart = false;
		//intake = new CANTalon();
		shooterSpeed = 0;
        shooterWheel.reverseSensor(false);
        shooterWheel.setProfile(0);
        shooterWheel.setF(0.1097);//Found it on the internet - Andy
        shooterWheel.setP(0.22);//Found it on the internet - Andy
        shooterWheel.setI(0); 
        shooterWheel.setD(0);
	}
	
	public void update()
	{
		shooterSpeed = shooterWheel.getSpeed();
		System.out.println("Current Speed: "+ shooterSpeed);
		
		/*if(Math.abs(shooterSpeed)<speedThresehold*.9 && (!isStart)){
			startTime = System.currentTimeMillis();
			isStart = true;
		}
		else if((Math.abs(shooterSpeed)>speedThresehold*.9) && (Math.abs(shooterSpeed)<speedThresehold*.98)){
			endTime = System.currentTimeMillis();
			System.out.println("change: "+ (endTime-startTime));
			isStart = false;
		}
		*/
		//speedThresehold = (speedThresehold + shooterSpeed)/2.0;
			
	}
	
}