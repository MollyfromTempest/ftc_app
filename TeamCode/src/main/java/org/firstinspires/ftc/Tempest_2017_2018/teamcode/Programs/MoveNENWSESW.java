package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.SuperBasicHolonomicDrive;

/**
 * Created by Molly on 10/14/2017.
 */
@Autonomous
public class MoveNENWSESW extends LinearOpMode{
        SuperBasicHolonomicDrive Holodrive;
        public void Sleep(long ticks) throws InterruptedException {
            long timer = System.currentTimeMillis();
            while (System.currentTimeMillis() - timer < ticks) {
                idle();
            }
        }
        @Override
        public void runOpMode ()throws InterruptedException {
            Holodrive = new SuperBasicHolonomicDrive();
            Holodrive.init(hardwareMap);
            waitForStart();
            Holodrive.NE.setPower(0.5);
            Sleep(2000);
            Holodrive.stopmotors();
            Sleep(1000);
            Holodrive.NW.setPower(0.5);
            Sleep(2000);
            Holodrive.stopmotors();
            Sleep(1000);
            Holodrive.SE.setPower(0.5);
            Sleep(2000);
            Holodrive.stopmotors();
            Sleep(1000);
            Holodrive.SW.setPower(0.5);
            Sleep(2000);
            Holodrive.stopmotors();
            Sleep(1000);
        }
    }