package org.usfirst.frc.team5113.robot;

import subsystems.AutonManager;
import subsystems.DriveTrain;
import subsystems.JoystickManager;
import subsystems.Shooter;
import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
    private DriveTrain driveTrain;
    private JoystickManager controller;
    private Shooter shooter;
    private AutonManager manager;
    private double debounce;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    public void robotInit() {
        driveTrain = new DriveTrain();
        driveTrain.init();
        controller = new JoystickManager();
        controller.init();
        shooter = new Shooter();
        shooter.init();
        manager = new AutonManager();
        manager.init();
        double FLmotorCurrent = 0;
        debounce = -5000;
    }
    
    //Dont know if this will work
    public void disabledPeriodic()
    {
    	if(controller.getShooterWheelBack() && System.currentTimeMillis() - debounce > 500) 
        {
    		debounce = System.currentTimeMillis();
    		manager.changeMode(controller.getShooterWheelBack());
        }
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	manager.update(driveTrain);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	controller.update(driveTrain, shooter);
    	driveTrain.update(controller);
    	shooter.update();
    	
		//System.out.println("FL Speed: " + driveTrain.fl.getSpeed());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}