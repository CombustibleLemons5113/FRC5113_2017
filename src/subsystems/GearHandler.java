package subsystems;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class GearHandler
{
	//public Relay lightRelay;
	private int zone, mode;
	private boolean lightOn;
	
	public void init()
	{
		zone = 2;
		mode = -1;
		//lightRelay = new Relay(0);
		//lightRelay.setDirection(Direction.kBoth);
	}
	
	public void drive(DriveTrain dt, NTHandler2 nettab)
	{
		zone = nettab.getZone();
		mode = nettab.getMode();
		
		if(nettab.getDistance() < 16)
		{
			dt.mecanumDrive(0, 0, 0);
			System.out.println("Robot should be in place.");
		}
		else if(mode == 1)
		{
			if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 20 * Math.PI / 180, .2);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive(0.3, 0 * Math.PI / 180, 0);
			else if(nettab.getZone() == 3)
				dt.mecanumDrive(0.3, 340 * Math.PI / 180, -.2);
		}
		else if(mode == 2)
		{
			if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 20, 0);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive(0.3, 0, 0);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive(0.3, 340, 0);
		}
	}
	
	/*public void set0() {
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
	}*/
	
	/*public void disabledUpdate()
	{
		SmartDashboard.putBoolean("Light", false);
    	
    	if(lightOn)
    		lightRelay.set(Value.kOn);
    	else
    		lightRelay.set(Value.kOff);
	}*/ 
}