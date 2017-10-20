package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Aaron on 5/20/2017.
 */
public class GyroScope {
    GyroSensor gyro;
    int robotZeroHeading;

    HardwareMap HWMap;

    public GyroScope(){}

    public void init(HardwareMap HWMap){
        this.HWMap = HWMap;
        gyro = this.HWMap.gyroSensor.get("gyro");
    }

    public int robotHeading(){
        int out = gyro.getHeading() - robotZeroHeading;
        if(out < 0){
            return out + 360;
        }else{
            return out;
        }
    }

    public void resetRobotHeading(){
        robotZeroHeading = gyro.getHeading();
    }
}
