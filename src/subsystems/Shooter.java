package subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Servo;

public class Shooter
{
	public Servo servo;
	public CANTalon shooterWheel;
	public CANTalon lowerOutput;
	
	public void init()
	{
		servo = new Servo(9);
		shooterWheel = new CANTalon(392142);
		lowerOutput = new CANTalon(324234);
	}
	
	public void update()
	{
		
	}
}
