package subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class GearHandler
{
	public Relay lightRelay;
	private int zone;
	private boolean lightOn;
	
	public void init()
	{
		zone = 2;
		lightRelay = new Relay(0);
		lightRelay.setDirection(Direction.kBoth);
	}
	
	public void drive(DriveTrain dt, NTHandler nettab)
	{
		zone = nettab.getZone();
		System.out.println(zone + "\t" + nettab.getArea());
		
		if(nettab.getArea() < 20000)
		{
			if(zone == 1)
				dt.mecanumDrive(0.2, 170 * Math.PI / 180, -0.2);
			else if(zone == 2)
				dt.mecanumDrive(0.2, 180 * Math.PI / 180, 0);
			else if(zone == 3)
				dt.mecanumDrive(0.2, 190 * Math.PI / 180, 0.2);
		}
		else
			System.out.println("Done!");
	}
	
	public void set0() {
		lightRelay.set(Value.kOff);
	}
	
	public void set1() {
		lightRelay.set(Value.kForward);
	}
	
	public void set2() {
		lightRelay.set(Value.kReverse);
	}
	
	public void set3() {
		lightRelay.set(Value.kOn);
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