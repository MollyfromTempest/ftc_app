package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Device;
//import com.kauailabs.navx.ftc.AHRS;
//import com.kauailabs.navx.ftc.navXPIDController;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;

/**
 * Created by Molly on 12/2/2017.
 */
@Autonomous
public class NavXPIDTest extends LinearOpMode {
    Robot2017_2018 NavXBot;
    //navXPIDController yawPIDController;
    @Override
    public void runOpMode() throws InterruptedException {
        NavXBot = new Robot2017_2018(this);
        NavXBot.init(hardwareMap);
        waitForStart();
    }
}
