package subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Shooter
{
	public Servo servo;
	//public TalonSRX shooterWheel;
	public Talon shooterWheel;
	public TalonSRX intake;
	
	public void init()
	{
		//servo = new Servo(9);
		shooterWheel = new Talon(3);//Needs to be changed
		//intake = new TalonSRX(420);
	}
	
	public void update()
	{
		
	}
}
