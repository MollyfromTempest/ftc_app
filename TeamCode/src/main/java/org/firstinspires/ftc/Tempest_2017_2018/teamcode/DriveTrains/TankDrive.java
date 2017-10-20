package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains;

import com.qualcomm.robotcore.hardware.DcMotor;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.GyroScope;

/**
 * Created by Aaron on 5/20/2017.
 */
public class TankDrive {
    private DcMotor backRight;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor frontLeft;
    DcMotor.RunMode encMode = DcMotor.RunMode.RUN_USING_ENCODER;

    private GyroScope gyro;

    public HardwareMap HWMap;
    int speed = 140*4;

    int backRightEnc;
    int frontRightEnc;
    int backLeftEnc;
    int frontLeftEnc;

    public TankDrive(){}

    void init(HardwareMap HWMap){
        this.HWMap = HWMap;

        backLeft = this.HWMap.dcMotor.get("backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setMode(encMode);
        //backLeft.setMaxSpeed(speed);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft = this.HWMap.dcMotor.get("frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setMode(encMode);
        //frontLeft.setMaxSpeed(speed);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight = this.HWMap.dcMotor.get("backRight");
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setMode(encMode);
        //backRight.setMaxSpeed(speed);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight = this.HWMap.dcMotor.get("frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setMode(encMode);
        //frontRight.setMaxSpeed(speed);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gyro = new GyroScope();
        gyro.init(this.HWMap);

        backRightEnc = backRight.getCurrentPosition();
        frontRightEnc = frontRight.getCurrentPosition();
        backLeftEnc = backLeft.getCurrentPosition();
        frontLeftEnc = frontLeft.getCurrentPosition();
    }

    void straight(double pow){
        if(pow > 1) pow = 1;
        if(pow < -1) pow = -1;

        backLeft.setPower(pow);
        frontLeft.setPower(pow);
        backRight.setPower(pow);
        frontRight.setPower(pow);
    }

    void straight(double pow, int ticks, boolean alignment, LinearOpMode master) throws InterruptedException{
        if(pow > 1) pow = 1;
        if(pow < -1) pow = -1;

        resetEncoders();
        int start = encoderAvg();

        if(alignment){
            gyro.resetRobotHeading();
            straight(pow);

            while(ticks > encoderAvg() - start){
                if(gyro.robotHeading() > 180 && gyro.robotHeading() < 357){
                    right(pow, 360-gyro.robotHeading(), master);
                    straight(pow);
                }else if(gyro.robotHeading() < 180 && gyro.robotHeading() > 3){
                    left(pow, gyro.robotHeading(), master);
                    straight(pow);
                }

                master.idle();
            }
        }else{
            straight(pow);
            while(ticks > encoderAvg() - start){master.idle();}
        }
    }

    public int encoderAvg(){
        return (backRight.getCurrentPosition()-backRightEnc + backLeft.getCurrentPosition()-backLeftEnc + frontRight.getCurrentPosition()-frontRightEnc + frontLeft.getCurrentPosition()-frontLeftEnc)/4;
    }

    public void resetEncoders(){
        backRightEnc = backRight.getCurrentPosition();
        frontRightEnc = frontRight.getCurrentPosition();
        backLeftEnc = backLeft.getCurrentPosition();
        frontLeftEnc = frontLeft.getCurrentPosition();
    }

    void left(double pow, int deg, LinearOpMode master) throws InterruptedException{
        deg = Math.abs(deg);

        if(pow > 1) pow = 1;
        if(pow < -1) pow = -1;

        gyro.resetRobotHeading();

        frontRight.setPower(pow);
        backRight.setPower(pow);
        frontLeft.setPower(-pow);
        frontLeft.setPower(-pow);

        while(Math.abs(gyro.robotHeading()) < deg) master.idle();

        stopmotors();
    }

    void right(double pow, int deg, LinearOpMode master) throws InterruptedException{
        deg = Math.abs(deg);

        if(pow > 1) pow = 1;
        if(pow < -1) pow = -1;

        gyro.resetRobotHeading();

        frontRight.setPower(-pow);
        backRight.setPower(-pow);
        frontLeft.setPower(pow);
        frontLeft.setPower(pow);

        while(Math.abs(gyro.robotHeading()) < deg) master.idle();

        stopmotors();
    }

    void stopmotors(){
        backRight.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);
    }


    DcMotor getBackRight(){return backRight;}
    DcMotor getFrontRight(){return frontRight;}
    DcMotor getBackLeft(){return backLeft;}
    DcMotor getFrontLeft(){return frontLeft;}

    GyroScope getGyro(){return gyro;}
}
