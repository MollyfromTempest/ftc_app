package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Aaron on 5/20/2017.
 */
public class Sweeper {
    DcMotor sweeper;
    Servo sweeperLift;

    HardwareMap HWMap;

    public Sweeper(){}

    public void init(HardwareMap newHWMap){
        HWMap = newHWMap;

        sweeper = HWMap.dcMotor.get("sweeper");
        sweeper.setDirection(DcMotorSimple.Direction.FORWARD);
        sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        sweeperLift = HWMap.servo.get("sweeper_lift");
    }

    public void intake(double pow){
        sweeper.setPower(pow);
    }

    public void outtake(double pow){
        sweeper.setPower(-pow);
    }

    public void stop(){
        sweeper.setPower(0);
    }

    public void move(double pos){
        sweeperLift.setPosition(pos);
    }

    DcMotor getSweeper(){return sweeper;}
    Servo getSweeperLift(){return sweeperLift;}
}
