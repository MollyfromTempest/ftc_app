
package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import android.util.Log;

import com.kauailabs.navx.ftc.AHRS;
import com.kauailabs.navx.ftc.navXPIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.DecimalFormat;

/*
 * An example linear op mode where the robot will rotate
 * to a specified angle an then stop.
 *
 * This example uses a simple PID controller configuration
 * with a P coefficient, and will likely need tuning in order
 * to achieve optimal performance.
 *
 * Note that for the best accuracy, a reasonably high update rate
 * for the navX-Model sensor should be used.
 */
public class NavX {
    public NavX(){}
    public AHRS navx_device;
    public void init(HardwareMap HWMap) {
        //navx_device = AHRS.getInstance(HWMap.deviceInterfaceModule.get("Device Interface Module 1"), 2, AHRS.DeviceDataType.kProcessedData);

        //while (navx_device.isCalibrating()) ;
        resetRotation();
    }
    public void resetRotation(){navx_device.zeroYaw();}
    public double getRotation() {
        return navx_device.getYaw();
    }
}
