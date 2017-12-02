package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Jewel_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.ColorSensorClass;

/**
 * Created by Molly on 9/30/2017.
 */
@Autonomous
public class Jewel_Autonomous extends LinearOpMode{
    Robot2017_2018 TempestRobot;

    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    @Override
    public void runOpMode()throws InterruptedException{
        TempestRobot = new Robot2017_2018();
        TempestRobot.init(hardwareMap);
        waitForStart();
        //Turn on the color sensor LEDS
        TempestRobot.color.leftColor.enableLed(true);
        TempestRobot.color.rightColor.enableLed(true);
        //Lower the jewel arm -- necessary regardless of color
        TempestRobot.jewelArm.jewelArmDown();
        //Wait for two seconds so that it has time to lower
        Sleep(2000);
        boolean rightBlue = TempestRobot.color.isBlue(TempestRobot.color.rightColor); //right color sensor is blue
        boolean rightRed = TempestRobot.color.isRed(TempestRobot.color.rightColor); //right color sensor is red
        boolean leftBlue = TempestRobot.color.isBlue(TempestRobot.color.leftColor); //left color sensor is blue
        boolean leftRed = TempestRobot.color.isRed(TempestRobot.color.leftColor); //left color sensor is red


        if (TempestRobot.BlueSwitch.getState()) {
            //RED RED RED
            telemetry.addData("Right blue", rightBlue);
            telemetry.addData("Right red", rightRed);
            telemetry.addData("Left blue", leftBlue);
            telemetry.addData("Left red", leftRed);


            if (leftBlue && rightBlue) {
                //Both blue -- something is wrong!
                telemetry.addData("Color sensor", "Both blue");

                //Sleep(30000);
            } else if (leftRed && rightRed) {
                //Both red -- something is wrong!
                telemetry.addData("Color sensor", "Both red");
                //Sleep(30000);
            } else if (leftBlue && !rightBlue) {
                // Left blue and right not specified (but not also blue). Since we are red, we want to turn left.
                telemetry.addData("Color sensor", "Left blue, right red or unspecified");
                TempestRobot.holoDrive.turnleftunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            } else if (leftRed && !rightRed) {
                // Left red and right not specified (but not also red). Since we are red, we want to turn right.
                telemetry.addData("Color sensor", "Left red, right blue or unspecified");
                TempestRobot.holoDrive.turnrightunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            }else if(!leftBlue && rightBlue){
                //Right blue and left not specified (but not also blue). Since we are red, we want to turn right.
                telemetry.addData("Color sensor", "Left red or unspecified, right blue");
                TempestRobot.holoDrive.turnrightunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            }else if (!leftRed && rightRed){
                //Right red and left not specified (but not also red). Since we are red, we want to turn left.
                telemetry.addData("Color sensor", "Left blue or unspecified, right red");
                TempestRobot.holoDrive.turnleftunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            }else {
                //No reading? Ambiguous reading? Either way, something is wrong and we don't want to risk turning.
                telemetry.addData("Color sensor", "No reading");
                //Sleep(30000);
            }
        }
        else{
            //BLUE BLUE BLUE
            telemetry.addData("Right blue", rightBlue);
            telemetry.addData("Right red", rightRed);
            telemetry.addData("Left blue", leftBlue);
            telemetry.addData("Left red", leftRed);


            if (leftBlue && rightBlue){
                //Both blue -- something is wrong!
                telemetry.addData ("Color sensor", "Both blue");

                //Sleep(30000);
            } else if (leftRed && rightRed){
                //Both red -- something is wrong!
                telemetry.addData ("Color sensor", "Both red");
                //Sleep(30000);
            } else if (leftBlue && !rightBlue){
                // Left blue and right not specified (but not also blue). Since we are blue, we want to turn right.
                telemetry.addData ("Color sensor", "Left blue, right red or unspecified");
                TempestRobot.holoDrive.turnrightunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            } else if (leftRed && !rightRed){
                // Left red and right unspecified (but not also red). Since we are blue, we want to turn left.
                telemetry.addData ("Color sensor", "Left red, right blue or unspecified");
                TempestRobot.holoDrive.turnleftunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            } else if (!leftBlue && rightBlue){
                //Right blue and left unspecified (but not also blue). Since we are blue, we want to turn left.
                telemetry.addData ("Color sensor", "Left red or unspecified, right blue");
                TempestRobot.holoDrive.turnleftunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            }else if (!leftRed && rightRed){
                //Right red and left unspecified (but not also red). Since we are blue, we want to turn right.
                telemetry.addData ("Color sensor", "Left blue or unspecified, right red");
                TempestRobot.holoDrive.turnrightunlim(0.2);
                Sleep(500);
                TempestRobot.holoDrive.stopmotors();
                TempestRobot.jewelArm.jewelArmUp();
            }else {
                //No reading? Ambiguous reading? Either way, something is wrong and we don't want to risk turning.
                telemetry.addData("Color sensor", "No reading");
                //Sleep(30000);
            }
        }
        telemetry.update();
        sleep(30000); //Don't do anything else until autonomous period ends
    }
}