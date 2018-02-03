package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Molly on 9/30/2017.
 */
@TeleOp
public class Teleop_6699 extends LinearOpMode {
    // holonomic drive object instance
    Robot2017_2018 Robot;

    // sleep fuction
    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    //Testing GitHub push

    @Override
    public void runOpMode() throws InterruptedException {
        Robot = new Robot2017_2018();
        Robot.init(hardwareMap);

        double theta;
        double power;
        double pivotpower;
        double state = 0;
    /*
    0: zero position
    0.5: mid position
    1: top position
     */
        //Scaling factors for speed
        double driveScale = 0.5;
        double turnScale = 0.5;

        Robot.jewelArm.jewelArmUp();
        boolean JewelArmUp = true; //arm starts up after autonomous
        boolean open = true; //default is that the grabber moves open

        double leftX;
        double leftY;
        double rightX;

        waitForStart();
        Robot.glyphArm.liftArm.setTargetPosition(Robot.glyphArm.LiftZeroPosition);
        Robot.glyphArm.liftArm.setPower(0.6);
        while (opModeIsActive()) {
            leftX = (float)scaleInput(gamepad1.left_stick_x);
            leftY = (float)scaleInput(gamepad1.left_stick_y);
            rightX = (float)scaleInput(gamepad1.right_stick_x);

            //Define angle for pan function by using trigonometry to calculate it from joystick
            theta = -Math.PI / 4 + Math.atan2(-leftY, -leftX);
            //Define power for pan function by using math to calculate the length of the hypotenuse between the x and y axes
            power = Math.pow(Math.sqrt((leftX) * (leftX) + (leftY) * (leftY)), 3);
            //Define power for turning, based solely on the x and y axes
            pivotpower = -rightX;

            telemetry.addData("JoyStickX", gamepad1.left_stick_x);
            telemetry.addData("ScaledX", leftX);
            telemetry.addData("JoyStickY", gamepad1.left_stick_y);
            telemetry.addData("ScaledY",leftY);
            //telemetry.addData("Power", power);
            telemetry.update();
            if (gamepad1.dpad_up) {
                //Toggles slow mode.
                if (turnScale == 0.5) {
                    //If it's fast, make it slow
                    driveScale = 0.3;
                    turnScale = 0.3;
                } else {
                    //If it's slow, make it fast
                    driveScale = 0.5;
                    turnScale = 0.5;
                }
                while (gamepad1.dpad_up) {
                    //Don't constantly change back and forth. One change per button press.
                    idle();
                }
            }

            if (power > 0.01) {
                //Power restrictions are so that random slightly-incorrect resets don't move the robot
                Robot.holoDrive.pan(theta, power * driveScale);
            } else if (Math.abs(pivotpower) > 0.1) {
                //Only turn it if it isn't driving
                Robot.holoDrive.NE.setPower(pivotpower * turnScale);
                Robot.holoDrive.SE.setPower(pivotpower * turnScale);
                Robot.holoDrive.NW.setPower(-pivotpower * turnScale);
                Robot.holoDrive.SW.setPower(-pivotpower * turnScale);
            } else {
                //Otherwise, don't move
                Robot.holoDrive.stopmotors();
            }
            /*
            float FrontLeft = (float)(-leftY + leftX - rightX);
            float FrontRight = (float)(leftY + leftX - rightX);
            float BackRight = (float)(leftY - leftX - rightX);
            float BackLeft = (float)(-leftY - leftX - rightX);

            // clip the right/left values so that the values never exceed +/- 1
            FrontRight = Range.clip(FrontRight, -1, 1);
            FrontLeft = Range.clip(FrontLeft, -1, 1);
            BackLeft = Range.clip(BackLeft, -1, 1);
            BackRight = Range.clip(BackRight, -1, 1);

            // write the values to the motors
            Robot.holoDrive.NE.setPower(-FrontRight*driveScale);
            Robot.holoDrive.NW.setPower(FrontLeft*driveScale);
            Robot.holoDrive.SW.setPower(BackLeft*driveScale);
            Robot.holoDrive.SE.setPower(-BackRight*driveScale);

            */

            //Move Jewel Arm
            if (gamepad1.dpad_left) {
                //Moves the jewel arm up and down with a toggle
                if (!JewelArmUp) {
                    Robot.jewelArm.jewelArmUp();
                    JewelArmUp = true;
                } else {
                    Robot.jewelArm.jewelArmDown();
                    JewelArmUp = false;
                }
                while (gamepad1.dpad_left) {
                    idle();
                }
            }

            if (gamepad1.left_trigger > 0.2 && gamepad1.right_trigger < 0.2 && !gamepad1.dpad_down) {
                Robot.glyphArm.release();
            } else if (gamepad1.left_trigger < 0.2 && gamepad1.right_trigger > 0.2 && !gamepad1.dpad_down) {
                Robot.glyphArm.grab();
                open = false;
            } else if (gamepad1.left_trigger < 0.2 && gamepad1.right_trigger < 0.2 && gamepad1.dpad_down) {
                Robot.glyphArm.sortOfRelease();
            }

            if (gamepad1.left_bumper) {
                //Moves the left grabber, only if the button is being held
                Robot.glyphArm.leftGrab();
                open = true;
            } else if (open) {
                Robot.glyphArm.leftRelease();
            }

            if (gamepad1.right_bumper) {
                //Moves the right bumper, only if the button is being held
                Robot.glyphArm.rightGrab();
                open = true;
            } else if (open) {
                Robot.glyphArm.rightRelease();
            }

            if (gamepad1.a) {
                //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                state = 0;
                Robot.glyphArm.liftArm.setTargetPosition(Robot.glyphArm.LiftZeroPosition);

            } else if (gamepad1.b) {
                //Moves to the middle position. Extra math stuff is designed so that it won't move if it's there or almost there.
                state = 0.5;
                Robot.glyphArm.liftArm.setTargetPosition(Robot.glyphArm.LiftMidPosition);

            } else if (gamepad1.y) {
                //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                state = 1;
                Robot.glyphArm.liftArm.setTargetPosition(Robot.glyphArm.LiftTopPosition);

            } else if (gamepad1.x) {
                Robot.glyphArm.incrementPosition(this);
            } else {
            }
        }
    }
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}