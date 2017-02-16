package subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Encoder;

public class JoystickManager
{
	private Joystick joystick;
	private Joystick xboxController;
	
	private double mag, angle, rotation, gyroAngle;
	
	private JoystickButton resetGyro;
	private JoystickButton servoUp, servoDown;
	private JoystickButton shooterWheel, shooterWheelBack;
	private JoystickButton intakeIn, intakeOut;
	private JoystickButton gearDrive;
	
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
		joystick = new Joystick(0);
		xboxController = new Joystick(1);
		
		resetGyro = new JoystickButton(joystick, 2);
		
		//servoUp = new JoystickButton(xboxController, 3);
		//servoDown = new JoystickButton(xboxController, 0);
		shooterWheel = new JoystickButton(xboxController, xboxB);
		shooterWheelBack = new JoystickButton(xboxController, xboxY);
		intakeIn = new JoystickButton(xboxController, xboxA);
		intakeOut = new JoystickButton(xboxController, xboxX);
		gearDrive = new JoystickButton(xboxController, xboxRB);
		shooterSpeed = 0;
		speedThresehold = 0; //change to the desired speed once determined
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
		
		dt.mecanumDrive(mag, angle, rotation / 2);
	}
	
	public void handleXboxControls(Shooter shooter, DriveTrain dt, NTHandler nettab, GearHandler gearHandler)
	{
		/*if(servoUp.get())
			shooter.servo.setAngle(180);
		else if(servoDown.get())
			shooter.servo.setAngle(0);*/
		
		if(shooterWheel.get())
			shooter.shooterWheel.set(-.6);
		else if(shooterWheelBack.get())
			shooter.shooterWheel.set(0.6);
		else
			shooter.shooterWheel.set(0);
		
		if(gearDrive.get())
			gearHandler.drive(dt, nettab);
		
		/*if(intakeIn.get())
			shooter.intake.set(0.5);
		else if(intakeOut.get())
			shooter.intake.set(-0.5);
		else
			shooter.intake.set(0);*/
	}
	
	public double getXAxis() {
		return -joystick.getX();
	}
	
	public double getYAxis() {
		return -joystick.getY();
	}
	
	public double getZAxis() {
		return joystick.getZ();
	}
	
	public boolean getGyroReset() {
		return resetGyro.get();
	}
	
	public boolean getShooterWheelBack() {
		return shooterWheelBack.get();
	}
}