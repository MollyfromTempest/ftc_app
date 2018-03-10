package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;

/**
 * Created by Chris on 2/24/2018.
 */
@Autonomous
public class HowManyDegreesIs200TicksNW extends LinearOpMode {
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
        Robot = new Robot2017_2018(this);
        Robot.init(hardwareMap);
        startticks = Math.abs(Robot.holoDrive.NW.getCurrentPosition());
        waitForStart();
        Robot.holoDrive.turnrightunlim(0.4);
        while (Math.abs(Robot.holoDrive.NW.getCurrentPosition()) < startticks + 200){
            idle();
        }
        Robot.holoDrive.stopmotors();
    }
}
//200 TICKS = 36 DEGREES