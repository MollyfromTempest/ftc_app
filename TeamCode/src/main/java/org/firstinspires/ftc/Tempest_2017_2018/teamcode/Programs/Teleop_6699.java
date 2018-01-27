package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;

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
/*
            if (gamepad1.dpad_up && turnScale == 1) {
                driveScale = 0.25;
                turnScale = 0.25;
                idle();
            } else if (gamepad1.dpad_up && turnScale == 0.25) {
                driveScale = 1;
                turnScale = 1;
                idle();*/
            if (power > 0.2) {
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


            if (gamepad1.a && ((Math.abs(Robot.glyphArm.liftArm.getCurrentPosition() - Robot.glyphArm.LiftZeroPosition)) > Robot.glyphArm.WiggleRoom)) {
                //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                state = 0;
                Robot.glyphArm.zeroPosition(this);

            } else if (gamepad1.b && ((Math.abs(Robot.glyphArm.liftArm.getCurrentPosition() - Robot.glyphArm.LiftMidPosition)) > Robot.glyphArm.WiggleRoom)) {
                //Moves to the middle position. Extra math stuff is designed so that it won't move if it's there or almost there.
                state = 0.5;
                Robot.glyphArm.midPosition(this);

            } else if (gamepad1.y && ((Math.abs(Robot.glyphArm.liftArm.getCurrentPosition() - Robot.glyphArm.LiftTopPosition)) > Robot.glyphArm.WiggleRoom)) {
                //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                state = 1;
                Robot.glyphArm.topPosition(this);

            } else if (gamepad1.x) {
                Robot.glyphArm.incrementPosition(this);
            } else {
                //Otherwise, maintains position

                /**
                 * Notice how you write a similar position check each if statement?
                 * And how all the variables called are from the glyphArm object?
                 * Consider writing a boolean function in Glyph_Arm that checks to see if it's "close" to a position.
                 *
                 * Also, be careful when calling the lift arm functions a lot.
                 * Since this is a single thread, Sean cannot move the robot while you are calling zeroPosition, midPosition, or topPosition.
                 * How can you write this same loop without giving control over to those functions?
                 * You are able to access the actual lift motor object (Robot.glyphArm.liftArm),
                 * so you can do a single call with the DC motor rather than use the larger position methods.
                 *
                 * But this code captures what you want to have happen, though it might be jittery when driving it as is.
                 *
                 * -- Aaron
                 */

                if (Robot.glyphArm.liftArm.getCurrentPosition()> Robot.glyphArm.liftArm.getTargetPosition()+Robot.glyphArm.WiggleRoom){
                    Robot.glyphArm.lower();
                }
                else if (Robot.glyphArm.liftArm.getCurrentPosition()<Robot.glyphArm.liftArm.getTargetPosition()-Robot.glyphArm.WiggleRoom){
                    Robot.glyphArm.lift();
                }
                else {
                    Robot.glyphArm.liftArm.setPower(0);
                }
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