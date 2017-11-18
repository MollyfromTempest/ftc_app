package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;

/**
 * Created by Molly on 9/30/2017.
 */
@TeleOp
public class Teleop_6699 extends LinearOpMode {
    // holonomic drive object instance
    HolonomicDrive Holodrive;

    // sleep fuction
    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Holodrive = new HolonomicDrive();
        Holodrive.init(hardwareMap);

        double theta;
        double power;
        double pivotpower;
        //Scaling factors for speed
        double driveScale = 1;
        double turnScale = 1;

        boolean JewelArmUp = false; //arm starts down after autonomous
        boolean open = true; //default is that the grabber moves open

        waitForStart();
        while (opModeIsActive()) {
            //Define angle for pan function by using trigonometry to calculate it from joystick
            theta = -Math.PI / 4 + Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
            //Define power for pan function by using math to calculate the length of the hypotenuse between the x and y axes
            power = Math.sqrt((gamepad1.left_stick_x) * (gamepad1.left_stick_x) + (gamepad1.left_stick_y) * (gamepad1.left_stick_y));
            //Define power for turning, based solely on the x and y axes
            pivotpower = -gamepad1.right_stick_x;
            if (gamepad1.dpad_up) {
                //Toggles slow mode.
                if (turnScale == 1) {
                    //If it's fast, make it slow
                    driveScale = 0.25;
                    turnScale = 0.25;
                } else {
                    //If it's slow, make it fast
                    driveScale = 1;
                    turnScale = 1;
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
                    Holodrive.pan(theta, power * driveScale);
                } else if (Math.abs(pivotpower) > 0.1) {
                    //Only turn it if it isn't driving
                    Holodrive.NE.setPower(pivotpower * turnScale);
                    Holodrive.SE.setPower(pivotpower * turnScale);
                    Holodrive.NW.setPower(-pivotpower * turnScale);
                    Holodrive.SW.setPower(-pivotpower * turnScale);
                } else {
                    //Otherwise, don't move
                    Holodrive.stopmotors();
                }

                if (gamepad1.dpad_left) {
                    //Moves the jewel arm up and down with a toggle
                    if (!JewelArmUp) {
                        Holodrive.jewelArm.jewelArmUp();
                        JewelArmUp = true;
                    } else {
                        Holodrive.jewelArm.jewelArmDown();
                        JewelArmUp = false;
                    }
                    while (gamepad1.dpad_left) idle();
                }

                if (gamepad1.left_trigger > 0.2 && gamepad1.right_trigger < 0.2 && !gamepad1.dpad_down) {
                    Holodrive.glyphArm.release();
                } else if (gamepad1.left_trigger < 0.2 && gamepad1.right_trigger > 0.2 && !gamepad1.dpad_down) {
                    Holodrive.glyphArm.grab();
                    open = false;
                } else if (gamepad1.left_trigger<0.2 && gamepad1.right_trigger <0.2 && gamepad1.dpad_down){
                    Holodrive.glyphArm.sortOfRelease();
                }
            if (gamepad1.left_bumper){
                //Moves the left grabber, only if the button is being held
                Holodrive.glyphArm.grabArmLeft.setPosition(0.488);
                open = true;
            }else if(open){
                Holodrive.glyphArm.grabArmLeft.setPosition(0.82);
            }
            if (gamepad1.right_bumper){
                //Moves the right bumper, only if the button is being held
                Holodrive.glyphArm.grabArmRight.setPosition(0.371);
                open = true;
            }else if (open){
                Holodrive.glyphArm.grabArmRight.setPosition(0.059);
            }


                if (gamepad1.a && ((Math.abs(Holodrive.glyphArm.liftArm.getCurrentPosition()-Holodrive.glyphArm.LiftZeroPosition))>Holodrive.glyphArm.WiggleRoom)){
                    //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                    Holodrive.glyphArm.zeroPosition(this);
                } else if (gamepad1.b && ((Math.abs(Holodrive.glyphArm.liftArm.getCurrentPosition()-Holodrive.glyphArm.LiftMidPosition))>Holodrive.glyphArm.WiggleRoom)) {
                    //Moves to the middle position. Extra math stuff is designed so that it won't move if it's there or almost there.
                    Holodrive.glyphArm.midPosition(this);
                } else if (gamepad1.y && ((Math.abs(Holodrive.glyphArm.liftArm.getCurrentPosition()-Holodrive.glyphArm.LiftTopPosition))>Holodrive.glyphArm.WiggleRoom)) {
                    //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                    Holodrive.glyphArm.topPosition(this);
                } else if (gamepad1.x) {
                    Holodrive.glyphArm.incrementPosition(this);
                } else {
                    //Otherwise, maintains position
                    Holodrive.glyphArm.stopLifting();
                }
            }
        }
    }