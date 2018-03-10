package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;

/**
 * Created by Aaron on 1/20/2018.
 */

@Autonomous
public class Run_to_Position_Test extends LinearOpMode {
    Robot2017_2018 Robot;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot = new Robot2017_2018(this);
        Robot.init(hardwareMap);
        waitForStart();

        Robot.glyphArm.liftArm.setTargetPosition(1000);

        while(Robot.glyphArm.liftArm.isBusy()){idle();}
    }
}
