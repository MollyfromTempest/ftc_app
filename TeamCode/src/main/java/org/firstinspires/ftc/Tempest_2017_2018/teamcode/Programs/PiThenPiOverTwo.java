package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;

/**
 * Created by AshQuinn on 2/24/18.
 */

@Autonomous
public class PiThenPiOverTwo extends LinearOpMode {
    Robot2017_2018 Robot;

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
        Robot.holoDrive.pan(Math.PI, 0.3);
        Sleep(2000);
        Robot.holoDrive.stopmotors();
        Robot.holoDrive.pan(Math.PI/2, 0.3);
        Sleep(2000);
        Robot.holoDrive.stopmotors();
    }
}
