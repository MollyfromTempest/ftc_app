package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;

/**
 * Created by Chris on 2/24/2018.
 */
@Autonomous
public class HowManyInchesIs1000TicksNWPiOverFour extends LinearOpMode{
    Robot2017_2018 Robot;
    long startticks;

    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Robot = new Robot2017_2018();
        Robot.init(hardwareMap);
        startticks = Math.abs(Robot.holoDrive.NW.getCurrentPosition());
        waitForStart();
        Robot.holoDrive.pan(Math.PI/4, 0.2);
        while(Math.abs(Robot.holoDrive.NW.getCurrentPosition()) < startticks + 1000){
            idle();
        }
        Robot.holoDrive.stopmotors();
    }
}
//1000 TICKS = EXACTLY 24 INCHES