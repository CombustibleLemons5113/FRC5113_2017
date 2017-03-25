package org.usfirst.frc.team5113.robot;

import subsystems.AutonManager;
import subsystems.DriveTrain;
import subsystems.GearHandler;
import subsystems.JoystickManager;
import subsystems.NTHandler;
import subsystems.Shooter;
import edu.wpi.first.wpilibj.CameraServer;
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
    private GearHandler gearHandler;
    private NTHandler nettab;
    private double debounce, thyme;
    private int lightToggle;
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
        gearHandler = new GearHandler();
        gearHandler.init();
        nettab = new NTHandler();
        nettab.init();
        thyme = 0;
        debounce = -5000;
        lightToggle = 0;
        
        CameraServer.getInstance().startAutomaticCapture(0);
        CameraServer.getInstance().startAutomaticCapture(1);
    }
    
    //Dont know if this will work
    public void disabledPeriodic()
    {    	
    	if(controller.getChangeAuton() && System.currentTimeMillis() - debounce > 500) 
        {
    		debounce = System.currentTimeMillis();
    		manager.changeMode(controller.getChangeAuton());
        }
    	//nettab.update();
    	nettab.print();
    	System.out.println("Connected: " + nettab.getConnected());
    	
    	//shooter.climber.enableBrakeMode(true);
    	//controller.rumble(true);
    	
    	System.out.println("-----------------------------");
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
    	manager.update(driveTrain, nettab);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	System.out.println("Enabled");
    	controller.update(driveTrain, shooter, nettab, gearHandler);
    	driveTrain.update(controller);
    	shooter.update();
    	//nettab.update();
    	//gearHandler.drive(driveTrain, nettab);
    	
    	/*if(controller.getChangeLight() && System.currentTimeMillis() - thyme > 250) {
			++lightToggle;
			thyme = System.currentTimeMillis();
		}
    	if(lightToggle > 3)
    		lightToggle = 0;*/
    	
		/*if(lightToggle == 0)
			gearHandler.set0();
    	else if(lightToggle == 1)
    		gearHandler.set1();
    	else if(lightToggle == 2)
    		gearHandler.set0();
    	else if(lightToggle == 3)
    		gearHandler.set1();*/
    	
		//System.out.println("FL Speed: " + driveTrain.fl.getSpeed());
    	
    	nettab.print();
    	System.out.println("------------------------");
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}