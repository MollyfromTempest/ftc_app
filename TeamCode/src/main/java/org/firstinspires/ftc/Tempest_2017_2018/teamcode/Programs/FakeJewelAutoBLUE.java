package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDriveForJewelAutonomous;

/**
 * Created by Molly on 10/20/2017.
 */
@Autonomous
public class FakeJewelAutoBLUE extends LinearOpMode {
    HolonomicDriveForJewelAutonomous Holodrive;

    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Holodrive = new HolonomicDriveForJewelAutonomous();
        Holodrive.init(hardwareMap);
        waitForStart();
        Holodrive.color.leftColor.enableLed(true);
        Holodrive.color.rightColor.enableLed(true);
        Holodrive.jewelArm.jewelArmDown();
        if (Holodrive.color.isBlue(Holodrive.color.leftColor) && Holodrive.color.isBlue(Holodrive.color.rightColor)) {
            //Both blue -- something is wrong!
            Sleep(30000);
        } else if (Holodrive.color.isRed(Holodrive.color.leftColor) && Holodrive.color.isRed(Holodrive.color.rightColor)) {
            //Both red -- something is wrong!
            Sleep(30000);
        } else if (Holodrive.color.isBlue(Holodrive.color.leftColor) && Holodrive.color.isRed(Holodrive.color.rightColor)) {
            // Left blue and right red. Since we are blue, we want to turn right.
            Holodrive.turnrightunlim(0.5, this);
            Sleep(2000);
            Holodrive.stopmotors();
        } else if (Holodrive.color.isRed(Holodrive.color.leftColor) && Holodrive.color.isRed(Holodrive.color.rightColor)) {
            // Left red and right blue. Since we are blue, we want to turn left.
            Holodrive.turnleftunlim(0.5, this);
            Sleep(2000);
            Holodrive.stopmotors();
        } else {
            //No reading? Ambigious reading? Either way, something is wrong and we don't want to risk turning.
            Sleep(30000);
        }
    }
}