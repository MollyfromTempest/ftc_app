package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Sweeper;

/**
 * Created by Molly on 6/10/2017.
 */
@Autonomous
public class SweeperTest extends LinearOpMode {
    Sweeper Broom;
    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }
    @Override
    public void runOpMode ()throws InterruptedException {
        Broom = new Sweeper();
        Broom.init(hardwareMap);
        waitForStart();
        Broom.intake(0.5);
        Sleep(2000);
        Broom.stop();
        Sleep(500);
        Broom.outtake(0.5);
        Sleep(2000);
        Broom.stop();
        Sleep(500);
        Broom.move(0.1);
        Sleep(500);
        Broom.move(0.5);
        Sleep(500);
        Broom.move(0.9);
        Sleep(500);
    }
}
