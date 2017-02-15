package auton;

import subsystems.AutonManager;
import subsystems.DriveTrain;
import subsystems.NTHandler;

public abstract class GearFrame
{
	public abstract void update(DriveTrain dt, NTHandler nettab);
	
	public AutonManager manager;
	
	public boolean flagCompleted;
}
