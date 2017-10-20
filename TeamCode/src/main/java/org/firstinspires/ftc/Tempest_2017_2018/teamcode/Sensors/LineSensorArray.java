package org.firstinspires.ftc.ftc_app.TeamCode.src.main.java.org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;

/**
 * Created by Aaron on 1/7/2017.
 */
public class LineSensorArray {
    //Variables and Objects
    I2cDevice rawDev;
    public I2cAddr address;
    public I2cDeviceSynchImpl reader;
    final int memAddr = 0x11; //This is the address for the single byte of output over the I2C channel


    //Constructor
    public LineSensorArray(I2cDevice newLSA, I2cAddr newAddr){
        rawDev = newLSA;
        address = newAddr;
        reader = new I2cDeviceSynchImpl(rawDev, address, false);
        reader.engage();
    }


    //Sensor Output
    public int getByte(){
        return (int)((reader.read8(memAddr) ^ 0xff)&0xff);
    }

    public boolean getBit(int pos){
        int raw = getByte();

        return (raw>>pos)%2 == 1;
    }


    //Basic algorithms
    public double getDensity(){
        double output = 0;

        //byte sample = getByte
        for(int i = 0; i < 8; i++){
            if(getBit(i)) output++;
        }
        return output/8;
    }

    //For the center, left, and right, check which directions the indices go on the sensor
    public boolean isCentered(){
        return getBit(4) || getBit(3);
    }

    //These indicate that there is a line reading on the left or right side of the sensor when looking from above
    public boolean isRight(){
        return (!isCentered()) && (getBit(0) || getBit(1) || getBit(2));
    }

    public boolean isLeft(){
        return (!isCentered()) && (getBit(5) || getBit(6) || getBit(7));
    }

    public boolean isBlank(){
        boolean isBlank = true;

        for(int i = 0; i < 8; i++){
            if(getBit(i)){
                isBlank = false;
            }
        }

        return isBlank;
    }


    //Setters and Getters

    //I might delete setAddress since there is no reason to change it while the object is being used
    public void setAddress(I2cAddr newAddr){
        address = newAddr;
    }

    public I2cAddr getAddress(){
        return address;
    }

    public I2cDevice getI2cDevice(){
        return rawDev;
    }

}

