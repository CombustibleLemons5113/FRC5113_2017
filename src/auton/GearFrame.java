package auton;

import subsystems.AutonManager;
import subsystems.DriveTrain;
import subsystems.NTHandler;

public abstract class GearFrame
{
	public abstract void update(DriveTrain dt, NTHandler nettab);
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