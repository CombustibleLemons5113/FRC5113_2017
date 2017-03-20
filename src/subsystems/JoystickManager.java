package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public class JoystickManager
{
	private Joystick joystick;
	private Joystick xboxController;
	
	private double mag, angle, rotation, gyroAngle;
	private long time, time2, t, time3, time4;
	private boolean agitatorToggle;
	
	private JoystickButton resetGyro;
	private JoystickButton shooterMode;
	private JoystickButton rustleMyJimmies;
	private JoystickButton intakeIn, intakeOut;
	private JoystickButton gearDrive;
	private JoystickButton changeAuton, changeLight;
	
	private final int xboxA = 1;
	private final int xboxB = 2;
	private final int xboxX = 3;
	private final int xboxY = 4;
	private final int xboxLB = 5;
	private final int xboxRB = 6;
	private final int xboxBACK = 7;
	private final int xboxSTART = 8;
	private final int xboxLS = 9;
	private final int xboxRS = 10;
	
	public double shooterSpeed;
	public double speedThresehold;
	
	public void init()
	{
		time = System.currentTimeMillis();
		time3 = System.currentTimeMillis();
		agitatorToggle = false;
		
		joystick = new Joystick(0);
		xboxController = new Joystick(1);
		
		rustleMyJimmies = new JoystickButton(joystick, 7);
		resetGyro = new JoystickButton(joystick, 2);
		
		shooterMode = new JoystickButton(xboxController, xboxB);
		intakeIn = new JoystickButton(xboxController, xboxA);
		intakeOut = new JoystickButton(xboxController, xboxX);
		gearDrive = new JoystickButton(xboxController, xboxRB);
		shooterSpeed = 0;
		speedThresehold = 0; //change to the desired speed once determined
		changeAuton = new JoystickButton(xboxController, xboxSTART);
		changeLight = new JoystickButton(xboxController, xboxBACK);
	}
	
	public void update(DriveTrain driveTrain, Shooter shooter, NTHandler nettab, GearHandler gearHandler) {
		handleJoystickDrive(driveTrain);
		handleXboxControls(shooter, driveTrain, nettab, gearHandler);
		//System.out.println("FLE :" + driveTrain.checkFLE() / 360 + "\nFRE :" + driveTrain.checkFRE() / 360 + "\nBLE :" + driveTrain.checkBLE() / 360 + "\nBRE :" + driveTrain.checkBRE() / 360);
		
		/*if(getGyroReset())
			driveTrain.gyro.reset();*/
	}
	
	private void handleJoystickDrive(DriveTrain dt)
	{
		double x = getXAxis();
		double y = getYAxis();
		double z = getZAxis();
		
		if (x > 0.99)
			x = 0.99;
		else if (x < -0.99)
			x = -0.99;
		
		if (Math.abs(x) < 0.05)
			x = 0;
		
		if (y > 0.99)
			y = 0.99;
		else if (y < -0.99)
			y = -0.99;
		
		if (Math.abs(y) < 0.05)
			y = 0;
		
		if (z > 0.99)
			z = 0.99;
		else if (z < -0.99)
			z = -0.99;
		
		if (Math.abs(z) < 0.4)
			z = 0;
		else if (z >= 0.4)
			z = z - 0.4;
		else if (z <= -0.4)
			z = z + 0.4;
		
		mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		angle = Math.atan2(y, x);
		rotation = z;
		//gyroAngle = dt.getGyroAngle();
		//System.out.println(gyroAngle);
		//System.out.println((angle / Math.PI) * 180);
		//System.out.println("Mag: " + mag + "\nAngle: " + angle + "\nRotation: " + rotation);
		/*if(rustleMyJimmies.get()) {
			if(System.currentTimeMillis() - time3 > 500) {
				time3 += System.currentTimeMillis();
				time4 = System.currentTimeMillis();
				dt.mecanumDrive(Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2)), Math.atan2(0, 0), 1);
			}
			else if(System.currentTimeMillis() - time4 > 500) {
				time4 += System.currentTimeMillis();
				time3 = System.currentTimeMillis();
				dt.mecanumDrive(Math.sqrt(Math.pow(0, 2) + Math.pow(0, 2)), Math.atan2(0, 0), -1);
			}
		}
		else {
			dt.mecanumDrive(mag, angle, rotation / 2);
		}*/
		System.out.println(dt.getNavAngle());
		dt.fod(x, y, z / 2, dt.getNavAngle());
		//dt.tankDrive(leftValue, rightValue);
	}
	
	public void handleXboxControls(Shooter shooter, DriveTrain dt, NTHandler nettab, GearHandler gearHandler)
	{
		if(shooterMode.get() && System.currentTimeMillis() - t > 500) {
			agitatorToggle = !agitatorToggle;
			t = System.currentTimeMillis();
		}
		if(agitatorToggle) {
			shooter.servo.setAngle(180);
			shooter.shooterWheel.set(-0.85);
			
			if(System.currentTimeMillis() - t > 1000) {
				if(System.currentTimeMillis() - time > 2000) {
					time += System.currentTimeMillis();
					time2 = System.currentTimeMillis();
					shooter.agitator.set(0.99);
				}
				else if(System.currentTimeMillis() - time2 > 500) {
					time2 += System.currentTimeMillis();
					time = System.currentTimeMillis();
					shooter.agitator.set(-0.99);
				}
			}
		}
		else {
			shooter.agitator.set(0);
			shooter.servo.setAngle(0);
			shooter.shooterWheel.set(0);
		}
		
		/*if(shooterWheel.get()){
			System.out.println("Not a Software Problem");//"Yeah, it's a software problem." -zbross 2017 (It wasn't a software problem)
			shooter.shooterWheel.set(-.8);
		}
		else if(shooterWheelBack.get())
			shooter.shooterWheel.set(0.8);
		else
			shooter.shooterWheel.set(0);*/
		
		if(gearDrive.get())
			gearHandler.drive(dt, nettab);
		
		/*if(stopAgit.get())
			shooter.agitator.set(0);
		else
			shooter.agitator.set(-0.5);
		else if(System.currentTimeMillis() - time > 1000) {
			time += System.currentTimeMillis();
			time2 = System.currentTimeMillis();
			shooter.agitator.set(0.5);
		}
		else if(System.currentTimeMillis() - time2 > 1000) {
			time2 += System.currentTimeMillis();
			time = System.currentTimeMillis();
			shooter.agitator.set(-0.5);
		}*/
		
		/*shooter.agitator.set(-xboxController.getRawAxis(3));
		System.out.println("Boi: " + xboxController.getRawAxis(3));*/
		if(intakeIn.get()) {
			shooter.intake.set(0.99);
			//rumble(true);
		}
		else if(intakeOut.get()) {
			shooter.intake.set(-0.99);
			//rumble(true);
		}
		else {
			shooter.intake.set(0);
			//rumble(false);
		}
	}
	
	public double getXAxis() {
		return joystick.getX();
	}
	
	public double getYAxis() {
		return joystick.getY();
	}
	
	public double getZAxis() {
		return joystick.getZ();
	}
	
	public boolean getGyroReset() {
		return resetGyro.get();
	}
	
	public boolean getChangeAuton() {
		return changeAuton.get();
	}
	
	public boolean getChangeLight() {
		return changeLight.get();
	}
	
	public void rumble(boolean state) {
		if(state) {
			xboxController.setRumble(RumbleType.kLeftRumble, 1);
			xboxController.setRumble(RumbleType.kRightRumble, 1);
		}
		else {
			xboxController.setRumble(RumbleType.kLeftRumble, 0);
			xboxController.setRumble(RumbleType.kRightRumble, 0);
		}
	}
}