package org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    public DcMotor NW;
    public DcMotor NE;
    public DcMotor SW;
    public DcMotor SE;

    public DigitalChannel BlueSwitch;

    public GyroScope gyro;
    public ColorSensorClass color;
    public Jewel_Arm jewelArm;
    public Glyph_Arm glyphArm;

    int speed = 140*4;

    HardwareMap HWMap;

    public HolonomicDrive(){}

    public void init(HardwareMap newHWMap){
        HWMap = newHWMap;

        NW = HWMap.dcMotor.get("NW");
        NW.setMode(encMode);
        NW.setDirection(DcMotor.Direction.REVERSE);
        NW.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //NW.setMaxSpeed(speed);

        NE = HWMap.dcMotor.get("NE");
        NE.setMode(encMode);
        NE.setDirection(DcMotor.Direction.FORWARD);
        NE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //NE.setMaxSpeed(speed);

        SW = HWMap.dcMotor.get("SW");
        SW.setMode(encMode);
        SW.setDirection(DcMotor.Direction.REVERSE);
        SW.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //SW.setMaxSpeed(speed);

        SE = HWMap.dcMotor.get("SE");
        SE.setMode(encMode);
        SE.setDirection(DcMotor.Direction.FORWARD);
        SE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //SE.setMaxSpeed(speed);

        gyro = new GyroScope();
        gyro.init(HWMap);

        color = new ColorSensorClass();
        color.init(HWMap);

        jewelArm = new Jewel_Arm();
        jewelArm.init(HWMap);

        glyphArm = new Glyph_Arm();
        glyphArm.init(HWMap);

        BlueSwitch = HWMap.digitalChannel.get("LEDBlueSwitch");
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

    public void turnleft(int turnangle, double turnspeedleft, LinearOpMode master) throws InterruptedException {
        gyro.resetRobotHeading();
        NE.setPower(turnspeedleft);
        SE.setPower(turnspeedleft);
        NW.setPower(-turnspeedleft);
        SW.setPower(-turnspeedleft);
        while(gyro.robotHeading() > 360 - turnangle || gyro.robotHeading() < 100){
            master.telemetry.addData("Robot Heading:", gyro.robotHeading());
            master.telemetry.update();
            master.idle();
        }
        stopmotors();
    }

    public void turnright(int turnangle, double turnspeedright, LinearOpMode master) throws InterruptedException {
        gyro.resetRobotHeading();
        NE.setPower(-turnspeedright);
        SE.setPower(-turnspeedright);
        NW.setPower(turnspeedright);
        SW.setPower(turnspeedright);
        while(gyro.robotHeading() < turnangle || gyro.robotHeading() > 300) master.idle();
        stopmotors();
    }
    public void turnleftunlim(double turnspeedleft, LinearOpMode master) throws InterruptedException {
        NE.setPower(turnspeedleft);
        SE.setPower(turnspeedleft);
        NW.setPower(-turnspeedleft);
        SW.setPower(-turnspeedleft);
    }

    public void turnrightunlim(double turnspeedright, LinearOpMode master) throws InterruptedException {
        NE.setPower(-turnspeedright);
        SE.setPower(-turnspeedright);
        NW.setPower(turnspeedright);
        SW.setPower(turnspeedright);
    }
}
