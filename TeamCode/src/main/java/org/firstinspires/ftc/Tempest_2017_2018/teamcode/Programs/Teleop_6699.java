package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
/**
 * Created by Molly on 9/30/2017.
 */
@TeleOp
public class Teleop_6699 extends LinearOpMode
{
    // holonomic drive object instance
    HolonomicDrive Holodrive;
    
    // sleep fuction
    public void Sleep(long ticks) throws InterruptedException
    {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks)
        {
            idle();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        Holodrive = new HolonomicDrive();
        Holodrive.init(hardwareMap);

        double theta;
        double power;
        double pivotpower;

        double driveScale = 1;
        double turnScale =1;

        boolean JewelArmUp = false;

        waitForStart();
        while(opModeIsActive())
        {
            theta = -Math.PI / 4 + Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
            power = Math.sqrt((gamepad1.left_stick_x) * (gamepad1.left_stick_x) + (gamepad1.left_stick_y) * (gamepad1.left_stick_y));
            pivotpower = -gamepad1.right_stick_x;
            if (power > 0.2)
            {
                Holodrive.pan(theta, power * driveScale);
            } else if (Math.abs(pivotpower) > 0.1)
            {
                Holodrive.NE.setPower(pivotpower * turnScale);
                Holodrive.SE.setPower(pivotpower * turnScale);
                Holodrive.NW.setPower(-pivotpower * turnScale);
                Holodrive.SW.setPower(-pivotpower * turnScale);
            } else
            {
                Holodrive.stopmotors();
            }

            if (gamepad1.dpad_left)
            {
                //Button could change
                if (!JewelArmUp) {
                    Holodrive.jewelArm.jewelArmUp();
                    JewelArmUp = true;
                } else {
                    Holodrive.jewelArm.jewelArmDown();
                    JewelArmUp = false;
                }
                while (gamepad1.dpad_left) idle();
            }

            if (gamepad1.left_trigger>0.2 && gamepad1.right_trigger<0.2){
                Holodrive.glyphArm.lift();
            } else if (gamepad1.left_trigger< 0.2 && gamepad1.right_trigger>0.2) {
                Holodrive.glyphArm.lower();
            }else {
                Holodrive.glyphArm.stopLifting();
            }

            if (gamepad1.left_bumper && !gamepad1.right_bumper){
                Holodrive.glyphArm.grab();
            }else if (!gamepad1.left_bumper && gamepad1.right_bumper){
                Holodrive.glyphArm.release();
            }else{
                Holodrive.glyphArm.holdGrabPosition();
            }
        }
    }
}
