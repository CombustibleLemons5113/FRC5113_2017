package subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class GearHandler2
{
	public Relay lightRelay;
	private int distance, zone;
	private boolean lightOn;
	
	public void init()
	{
		zone = 2;
		lightRelay = new Relay(0);//set 0 to the port number
	}
	
	public void drive(DriveTrain dt, NTHandler2 nettab)
	{
		//used in teleop when holding one of the xbox buttons
	}
	
	public void setOff() {
		lightRelay.set(Value.kOff);
		lightRelay.set(Value.kReverse);
	}
	
	public void setOn() {
		lightRelay.set(Value.kOn);
		lightRelay.set(Value.kForward);
	}
	
	/*public void disabledUpdate()
	{
		SmartDashboard.putBoolean("Light", false);
    	
    	if(lightOn)
    		lightRelay.set(Value.kOn);
    	else
    		lightRelay.set(Value.kOff);
	}*/ 
}