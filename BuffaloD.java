package org.firstinspires.ftc.teamcode.buffaloD;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 *   __________        _____  _____       .__           ____  ___
 *  \______   \__ ___/ ____\/ ____\____  |  |   ____   \   \/  /
 *  |    |  _/  |  \   __\\   __\\__  \ |  |  /  _ \   \     /
 *  |    |   \  |  /|  |   |  |   / __ \|  |_(  <_> )  /     \
 *  |______  /____/ |__|   |__|  (____  /____/\____/  /___/\  \
 *   \/                         \/                    \_/
 * Buffalo Wings OP Mode (Updated)
 *
 */

@TeleOp(name="BuffaloD-Update", group="Iterative Opmode")
public class BuffaloD extends OpMode
{
	// Op Mode Essential Variables

	private ElapsedTime runtime = new ElapsedTime(); // Defines the Up Time of the program
	private DcMotor leftBack = null; // Define Back Left Motor
	private DcMotor leftFront = null; // Define Front Left Motor
	private DcMotor rightBack = null; // Define Back Right Motor
	private DcMotor rightFront = null; // Define Front Right Motor
	private Servo arm_1 = null;
	private Servo arm_2 = null;
	private DcMotor armReel = null;
	private double lx1, rx1, ly1, ry2, lt1, rt1;
	private boolean lb1, rb1, rb2, toggle;
	private BuffaloDefinitions buff;

	//Driver starts the program

	@Override
	public void init() { // Initialize all components

		leftBack  = hardwareMap.get(DcMotor.class, "leftBack"); // Initialize Left Drive
		leftFront = hardwareMap.get(DcMotor.class, "leftFront"); // Define Right Drive
		rightBack  = hardwareMap.get(DcMotor.class, "rightBack"); // Define Left Drive
		rightFront  = hardwareMap.get(DcMotor.class, "rightFront"); // Define Left Drive
		arm_1 = hardwareMap.get(Servo.class, "servoArmRight");
		arm_2 = hardwareMap.get(Servo.class, "servoArmLeft");
		armReel = hardwareMap.get(DcMotor.class, "armReel");
		buff = new BuffaloDefinitions();
		buff.init(leftBack, leftFront, rightBack, rightFront, arm_1, arm_2, armReel, gamepad1, gamepad2);
	}

	@Override
	public void init_loop() {
	}

	@Override
	public void start() {
		runtime.reset();
	}

	@Override
	public void loop() {

		//Recieves controller input and assigns to values
		buff.inputController();

		//Mecanum Wheel Drive Controller
		buff.driveControl();

		//Deprecated Emergency Stop
		if(buff.eStop() == true) {telemetry.addData("EMERGENCY STOP", "ACTIVATED");}

		//Controller for the glyph arm
		toggle = buff.glyphArmControl();
		telemetry.addData("toggle", toggle);
	}

	@Override
	public void stop() { }
}
