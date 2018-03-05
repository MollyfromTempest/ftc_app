package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Programs;

import android.drm.DrmStore;

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

import android.support.annotation.Nullable;

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
    //VuMarkInstanceIdWrite instanceId;
    long value;
    double ttime;
    boolean leftturn;

    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            idle();
        }
    }

    /**
     *
     * @param angle1 vector angle for initial movement
     * @param dist1 distance for initial movement
     * @param turndeg angle of degrees for putting the glyph in the correct spot
     * @param angle2 vector angle for driving(should always be forward)
     * @param dist2 distance for driving into the box
     */
    public void panturnpan(double angle1, double dist1, double turndeg, double angle2, double dist2) {
        double currdist = Robot.holoDrive.NW.getCurrentPosition();
        Robot.holoDrive.pan(angle1, 0.5);
        while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= dist1) {
            idle();
        }
        Robot.holoDrive.stopmotors();
        if (leftturn) {
            currdist = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.turnleftunlim(0.3);
            while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= turndeg * 200 / 36) {
                idle();
            }
        } else if (!leftturn) {
            currdist = Robot.holoDrive.NW.getCurrentPosition();
            Robot.holoDrive.turnrightunlim(0.3);
            while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= turndeg * 200 / 36) {
                idle();
            }
        }
        Robot.holoDrive.stopmotors();
        currdist = Robot.holoDrive.NW.getCurrentPosition();
        Robot.holoDrive.pan(angle2, 0.5);
        while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= dist2) {
            idle();
        }
        Robot.holoDrive.stopmotors();
    }

    // Enum example (from Oracle):
    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Robot = new Robot2017_2018();
        Robot.init(hardwareMap);
        //Turn on the color sensor LEDS
        Robot.color.leftColor.enableLed(true);
        Robot.color.rightColor.enableLed(true);
        double Speed = 0.1;
        boolean BlueTeam = !Robot.BlueSwitch.getState();
        if (BlueTeam) {
            telemetry.addData("Team Color", "Blue");
        } else {
            telemetry.addData("Team Color", "Red");
        }
        boolean LeftSide = Robot.LeftRightSwitch.getState();
        if (LeftSide) {
            telemetry.addData("Team Side", "Left");
        } else {
            telemetry.addData("Team Side", "Right");
        }
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AdWgUmr/////AAAAmdti1qKm9EedjDMUSL69hX+HJOhoZN/4cP0fCM8xi1/R0wjPyqh42r8h3X/xU4ONO+d0z+Of5xBeLnjXGG/UAmVWo7idgTEklCIYYQbNXGu4RWYoh2jsg9tpMSBvbhaTVkDJizAQa+XeSYDVb5N3S41xBjCiMB0zj8ECwGBp7cEHxZSjfTbm++fm3rBPMy0zMKd1vj1Gy7plv1YqDhPJYqXGK1sEOyMaUgtvbtf9lzKfz6pGv7ky3iD6U1QcPOHJ2FFEZhVrYynupvTy3T+kYVdKjuiv6eNuBTFZZAlnsq5pTUgyoZDfn7c4kKsEbbF0kmXwzpHWhhPSvcPRrAY2Z4UJz31/fJ0P835eibOQFjeU";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        /**
         * There is a lot of code dedicated for setting up the Vuforia.
         * If you ever make another program using Vuforia, you will reuse this code.
         * How could you embed this into the Robot class?
         * You already have a Robot.init() function,
         * isn't Vuforia part of initialization?
         *
         * -- Aaron
         */

        waitForStart();
        relicTrackables.activate();

        /**
         * These three booleans (Left/Right/CenterMark) are mutually exclusive.
         * Could you use an int? or an enum? and merge those three variables into one?
         * Refer to the Day enum example I placed above this function.
         *
         * Ah, looks like you've already noticed this based off of later comments :)
         *
         * -- Aaron
         */


        boolean LeftMark = false;
        boolean RightMark = false;
        boolean CenterMark = false;
        boolean getout = false;
        boolean Unknown = true;

        /**
         * If there is a known issue in the code, add a TODO comment
         *
         * -- Aaron
         */

        // this is backwards
        Robot.glyphArm.release();
        Sleep(1000);
        Robot.glyphArm.midPosition(this);
        ttime = System.currentTimeMillis();
        //Vuforia code
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        while ((System.currentTimeMillis() < ttime + 10000) && !getout) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
               /* Found an instance of the template. In the actual game, you will probably
                * loop until this condition occurs, then move on to act accordingly depending
                * onMark", which VuMark was visible. */
                //telemetry.addData("Vu "%s visible", vuMark);
               /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                * it is perhaps unlikely that you will actually need to act on this pose information, but
                * we illustrate it nevertheless, for completeness. */

                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    LeftMark = true;
                    Unknown = false;
                    //telemetry.addData("LeftMark", vuMark);
                    //telemetry.update();
                    getout = true;
                } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    RightMark = true;
                    Unknown = false;
                    //telemetry.addData("RightMark", vuMark);
                    //telemetry.update();
                    getout = true;
                } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    CenterMark = true;
                    Unknown = false;
                    //telemetry.addData("CenterMark", vuMark);
                    //telemetry.update();
                    getout = true;
                }
            } else {
                //telemetry.addData("VuMark", "not visible");
                //telemetry.update();
            }

        }

        /**
         * Telemetry is good!
         * _thumbs up_
         *
         * -- Aaron
         */

        if (LeftMark) {
            telemetry.addData("LeftMark", vuMark);
            telemetry.update();
        } else if (RightMark) {
            telemetry.addData("RightMark", vuMark);
            telemetry.update();
        } else if (CenterMark) {
            telemetry.addData("CenterMark", vuMark);
            telemetry.update();
        } else if (Unknown) {
            telemetry.addData("Unknown", vuMark);
            telemetry.update();
        } else {
            telemetry.addData("ThisMakesNoSense", vuMark);
            telemetry.update();
        }
        /*String format(OpenGLMatrix transformationMatrix){
            return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
        }*/
        //Lower the jewel arm -- necessary regardless of color
        Robot.jewelArm.jewelArmDown();
        //Wait for two seconds so that it has time to lower
        Sleep(2000);
        boolean rightBlue = Robot.color.isBlue(Robot.color.rightColor); //right color sensor is blue
        boolean rightRed = Robot.color.isRed(Robot.color.rightColor); //right color sensor is red
        boolean leftBlue = Robot.color.isBlue(Robot.color.leftColor); //left color sensor is blue
        boolean leftRed = Robot.color.isRed(Robot.color.leftColor); //left color sensor is red


        if (!BlueTeam) {
            //RED RED RED
            telemetry.addData("Right blue", rightBlue);
            telemetry.addData("Right red", rightRed);
            telemetry.addData("Left blue", leftBlue);
            telemetry.addData("Left red", leftRed);


            if (leftBlue && rightBlue) {
                //Both blue -- something is wrong!
                telemetry.addData("Color sensor", "Both blue");
                Robot.jewelArm.jewelArmUp();
                //Sleep(30000);
            } else if (leftRed && rightRed) {
                //Both red -- something is wrong!
                telemetry.addData("Color sensor", "Both red");
                Robot.jewelArm.jewelArmUp();
                //Sleep(30000);
            } else if (leftBlue && !rightBlue) {
                // Left blue and right not specified (but not also blue). Since we are red, we want to turn left.
                telemetry.addData("Color sensor", "Left blue, right red or unspecified");
                knockLeft(Speed);
            } else if (leftRed && !rightRed) {
                // Left red and right not specified (but not also red). Since we are red, we want to turn right.
                telemetry.addData("Color sensor", "Left red, right blue or unspecified");
                knockRight(Speed);
            } else if (!leftBlue && rightBlue) {
                //Right blue and left not specified (but not also blue). Since we are red, we want to turn right.
                telemetry.addData("Color sensor", "Left red or unspecified, right blue");
                knockRight(Speed);
            } else if (!leftRed && rightRed) {
                //Right red and left not specified (but not also red). Since we are red, we want to turn left.
                telemetry.addData("Color sensor", "Left blue or unspecified, right red");
                knockLeft(Speed);
            } else {
                //No reading? Ambiguous reading? Either way, something is wrong and we don't want to risk turning.
                telemetry.addData("Color sensor", "No reading");
                Robot.jewelArm.jewelArmUp();
                //Sleep(30000);
            }
        } else {
            //BLUE BLUE BLUE
            telemetry.addData("Right blue", rightBlue);
            telemetry.addData("Right red", rightRed);
            telemetry.addData("Left blue", leftBlue);
            telemetry.addData("Left red", leftRed);


            /**
             * Some of your conditions have identical code in them.
             * I'm assuming it still works so it's not a big issue,
             * but rewriting code should (and can) be avoided
             *
             * -- Aaron
             */

            if (leftBlue && rightBlue) {
                //Both blue -- something is wrong!
                telemetry.addData("Color sensor", "Both blue");
                Robot.jewelArm.jewelArmUp();
                //Sleep(30000);
            } else if (leftRed && rightRed) {
                //Both red -- something is wrong!
                telemetry.addData("Color sensor", "Both red");
                Robot.jewelArm.jewelArmUp();
                //Sleep(30000);
            } else if (leftBlue && !rightBlue) {
                // Left blue and right not specified (but not also blue). Since we are blue, we want to turn right.
                telemetry.addData("Color sensor", "Left blue, right red or unspecified");
                knockRight(Speed);
            } else if (leftRed && !rightRed) {
                // Left red and right unspecified (but not also red). Since we are blue, we want to turn left.
                telemetry.addData("Color sensor", "Left red, right blue or unspecified");
                knockLeft(Speed);
            } else if (!leftBlue && rightBlue) {
                //Right blue and left unspecified (but not also blue). Since we are blue, we want to turn left.
                telemetry.addData("Color sensor", "Left red or unspecified, right blue");
                knockLeft(Speed);
            } else if (!leftRed && rightRed) {
                //Right red and left unspecified (but not also red). Since we are blue, we want to turn right.
                telemetry.addData("Color sensor", "Left blue or unspecified, right red");
                knockRight(Speed);
            } else {
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
        Robot.jewelArm.jewelArmUp(); // just in case


        /**
         * It looks like you are trying to handle every possible position individually
         * This will be very hard to test, especially with the limited time
         *
         * All of the crypto boxes are the same,
         * so placing a block could be handled by one routine for every position.
         * e.g:
         * Robot.PlaceBlock(LeftMark);
         * Robot.PlaceBlock(RightMark);
         * etc.
         *
         * The only differences between the positions is how you get to the crypto box.
         * How can you approach this issue so you can optimize your time for testing?
         *
         * -- Aaron
         */


        // these are crude estimates for parking and putting the glyph in one of the boxes
        // all positions NEED to be tested
        if (LeftSide && BlueTeam) {

            if (LeftMark) {

                /**
                 * You have the near identical while loop like 100 times
                 * What can you do to make this cleaner?
                 * Also, add line breaks for readability...
                 * -- Aaron
                 */
                leftturn = false;
                panturnpan(Math.PI * 17 / 12, 2730, 160, Math.PI / 4, 1080);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (CenterMark || Unknown) {
                leftturn = true;
                panturnpan(Math.PI * 17 / 12, 2730, 180, Math.PI / 4, 915);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (RightMark) {
                //Right
                leftturn = true;
                panturnpan(Math.PI * 17 / 12, 2730, 150, Math.PI / 4, 1080);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            }
        } else if (!LeftSide && BlueTeam) {
            if (LeftMark) {
                leftturn = false;
                panturnpan(5 * Math.PI / 4, 1500, 90, .32, 785);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (CenterMark || Unknown) {
                leftturn = false;
                panturnpan(5 * Math.PI / 4, 1500, 90, Math.PI / 4, 830);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (RightMark) {
                leftturn = false;
                panturnpan(5 * Math.PI / 4, 1500, 135, Math.PI / 4, 1080);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            }
        } else if (LeftSide && !BlueTeam) {
            if (LeftMark) {
                leftturn = false;
                panturnpan(Math.PI / 4, 1500, 90, Math.PI / 4, 1000);
                Robot.glyphArm.grab(); // releases glyph arm
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (CenterMark || Unknown) {
                leftturn = false;
                panturnpan(Math.PI / 5.14, 1500, 115, Math.PI / 4, 900);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (RightMark) {
                leftturn = false;
                panturnpan(Math.PI / 5.14, 1500, 140, Math.PI / 4, 1100);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            }
        } else if (!LeftSide && !BlueTeam) {
            //TODO currently runs into the corner
            if (LeftMark) {
                //LEFT
                leftturn = true;
                panturnpan(.61, 765, 18, Math.PI / 4, 1000);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (CenterMark || Unknown) {
                //Center
                leftturn = false;
                panturnpan(.54, 765, 0, Math.PI / 4, 900);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            } else if (RightMark) {
                //Right
                leftturn = false;
                panturnpan(.54, 765, 18, Math.PI / 4, 1000);
                Robot.glyphArm.grab();
                Sleep(500);
                Robot.holoDrive.pan(Math.PI * 5 / 4, 0.4);
                Sleep(750);
                Robot.holoDrive.stopmotors();
            }
        }
    }
    /**
     * Turns the robot enough to hit the left ball
     * @param turnspeed speed to turn left
     */
    public void knockLeft(double turnspeed){
        double currdist = Robot.holoDrive.NW.getCurrentPosition();
        while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= 100) {
            Robot.holoDrive.turnleftunlim(turnspeed);
        }
        Robot.holoDrive.stopmotors();
        Robot.jewelArm.jewelArmUp();
        currdist = Robot.holoDrive.NW.getCurrentPosition();
        while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= 100) {
            Robot.holoDrive.turnrightunlim(turnspeed);
        }
        Robot.holoDrive.stopmotors();

    }

    /**
     * Turns the robot enough to hit the right ball
     * @param turnspeed speed to turn
     */
    public void knockRight(double turnspeed){
        double currdist = Robot.holoDrive.NW.getCurrentPosition();
        while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= 100) {
            Robot.holoDrive.turnrightunlim(turnspeed);
        }
        Robot.holoDrive.stopmotors();
        Robot.jewelArm.jewelArmUp();
        currdist = Robot.holoDrive.NW.getCurrentPosition();
        while ((Math.abs(Robot.holoDrive.NW.getCurrentPosition() - currdist)) <= 100) {
            Robot.holoDrive.turnleftunlim(turnspeed);
        }
        Robot.holoDrive.stopmotors();
    }
}

/**
 * While it's good that you have identified a series of repeated lines,
 * it's best to try and remove a lot of the logic for core tasks from the program.
 *
 * Hitting the correct jewel based off of your color is a task that multiple programs could use,
 * so is identifying the picture and placing the block.
 *
 * These tasks can reside in the Robot class, imagine calling  robot.hitJewel() or
 * robot.placeBlock(position). Removing the giant if else statements and having class
 * functions in robot handle them makes the code much more portable and readable.
 *
 * -- Aaron
 */
