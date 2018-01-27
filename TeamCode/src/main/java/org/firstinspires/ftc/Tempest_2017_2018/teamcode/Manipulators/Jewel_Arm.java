package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Molly on 10/7/2017.
 */

public class Jewel_Arm {
    //This defines the arm for hitting the jewels in autonomous mode
    Servo jewelArm;
    double up = 0.54; // Value for when the arm IS NOT in hitting position (starts here in autonomous)
    double down = 0.0; //Value for when the arm IS in hitting position (moves here when autonomous starts)

    HardwareMap HWMap;

    public Jewel_Arm(){}

    public void init(HardwareMap newHWMap){
        HWMap = newHWMap;
        jewelArm = HWMap.servo.get("jewelArm");
        jewelArmUp();
    }

    public void jewelArmPosition(double pos){
        //This allows for greater flexibility if we decide we need more positions
        jewelArm.setPosition(pos);
    }
    public void jewelArmUp(){
        //Moves the arm up
        jewelArmPosition(up);
    }
    public void jewelArmDown(){
        //Moves the arm down
        jewelArmPosition(down);
    }
}

