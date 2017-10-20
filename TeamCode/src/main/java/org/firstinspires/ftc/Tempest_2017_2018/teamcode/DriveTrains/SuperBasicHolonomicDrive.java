package org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Molly on 9/30/2017.
 */

public class SuperBasicHolonomicDrive {
    /*
    This is a class that I created to help the build team test their robot.
    It allows the robot to pan, and nothing else.
    Use if you have no sensors attached and you have no desire for more complicated functionality.
    This class forms the basis for BasicTeleop.
     */
    int speed = 140*4;

    HardwareMap HWMap;

    public SuperBasicHolonomicDrive(){}
    DcMotor.RunMode encMode = DcMotor.RunMode.RUN_USING_ENCODER;

    public DcMotor NW;
    public DcMotor NE;
    public DcMotor SW;
    public DcMotor SE;

    public void init(HardwareMap newHWMap) {
        HWMap = newHWMap;

        NW = HWMap.dcMotor.get("NW");
        NW.setMode(encMode);
        NW.setDirection(DcMotor.Direction.REVERSE);
        NW.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        NW.setMaxSpeed(speed);

        NE = HWMap.dcMotor.get("NE");
        NE.setMode(encMode);
        NE.setDirection(DcMotor.Direction.FORWARD);
        NE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        NE.setMaxSpeed(speed);

        SW = HWMap.dcMotor.get("SW");
        SW.setMode(encMode);
        SW.setDirection(DcMotor.Direction.REVERSE);
        SW.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SW.setMaxSpeed(speed);

        SE = HWMap.dcMotor.get("SE");
        SE.setMode(encMode);
        SE.setDirection(DcMotor.Direction.FORWARD);
        SE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        SE.setMaxSpeed(speed);
    }
    public void pan(double theta, double power){
        if (power <= 1 && power >= -1){
            NW.setPower(power*Math.sin(theta));
            SE.setPower(power*Math.sin(theta));
            NE.setPower(power*Math.cos(theta));
            SW.setPower(power*Math.cos(theta));
        }else if (power>1){
            NW.setPower(1*Math.sin(theta));
            SE.setPower(1*Math.sin(theta));
            NE.setPower(1*Math.cos(theta));
            SW.setPower(1*Math.cos(theta));
        }else{
            NW.setPower(-1*Math.sin(theta));
            SE.setPower(-1*Math.sin(theta));
            NE.setPower(-1*Math.cos(theta));
            SW.setPower(-1*Math.cos(theta));
        }
    }

    public void stopmotors(){
        NW.setPower(0);
        SE.setPower(0);
        NE.setPower(0);
        SW.setPower(0);
    }

}
