package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;
//import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.NavX;

/**
 * Created by Chris on 1/6/2018.
 */
@Autonomous
public class NavX_Test extends LinearOpMode {
    Robot2017_2018 Robot;
    public NavX_Test(){}

    @Override
    public void runOpMode() throws InterruptedException {
        Robot = new Robot2017_2018(this);

        telemetry.addData("Debug:", "Init Start");
        telemetry.update();

        Robot.init(hardwareMap);

        telemetry.addData("Debug:", "Init Done");
        telemetry.update();

        waitForStart();

        while( opModeIsActive() ) {
            //telemetry.addData("Rotation", Robot.navx.getRotation());
            telemetry.update();
        }
    }
}
