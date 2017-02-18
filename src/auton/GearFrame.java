package auton;

import subsystems.AutonManager;
import subsystems.DriveTrain;
import subsystems.NTHandler;

public abstract class GearFrame
{
	public abstract void update(DriveTrain dt);
	public AutonManager manager;
	public DriveTrain dt;
	
	public double m, a, r;
	public double distance;
	
	public boolean flagCompleted;
	
	public void drive(double mag, double angle, double rotation)
	{	
		m = mag;
		a = angle;
		r = rotation;
	}
}