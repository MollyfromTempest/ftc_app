package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Molly on 9/30/2017.
 */

public class ColorSensorClass {
    public ColorSensor leftColor;
    public ColorSensor rightColor;



    HardwareMap HWMap;

    public ColorSensorClass(){}

    public void init(HardwareMap HWMap) {
        this.HWMap = HWMap;
        leftColor = this.HWMap.colorSensor.get("leftColor");
        rightColor = this.HWMap.colorSensor.get("rightColor");
        //We have to set I2C addresses because we have two color sensors:
        leftColor.setI2cAddress(new I2cAddr(0x3a/2));
        rightColor.setI2cAddress(new I2cAddr(0x3c/2));//Divide by 2 because expecting 7 bit version of address

    }
    public boolean isRed(ColorSensor sensor) {
        //If there is more red than blue, it is red.
        //We may add a buffer in the future.
        return (sensor.red() > sensor.blue());
    }

    public boolean isBlue(ColorSensor sensor) {
        //If there is more blue than red, it is blue.
        //We may add a buffer in the future.
        return (sensor.red() < sensor.blue());
    }
}

