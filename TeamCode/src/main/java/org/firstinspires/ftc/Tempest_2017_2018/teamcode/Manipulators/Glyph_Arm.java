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
    public DcMotor liftArm; //Arm to lift the blocks
    public Servo grabArmLeft; //Left side of grabbing mechanism
    public Servo grabArmRight; //Right side of grabbing mechanism

    int speed = 140*4; //Holdover from the setMaxSpeed era
    double liftPower = 0.4; //Power used to lift the blocks
    double lowerPower = -0.4; //Power used to lower the blocks. It has its own variable in case we decide it should be different

    public int LiftZeroPosition; //Starting position, on the ground
    public int LiftMidPosition = 850; //Slightly more than 6 inches (so blocks can be stacked)
    public int LiftTopPosition = 1500; //Slightly more than 12 inches (so blocks can be stacked)
    public int Increment = 150; //Approximately 1 inch
    public int WiggleRoom = 20; //Small number of encoder ticks
    long TimeOut = 3000; //Value for clock timeout on lifting/lowering

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
        LiftZeroPosition = liftArm.getCurrentPosition(); //Must start arm in bottom position
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
    } //raises the arm
    public void lower()
    {
        liftArm.setPower(lowerPower);
    } //lowers the arm
    public void stopLifting()
    {
        liftArm.setPower(0);
    } //stops the arm from moving

    public void zeroPosition (LinearOpMode master) {
        //Sends the arm to the bottom position
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(LiftZeroPosition);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        //Times out if it runs for more than 3 seconds. The same code exists for the other lifting functions.
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy() && (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }

    public void midPosition (LinearOpMode master) {
        //Sends the arm to the middle position
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(LiftMidPosition);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        //Times out if it runs for more than 3 seconds. The same code exists for the other lifting functions.
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy()&& (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }

    public void topPosition (LinearOpMode master) {
        //Sends the arm to the top position
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(LiftTopPosition);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        //Times out if it runs for more than 3 seconds. The same code exists for the other lifting functions.
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy()&& (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }

    public void incrementPosition (LinearOpMode master) {
        //Moves the arm up by the increment value to allow fine-tuning
        int currentPosition = liftArm.getCurrentPosition();

        liftArm.setTargetPosition(currentPosition + Increment);

        if (currentPosition > liftArm.getTargetPosition()) {
            lower();
        } else {
            lift();
        }
        //Times out if it runs for more than 3 seconds. The same code exists for the other lifting functions.
        long startTime = System.currentTimeMillis();

        while (liftArm.isBusy()&& (System.currentTimeMillis()- startTime < TimeOut)) {
            master.idle();
        }

        stopLifting();
    }
    public void grab()
    {
        //Grabs a block
        grabArmLeft.setPosition(0.488);
        grabArmRight.setPosition(0.371);
    }
    public void release()
    {
        //Releases the block
        grabArmLeft.setPosition(0.82);
        grabArmRight.setPosition(0.059);
    }
    public void sortOfRelease()
    {
        grabArmLeft.setPosition(0.6);
        grabArmRight.setPosition(0.25);
    }
    public void holdGrabPosition()
    {
        //Preserves the current location of the grabbers (currently unused)
        if (grabArmLeft.getPosition()>0 && grabArmLeft.getPosition()<1){grabArmLeft.setPosition(grabArmLeft.getPosition());}
        if (grabArmRight.getPosition()>0 && grabArmRight.getPosition()<1){grabArmRight.setPosition(grabArmRight.getPosition());}
    }
}