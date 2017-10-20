package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;

/**
 * Created by Molly on 5/27/2017.
 */
    @Autonomous
    public class HolonomicDriveTest extends LinearOpMode {

        HolonomicDrive Holodrive;
        public void Sleep(long ticks) throws InterruptedException {
            long timer = System.currentTimeMillis();
            while (System.currentTimeMillis() - timer < ticks) {
                idle();
            }
    }
    @Override
    public void runOpMode ()throws InterruptedException {
        Holodrive = new HolonomicDrive();
        Holodrive.init(hardwareMap);
        waitForStart();
        Holodrive.pan(Math.PI / 2, 0.5);
        Sleep(2000);
        Holodrive.stopmotors();
        Holodrive.pan(3*Math.PI/2, 0.5);
        Sleep(2000);
        Holodrive.stopmotors();
        Holodrive.pan(Math.PI, 0.5);
        Sleep(2000);
        Holodrive.stopmotors();
        Holodrive.pan(Math.PI/4, 0.5);
        Sleep(2000);
        Holodrive.stopmotors();
        Sleep(1000);
        Holodrive.turnleftunlim(0.5, this);
        Sleep(2000);
        Holodrive.stopmotors();
        Holodrive.turnrightunlim(0.5, this);
        Holodrive.stopmotors();

    }
}
