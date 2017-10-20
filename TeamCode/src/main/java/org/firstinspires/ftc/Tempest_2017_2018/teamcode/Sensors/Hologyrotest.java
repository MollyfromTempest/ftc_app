package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;


/**
 * Created by Molly on 5/27/2017.
 */
@Autonomous
public class Hologyrotest extends LinearOpMode{
   HolonomicDrive Holodrive;
    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }
    @Override
    public void runOpMode() throws InterruptedException{
        Holodrive = new HolonomicDrive();
        Holodrive.init(hardwareMap);
        waitForStart();
        Holodrive.turnleft(45, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnright(45, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnleft(90, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnright(90, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnleft(180, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnright(180, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnleft(270, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnright(270, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnleft(360, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
        Holodrive.turnright(360, 0.5, this);
        Holodrive.stopmotors();
        Sleep(2000);
    }
}
