package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators;

/**
 * Created by Molly on 10/7/2017.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Glyph_Arm

{
    DcMotor.RunMode encMode = DcMotor.RunMode.RUN_USING_ENCODER;
    public DcMotor liftArm;
    public Servo grabArmLeft;
    public Servo grabArmRight;

    int speed = 140*4;
    double liftPower = 0.4;
    double lowerPower = -0.4;
    double grabPower = 0.2;

    public int LiftZeroPosition;
    public int LiftMidPosition = 850;
    public int LiftTopPosition = 1500;
    public int Increment = 150;
    public int WiggleRoom = 20;
    long TimeOut = 3000;

    HardwareMap HWMap;

    public Glyph_Arm(){}

    public void init(HardwareMap newHWMap)
    {
        HWMap = newHWMap;

        //We can reverse the motor directions if we need to in order to get the motor to run in the correct direction
        liftArm = HWMap.dcMotor.get("liftArm");
        liftArm.setMode(encMode);
        liftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftArm.setDirection(DcMotorSimple.Direction.REVERSE);
        LiftZeroPosition = liftArm.getCurrentPosition();
        LiftMidPosition = LiftMidPosition + LiftZeroPosition;
        LiftTopPosition = LiftTopPosition + LiftZeroPosition;
        //So positive power is up
        //liftArm.setMaxSpeed(speed);

        grabArmLeft = HWMap.servo.get("grabArmLeft");
        grabArmRight = HWMap.servo.get("grabArmRight");
        release();
        //grabArm.setMaxSpeed(speed);
    }

    public void lift()
    {
        liftArm.setPower(liftPower);
    }
    public void lower()
    {
        liftArm.setPower(lowerPower);
    }
    public void stopLifting()
    {
        liftArm.setPower(0);
    }

    public void zeroPosition (LinearOpMode master) {
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(LiftZeroPosition);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }

        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy() && (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }

    public void midPosition (LinearOpMode master) {
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(LiftMidPosition);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy()&& (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }

    public void topPosition (LinearOpMode master) {
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(LiftTopPosition);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy()&& (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }

    public void incrementPosition (LinearOpMode master) {
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(currentPosition + Increment);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy()&& (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }
    public void grab()
    {
        grabArmLeft.setPosition(0.488);
        grabArmRight.setPosition(0.371);
    }
    public void release()
    {
        grabArmLeft.setPosition(0.82);
        grabArmRight.setPosition(0.059);
    }
    public void holdGrabPosition()
    {
        if (grabArmLeft.getPosition()>0 && grabArmLeft.getPosition()<1){grabArmLeft.setPosition(grabArmLeft.getPosition());}
        if (grabArmRight.getPosition()>0 && grabArmRight.getPosition()<1){grabArmRight.setPosition(grabArmRight.getPosition());}
    }
}


