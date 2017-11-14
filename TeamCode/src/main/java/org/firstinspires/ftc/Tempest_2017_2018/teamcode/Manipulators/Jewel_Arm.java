package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Molly on 10/7/2017.
 */

public class Jewel_Arm {
    Servo jewelArm;
    double up = 0;
    double down = 0.62;
    //These values can be changed later but they seem plausible

    HardwareMap HWMap;

    public Jewel_Arm(){}

    public void init(HardwareMap newHWMap){
        HWMap = newHWMap;
        jewelArm = HWMap.servo.get("jewelArm");
    }

    public void jewelArmPosition(double pos){
        jewelArm.setPosition(pos);
    }
    public void jewelArmUp(){
        jewelArmPosition(up);
    }
    public void jewelArmDown(){
        jewelArmPosition(down);
    }
}