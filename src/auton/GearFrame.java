package auton;

import subsystems.AutonManager;
import subsystems.DriveTrain;

public abstract class GearFrame
{
	public AutonManager manager;
	
	public double m, a, r;
	
	public boolean flagCompleted;
	
	public void drive(double mag, double angle, double rotation)
	{	
		m = mag;
		a = angle;
		r = rotation;
	}
}