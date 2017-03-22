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
	
	public void drive(DriveTrain dt, NTHandler nettab)
	{
		zone = nettab.getZone();
		mode = nettab.getMode();
		double angle = dt.navx.getAngle();
		
		if(mode == -1)
		{
			System.out.println("Teleop auton is not currently available!");
		}
		else if(nettab.getDistance() < 16)
		{
			dt.mecanumDrive(0, 0, 0);
			System.out.println("Robot should be in place.");
		}
		else if(mode == 1)
		{
			System.out.println("Driving to peg - coarse");
			if(nettab.getZone() == 3)
				dt.mecanumDrive2(0.15, 60, 0.5, angle);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive2(0.15, 0, 0, angle);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.15, 300, -0.5, angle);
		}
		else if(mode == 2)
		{
			System.out.println("Driving to peg - fine");
			if(nettab.getZone() == 3)
				dt.mecanumDrive2(0.1, 90, 0, angle);
			else if(nettab.getZone() == 2)
				dt.mecanumDrive2(0.1, 0, 0, angle);
			else if(nettab.getZone() == 1)
				dt.mecanumDrive2(0.1, 270, 0, angle);
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