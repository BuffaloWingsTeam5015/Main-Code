package org.firstinspires.ftc.teamcode.buffaloD;

/**
 * Created by CHSRobotics on 12/8/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.sun.tools.javac.tree.DCTree;

public class BuffaloDefinitions {

	private DcMotor leftBack = null; // Define Back Left Motor
	private DcMotor leftFront = null; // Define Front Left Motor
	private DcMotor rightBack = null; // Define Back Right Motor
	private DcMotor rightFront = null; // Define Front Right Motor
	private Servo arm_1 = null;
	private Servo arm_2 = null;
	private DcMotor armReel = null;
	private double lx1, rx1, ly1, ry2, lt1, rt1, lt2, rt2;
	private boolean lb1, rb1, rb2, toggle;
	private Gamepad gamepad1, gamepad2;

	public BuffaloDefinitions(){}

	public void init(DcMotor leftBack, DcMotor leftFront, DcMotor rightBack, DcMotor rightFront,
				Servo arm_1, Servo arm_2, DcMotor armReel, Gamepad gamepad1, Gamepad gamepad2) {
		this.gamepad1 = gamepad1;
		this.gamepad2 = gamepad2;
		this.leftBack  = leftBack; // Initialize Left Drive
		this.leftFront = leftFront; // Define Right Drive
		this.rightBack  = rightBack; // Define Left Drive
		this.rightFront  = rightFront; // Define Left Drive
		this.arm_1 = arm_1;
		this.arm_2 = arm_2;
		this.armReel = armReel;
		toggle = true;
	}

	public void inputController(){
		lx1 = gamepad1.left_stick_x;
		rx1 = gamepad1.right_stick_x;
		ly1 = gamepad1.left_stick_y;
		ry2 = gamepad2.right_stick_y;
		lt1 = gamepad1.left_trigger;
		rt1 = gamepad1.right_trigger;
		lb1 = gamepad1.left_bumper;
		rb1 = gamepad1.right_bumper;
		rb2 = gamepad2.right_bumper;
		lt2 = gamepad2.left_trigger;
		lb1 = gamepad2.left_bumper;
		rt2 = gamepad2.right_trigger;
	}

	public boolean glyphArmControl() {
		/*if(gamepad2.x == true) { toggle = false; } else if (gamepad2.y == true) { toggle = true; }
		if(toggle == true) {
			if(rb2) { armPosition(1,0); } else { armPosition(0,1); }
        } else if(toggle == false) {
            armPosition(rt2, (1-rt2));
		}*/

		if(rb2 == true) {
			armPosition(1,0);
		} else if(rt2 != 0) {
			armPosition(rt2, 1-rt2);
		} else {
			armPosition(0,1);
		}

		motorPower(armReel, -ry2);
		return toggle;
	}

	public void armPosition(double left, double right) {
		arm_1.setPosition(left);
		arm_2.setPosition(right);
	}

	public void driveControl() {

		if (lb1 == true && rb1 == true) {
			drive(0, 0, 0, 0);
		} else {
			drive(ly1 + lx1 - rx1, -ly1 + lx1 - rx1, -ly1 - lx1 - rx1, ly1 - lx1 - rx1);
		}
	}

	private void autoDrive(int x, int y, int t, int seconds) {
		drive(y + x - t, -y + x - t, -y - x - t, y - x - t);

	}



	public void drive( double V1, double V2, double V3, double V4) {

		motorPower(leftFront, V1);
		motorPower(leftBack, V3);
		motorPower(rightFront, V2);
		motorPower(rightBack, V4);
	}

	public void motorPower (DcMotor motor, double power) { motor.setPower(power); }

	public boolean eStop() {

		if(rt1 == 1 && lt2 == 1 || rt1 == 1 && lt1 == 1) {
			leftFront.close();
			leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			leftFront.setPower(0);

			leftBack.close();
			leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			leftBack.setPower(0);

			rightFront.close();
			rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			rightFront.setPower(0);

			rightBack.close();
			rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
			rightBack.setPower(0);
			return true;
		}

		if(gamepad2.left_trigger == 1 && gamepad2.right_trigger == 1) {
			arm_1.close();
			arm_1.setPosition(0);

			arm_2.close();
			arm_2.setPosition(0);
		}

		if(gamepad1.x && gamepad1.left_trigger == 1) {
			leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
			rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		}

		return false;
	}
}
