package org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Jewel_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.ColorSensorClass;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.GyroScope;


/**
 * Created by Aaron on 4/25/2017.
 */
public class    HolonomicDrive {
    DcMotor.RunMode encMode = DcMotor.RunMode.RUN_USING_ENCODER;
    DcMotor.RunMode dumbMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
    //Define the four drive motors (labelled on robot)
    public DcMotor NW;
    public DcMotor NE;
    public DcMotor SW;
    public DcMotor SE;
    //Switch for red/blue mode. True means red mode (unlike last year)
    //Gyro sensor does not actually exist
    //public GyroScope gyro;
    //Was used to set max speed before that stopped being allowed
    int speed = 140*4;

    HardwareMap HWMap;

    public HolonomicDrive(){}

    public void init(HardwareMap newHWMap){
        HWMap = newHWMap;

        NW = HWMap.dcMotor.get("NW");
        NW.setMode(dumbMode);
        NW.setDirection(DcMotor.Direction.FORWARD);
        NW.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //NW.setMaxSpeed(speed);

        NE = HWMap.dcMotor.get("NE");
        NE.setMode(dumbMode);
        NE.setDirection(DcMotor.Direction.REVERSE);
        NE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //NE.setMaxSpeed(speed);

        SW = HWMap.dcMotor.get("SW");
        SW.setMode(dumbMode);
        SW.setDirection(DcMotor.Direction.FORWARD);
        SW.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //SW.setMaxSpeed(speed);

        SE = HWMap.dcMotor.get("SE");
        SE.setMode(dumbMode);
        SE.setDirection(DcMotor.Direction.REVERSE);
        SE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //SE.setMaxSpeed(speed);

        //gyro = new GyroScope();
        //gyro.init(HWMap);
    }

    public void pan(double theta, double power){
        //This is the pan function. If it is given an angle, it will move the robot at that angle.
        if (power <= 1 && power >= -1){
            NW.setPower(-power*Math.cos(theta));
            SE.setPower(-power*Math.cos(theta));
            NE.setPower(-power*Math.sin(theta));
            SW.setPower(-power*Math.sin(theta));
        }else if (power>1){
            //If it is given a power outside of the allowable range, it will adjust to be within an allowable range
            NW.setPower(-1*Math.cos(theta));
            SE.setPower(-1*Math.cos(theta));
            NE.setPower(-1*Math.sin(theta));
            SW.setPower(-1*Math.sin(theta));
        }else{
            NW.setPower(1*Math.cos(theta));
            SE.setPower(1*Math.cos(theta));
            NE.setPower(1*Math.sin(theta));
            SW.setPower(1*Math.sin(theta));
        }
    }

    public void stopmotors(){
        //Stops the drive motors
        NW.setPower(0);
        SE.setPower(0);
        NE.setPower(0);
        SW.setPower(0);
    }
    //The following code is reserved for future use, if we decide we want the gyro sensor. It works as of last summer.
   public void turnleft(int turnangle, double turnspeedleft, LinearOpMode master) throws InterruptedException {
       NE.setPower(turnspeedleft);
       SE.setPower(turnspeedleft);
       NW.setPower(-turnspeedleft);
       SW.setPower(-turnspeedleft);
       /*
       while(gyro.robotHeading() > 360 - turnangle || gyro.robotHeading() < 100){
           master.telemetry.addData("Robot Heading:", gyro.robotHeading());
           master.telemetry.update();
           master.idle();
       }
       */
       stopmotors();
   }

   public void turnright(int turnangle, double turnspeedright, LinearOpMode master) throws InterruptedException {
       NE.setPower(-turnspeedright);
       SE.setPower(-turnspeedright);
       NW.setPower(turnspeedright);
       SW.setPower(turnspeedright);
       /*
       while(gyro.robotHeading() < turnangle || gyro.robotHeading() > 300) master.idle();
       stopmotors();
   */
   }

    public void turnleftunlim(double turnspeedleft) {
        //Turns left (until it is told to stop)
        NE.setPower(turnspeedleft);
        SE.setPower(turnspeedleft);
        NW.setPower(-turnspeedleft);
        SW.setPower(-turnspeedleft);
    }
    //We could technically define turning right as turning left in the negative direction, but this is simpler to remember
    public void turnrightunlim(double turnspeedright) {
        //Turns right (until it is told to stop)
        NE.setPower(-turnspeedright);
        SE.setPower(-turnspeedright);
        NW.setPower(turnspeedright);
        SW.setPower(turnspeedright);
    }
    // builders wanted to have the robot pan instead of turning
    public void panright(double turnspeedright) {
        //Turns right (until it is told to stop)
        NE.setPower(turnspeedright);
        SE.setPower(turnspeedright);
        NW.setPower(turnspeedright);
        SW.setPower(turnspeedright);
    }
    public void panleft(double turnspeedright) {
        //Turns right (until it is told to stop)
        NE.setPower(-turnspeedright);
        SE.setPower(-turnspeedright);
        NW.setPower(-turnspeedright);
        SW.setPower(-turnspeedright);
    }
}


