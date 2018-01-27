package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.internal.usb.exception.RobotUsbTimeoutException;

/**
 * Created by Chris on 1/13/2018.
 */

@Autonomous
public class Vuforia_Driving_Test extends LinearOpMode {
    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    Robot2017_2018 Robot;

    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    @Override
    public void runOpMode()throws InterruptedException{
        Robot = new Robot2017_2018();
        Robot.init(hardwareMap);
        //Turn on the color sensor LEDS
        Robot.color.leftColor.enableLed(true);
        Robot.color.rightColor.enableLed(true);
        double Speed = 0.1;
        boolean BlueTeam = !Robot.BlueSwitch.getState();
        if(BlueTeam){
            telemetry.addData("Team Color", "Blue");
        }else{
            telemetry.addData("Team Color", "Red");
        }
        boolean LeftSide = Robot.LeftRightSwitch.getState();
        if(LeftSide){
            telemetry.addData("Team Side", "Left");
        }else{
            telemetry.addData("Team Side", "Right");
        }
        telemetry.update();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AdWgUmr/////AAAAmdti1qKm9EedjDMUSL69hX+HJOhoZN/4cP0fCM8xi1/R0wjPyqh42r8h3X/xU4ONO+d0z+Of5xBeLnjXGG/UAmVWo7idgTEklCIYYQbNXGu4RWYoh2jsg9tpMSBvbhaTVkDJizAQa+XeSYDVb5N3S41xBjCiMB0zj8ECwGBp7cEHxZSjfTbm++fm3rBPMy0zMKd1vj1Gy7plv1YqDhPJYqXGK1sEOyMaUgtvbtf9lzKfz6pGv7ky3iD6U1QcPOHJ2FFEZhVrYynupvTy3T+kYVdKjuiv6eNuBTFZZAlnsq5pTUgyoZDfn7c4kKsEbbF0kmXwzpHWhhPSvcPRrAY2Z4UJz31/fJ0P835eibOQFjeU";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        waitForStart();
        //Lower the jewel arm -- necessary regardless of color
        Robot.jewelArm.jewelArmDown();
        //Wait for two seconds so that it has time to lower
        Sleep(2000);
        boolean rightBlue = Robot.color.isBlue(Robot.color.rightColor); //right color sensor is blue
        boolean rightRed = Robot.color.isRed(Robot.color.rightColor); //right color sensor is red
        boolean leftBlue = Robot.color.isBlue(Robot.color.leftColor); //left color sensor is blue
        boolean leftRed = Robot.color.isRed(Robot.color.leftColor); //left color sensor is red

        // this is backwards
        Robot.glyphArm.release();
        Sleep(1000);
        Robot.glyphArm.midPosition(this);
        if (!BlueTeam) {
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
                //Robot.holoDrive.turnleftunlim(0.2);
                Robot.holoDrive.panleft(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panright(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
            } else if (leftRed && !rightRed) {
                // Left red and right not specified (but not also red). Since we are red, we want to turn right.
                telemetry.addData("Color sensor", "Left red, right blue or unspecified");
                //Robot.holoDrive.turnrightunlim(0.2);
                Robot.holoDrive.panright(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panleft(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
            }else if(!leftBlue && rightBlue){
                //Right blue and left not specified (but not also blue). Since we are red, we want to turn right.
                telemetry.addData("Color sensor", "Left red or unspecified, right blue");
                //Robot.holoDrive.turnrightunlim(0.2);
                Robot.holoDrive.panleft(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panright(Speed);
                sleep(500);
                Robot.holoDrive.stopmotors();
            }else if (!leftRed && rightRed){
                //Right red and left not specified (but not also red). Since we are red, we want to turn left.
                telemetry.addData("Color sensor", "Left blue or unspecified, right red");
                //Robot.holoDrive.turnleftunlim(0.2);
                Robot.holoDrive.panleft(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panright(Speed);
                sleep(500);
                Robot.holoDrive.stopmotors();
            }else {
                //No reading? Ambiguous reading? Either way, something is wrong and we don't want to risk turning.
                telemetry.addData("Color sensor", "No reading");
                Robot.jewelArm.jewelArmUp();
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
                //Robot.holoDrive.turnrightunlim(0.2);
                Robot.holoDrive.panright(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panleft(Speed);
                sleep(500);
                Robot.holoDrive.stopmotors();
            } else if (leftRed && !rightRed){
                // Left red and right unspecified (but not also red). Since we are blue, we want to turn left.
                telemetry.addData ("Color sensor", "Left red, right blue or unspecified");
                //Robot.holoDrive.turnleftunlim(0.2);
                Robot.holoDrive.panleft(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panright(Speed);
                sleep(500);
                Robot.holoDrive.stopmotors();
            } else if (!leftBlue && rightBlue){
                //Right blue and left unspecified (but not also blue). Since we are blue, we want to turn left.
                telemetry.addData ("Color sensor", "Left red or unspecified, right blue");
                //Robot.holoDrive.turnleftunlim(0.2);
                Robot.holoDrive.panleft(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panright(Speed);
                sleep(500);
                Robot.holoDrive.stopmotors();
            }else if (!leftRed && rightRed){
                //Right red and left unspecified (but not also red). Since we are blue, we want to turn right.
                telemetry.addData ("Color sensor", "Left blue or unspecified, right red");
                //Robot.holoDrive.turnrightunlim(0.2);
                Robot.holoDrive.panright(Speed);
                Sleep(500);
                Robot.holoDrive.stopmotors();
                Robot.jewelArm.jewelArmUp();
                Robot.holoDrive.panleft(Speed);
                sleep(500);
                Robot.holoDrive.stopmotors();
            }else {
                //No reading? Ambiguous reading? Either way, something is wrong and we don't want to risk turning.
                telemetry.addData("Color sensor", "No reading");
                Robot.jewelArm.jewelArmUp();
                //Sleep(30000);
            }
        }
        telemetry.update();
        //sleep(30000); //Don't do anything else until autonomous period ends
        //double Start = ((Robot.holoDrive.NW.getCurrentPosition() + Robot.holoDrive.NE.getCurrentPosition() + Robot.holoDrive.SW.getCurrentPosition()+Robot.holoDrive.SE.getCurrentPosition())/4.0);
        double Start = Robot.holoDrive.NW.getCurrentPosition();
        double FasterSpeed = 0.2;
        // these are crude estimates for parking and putting the glyph in one of the boxes
        // all positions NEED to be tested
        if (LeftSide && BlueTeam){
            // havent tested this yet
            Robot.holoDrive.pan(11*Math.PI/8, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 600) {
                idle();
            }
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.turnleftunlim(FasterSpeed);
            while (Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 1400) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.pan(3*Math.PI/8, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 500) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Robot.glyphArm.zeroPosition(this);
            Sleep(1000);
            Robot.glyphArm.grab(); //opposite, this releases
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.pan(Math.PI/4, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 400) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Robot.holoDrive.pan(5*Math.PI/4, FasterSpeed);
            Sleep(500);
            Robot.holoDrive.stopmotors();
        }
        else if (!LeftSide && BlueTeam) {
            // this us into the parking zone, this has been tested
            Robot.holoDrive.pan(5*Math.PI / 4, FasterSpeed);
            while (Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 1600) {
                idle();
            }
            //Robot.holoDrive.stopmotors();
            // this should turn us to the right 90 degrees, need to test this
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.turnrightunlim(FasterSpeed);
            while (Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 600) {
                idle();
            }
            // this drives forward and lowers the arm, hopefully putting the glyph in the box
            Robot.holoDrive.stopmotors();
            Robot.glyphArm.zeroPosition(this);
            Sleep(1000);
            Start = Robot.holoDrive.NW.getCurrentPosition();
            // not sure if its pi/8 or 5pi/4 because the robot is turning
            Robot.holoDrive.pan(Math.PI / 4, FasterSpeed);
            while (Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 500) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Robot.glyphArm.grab(); // this is the opposite
            // this backs up after the glyph is put down just in case the gylph arm gets stuck in the box
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.pan(5*Math.PI / 4, FasterSpeed);
            while (Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 200) {
                idle();
            }
            Robot.holoDrive.stopmotors();
        }
        else if (LeftSide && !BlueTeam){
            // this us into the parking zone, this has been tested
            Robot.holoDrive.pan(Math.PI/4, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 800) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            double time = System.currentTimeMillis();
            while (System.currentTimeMillis()<time + 5000){
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
               /* Found an instance of the template. In the actual game, you will probably
                * loop until this condition occurs, then move on to act accordingly depending
                * on which VuMark was visible. */
                    telemetry.addData("VuMark", "%s visible", vuMark);
               /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                * it is perhaps unlikely that you will actually need to act on this pose information, but
                * we illustrate it nevertheless, for completeness. */
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                    //final Telemetry.Item pose1 = telemetry.addData("Pose", format(pose));
               /* We further illustrate how to decompose the pose into useful rotational and
                * translational components */
                    if (pose != null) {
                        VectorF trans = pose.getTranslation();
                        Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                        // Extract the X, Y, and Z components of the offset of the target relative to the robot
                        double tX = trans.get(0);
                        double tY = trans.get(1);
                        double tZ = trans.get(2);
                        // Extract the rotational components of the target relative to the robot
                        double rX = rot.firstAngle;
                        double rY = rot.secondAngle;
                        double rZ = rot.thirdAngle;
                    }
                }
                else {
                    telemetry.addData("VuMark", "not visible");
                }
                telemetry.update();
            }
            Robot.holoDrive.pan(Math.PI/4, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 800) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            //Robot.holoDrive.stopmotors();
            // this should turn us to the right 90 degrees, need to test this
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.turnrightunlim(FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 500) {
                idle();
            }
            // this drives forward and lowers the arm, hopefully putting the glyph in the box
            Robot.holoDrive.stopmotors();
            Robot.glyphArm.zeroPosition(this);
            Sleep(1000);
            Start = Robot.holoDrive.NW.getCurrentPosition();
            // not sure if its pi/8 or 5pi/4 because the robot is turning
            Robot.holoDrive.pan(Math.PI/4, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 500) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Robot.glyphArm.grab(); // this is the opposite
            // this backs up after the glyph is put down just in case the gylph arm gets stuck in the box
            Start = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.pan(5*Math.PI/4, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 300) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            //Robot.holoDrive.pan(Math.PI/8, -Speed);
            //Sleep(100);
        }
        else if (!LeftSide && !BlueTeam){
            Robot.holoDrive.pan(Math.PI/8, FasterSpeed);
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 2000) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Robot.glyphArm.zeroPosition(this);
            Sleep(1000);
            Robot.glyphArm.grab(); // this releases, its backwards
            Robot.holoDrive.pan(Math.PI/8, FasterSpeed);
            Start = Robot.holoDrive.NW.getCurrentPosition();
            while(Math.abs(Robot.holoDrive.NW.getCurrentPosition() - Start) < 400) {
                idle();
            }
            Robot.holoDrive.stopmotors();
            Robot.holoDrive.pan(9*Math.PI/8, FasterSpeed);
            while (Math.abs(Robot.holoDrive.NW.getCurrentPosition()-Start) < 300){
                idle();
            }
            Robot.holoDrive.stopmotors();
            //Sleep(1000);
        }
    }
}
