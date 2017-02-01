package subsystems;

import edu.wpi.first.wpilibj.Servo;

public class Shooter
{
	public Servo servo;
	
	public void init()
	{
		servo = new Servo(0);
	}
	
	public void update()
	{
		
	}
}
