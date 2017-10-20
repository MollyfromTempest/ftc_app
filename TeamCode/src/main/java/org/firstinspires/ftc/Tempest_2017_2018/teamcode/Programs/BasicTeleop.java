package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.SuperBasicHolonomicDrive;

/**
 * Created by Molly on 5/27/2017.
 */
@TeleOp
public class BasicTeleop extends LinearOpMode {
    /*
    SuperBasicHolonomicDrive allows this to run.
    Super Basic means that this can only drive in different directions. It can't turn, and there are no exciting sensor things.
     */
    SuperBasicHolonomicDrive Holodrive;

    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Holodrive = new SuperBasicHolonomicDrive();
        Holodrive.init(hardwareMap);
        double theta;
        double power;
        double driveScale = 1;
        waitForStart();
        while(opModeIsActive()){
            theta = -Math.PI / 4 + Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
            power = Math.sqrt((gamepad1.left_stick_x) * (gamepad1.left_stick_x) + (gamepad1.left_stick_y) * (gamepad1.left_stick_y));
            if (power > 0.2) {
                Holodrive.pan(theta, power * driveScale);
            }else{
                Holodrive.stopmotors();
            }
        }
    }
}