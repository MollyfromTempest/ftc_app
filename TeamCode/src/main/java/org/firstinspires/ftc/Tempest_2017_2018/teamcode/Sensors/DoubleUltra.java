package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by AshQuinn on 1/17/17.
 */
public class DoubleUltra {
    UltrasonicSensor leftUltra;
    UltrasonicSensor rightUltra;

    //Constructor
    public DoubleUltra (UltrasonicSensor newLeft, UltrasonicSensor newRight){
        leftUltra = newLeft;
        rightUltra = newRight;
    }

    //Methods
    public double getAverage () {
        double output;

        double leftVal= leftUltra.getUltrasonicLevel();
        double rightVal = rightUltra.getUltrasonicLevel();

        if ((leftVal >= 150 || leftVal <=0.01) && (rightVal >= 0.01 && rightVal<= 150)) {
            output = rightVal;

        }else if ((leftVal <= 150 && leftVal >0.01) && (rightVal>= 150 && rightVal <= 0.01)){
            output = leftVal;
        }
        else if ((leftVal >= 150 || leftVal <=0.01)&&(rightVal>= 150 && rightVal <= 0.01)){
            output = -1;
        }
        else{
            output = (leftVal + rightVal)/2.0;
        }
        return output;
    }

    public double getDelta (){
        double output;
        double leftVal= leftUltra.getUltrasonicLevel();
        double rightVal = rightUltra.getUltrasonicLevel();

        if ((leftVal >= 150 || leftVal <=0.01)||(rightVal>= 150 && rightVal <= 0.01)){
            output =0;
        }else{
            output = leftVal - rightVal;
        }
        return output;
    }

    //Setters and Getters
    public UltrasonicSensor getLeftUltra () {
        return leftUltra;
    }
    public void setLeftUltra (UltrasonicSensor newLeft) {
        leftUltra = newLeft;
    }

    public UltrasonicSensor getRightUltra () {
        return rightUltra;
    }
    public void setRightUltra (UltrasonicSensor newRight) {
        leftUltra = newRight;
    }
}

