package auton;

import subsystems.AutonManager;
import subsystems.DriveTrain;

public abstract class GearFrame
{
	public abstract void update(DriveTrain dt);
	
	public AutonManager manager;
	
	public boolean flagCompleted;
}
