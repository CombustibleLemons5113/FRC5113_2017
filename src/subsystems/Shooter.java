package subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;

public class Shooter
{
	public Servo servo;
	public Talon shooterWheel;
	public Talon intake;
	
	public void init()
	{
		//servo = new Servo(9);
		shooterWheel = new Talon(2);
		//intake = new Talon(420);
	}
	
	public void update()
	{
		
	}
}
