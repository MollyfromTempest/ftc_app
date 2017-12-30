package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

import com.kauailabs.navx.ftc.AHRS;
import com.kauailabs.navx.ftc.navXPIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Jewel_Arm;

/**
 * Created by Chris on 12/30/2017.
 */

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Jewel_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Robot2017_2018;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.ColorSensorClass;


public class NavX_Test {
    @TeleOp
    public class Teleop_6699 extends LinearOpMode {
        // holonomic drive object instance
        Robot2017_2018 Robot;

        // sleep fuction
        public void Sleep(long ticks) throws InterruptedException {
            long timer = System.currentTimeMillis();
            while (System.currentTimeMillis() - timer < ticks) {
                idle();
            }
        }

        //Testing GitHub push
        private final int NAVX_DIM_I2C_PORT = 0;
        private AHRS navx_device;
        private navXPIDController yawPIDController;
        private ElapsedTime runtime = new ElapsedTime();

        private final byte NAVX_DEVICE_UPDATE_RATE_HZ = 50;

        private final double TARGET_ANGLE_DEGREES = 90.0;
        private final double TOLERANCE_DEGREES = 2.0;
        private final double MIN_MOTOR_OUTPUT_VALUE = -1.0;
        private final double MAX_MOTOR_OUTPUT_VALUE = 1.0;
        private final double YAW_PID_P = 0.005;
        private final double YAW_PID_I = 0.0;
        private final double YAW_PID_D = 0.0;

        private boolean calibration_complete = false;
        @Override
        public void runOpMode() throws InterruptedException {
            Robot = new Robot2017_2018();
            Robot.init(hardwareMap);

            double theta;
            double power;
            double pivotpower;
            //Scaling factors for speed
            double driveScale = 1;
            double turnScale = 1;

            Robot.jewelArm.jewelArmUp();
            boolean JewelArmUp = true; //arm starts up after autonomous
            boolean open = true; //default is that the grabber moves open
            navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
                    NAVX_DIM_I2C_PORT,
                    AHRS.DeviceDataType.kProcessedData,
                    NAVX_DEVICE_UPDATE_RATE_HZ);


        /* If possible, use encoders when driving, as it results in more */
        /* predictable drive system response.                           */
            //leftMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            //rightMotor.setChannelMode(DcMotorController.RunMode.RUN_USING_ENCODERS);

        /* Create a PID Controller which uses the Yaw Angle as input. */
            yawPIDController = new navXPIDController( navx_device,
                    navXPIDController.navXTimestampedDataSource.YAW);

        /* Configure the PID controller */
            yawPIDController.setSetpoint(TARGET_ANGLE_DEGREES);
            yawPIDController.setContinuous(true);
            yawPIDController.setOutputRange(MIN_MOTOR_OUTPUT_VALUE, MAX_MOTOR_OUTPUT_VALUE);
            yawPIDController.setTolerance(navXPIDController.ToleranceType.ABSOLUTE, TOLERANCE_DEGREES);
            yawPIDController.setPID(YAW_PID_P, YAW_PID_I, YAW_PID_D);
            waitForStart();
            while (opModeIsActive()) {
                //Define angle for pan function by using trigonometry to calculate it from joystick
                theta = -Math.PI / 4 + Math.atan2(-gamepad1.left_stick_y, -gamepad1.left_stick_x);
                //Define power for pan function by using math to calculate the length of the hypotenuse between the x and y axes
                power = Math.pow(Math.sqrt((gamepad1.left_stick_x) * (gamepad1.left_stick_x) + (gamepad1.left_stick_y) * (gamepad1.left_stick_y)), 3);
                //Define power for turning, based solely on the x and y axes
                pivotpower = -gamepad1.right_stick_x;
                if (gamepad1.dpad_up) {
                    //Toggles slow mode.
                    if (turnScale == 1) {
                        //If it's fast, make it slow
                        driveScale = 0.25;
                        turnScale = 0.25;
                    } else {
                        //If it's slow, make it fast
                        driveScale = 1;
                        turnScale = 1;
                    }
                    while (gamepad1.dpad_up) {
                        //Don't constantly change back and forth. One change per button press.
                        idle();
                    }
                }
/*
            if (gamepad1.dpad_up && turnScale == 1) {
                driveScale = 0.25;
                turnScale = 0.25;
                idle();
            } else if (gamepad1.dpad_up && turnScale == 0.25) {
                driveScale = 1;
                turnScale = 1;
                idle();*/
                if (power > 0.2) {
                    //Power restrictions are so that random slightly-incorrect resets don't move the robot
                    Robot.holoDrive.pan(theta, power * driveScale);
                } else if (Math.abs(pivotpower) > 0.1) {
                    //Only turn it if it isn't driving
                    Robot.holoDrive.NE.setPower(pivotpower * turnScale);
                    Robot.holoDrive.SE.setPower(pivotpower * turnScale);
                    Robot.holoDrive.NW.setPower(-pivotpower * turnScale);
                    Robot.holoDrive.SW.setPower(-pivotpower * turnScale);
                } else {
                    //Otherwise, don't move
                    Robot.holoDrive.stopmotors();
                }

                if (gamepad1.dpad_left) {
                    //Moves the jewel arm up and down with a toggle
                    if (!JewelArmUp) {
                        Robot.jewelArm.jewelArmUp();
                        JewelArmUp = true;
                    } else {
                        Robot.jewelArm.jewelArmDown();
                        JewelArmUp = false;
                    }
                    while (gamepad1.dpad_left) {
                        idle();
                    }
                }

                if (gamepad1.left_trigger > 0.2 && gamepad1.right_trigger < 0.2 && !gamepad1.dpad_down) {
                    Robot.glyphArm.release();
                } else if (gamepad1.left_trigger < 0.2 && gamepad1.right_trigger > 0.2 && !gamepad1.dpad_down) {
                    Robot.glyphArm.grab();
                    open = false;
                } else if (gamepad1.left_trigger < 0.2 && gamepad1.right_trigger < 0.2 && gamepad1.dpad_down) {
                    Robot.glyphArm.sortOfRelease();
                }
                if (gamepad1.left_bumper) {
                    //Moves the left grabber, only if the button is being held
                    Robot.glyphArm.leftGrab();
                    open = true;
                } else if (open) {
                    Robot.glyphArm.leftRelease();
                }
                if (gamepad1.right_bumper) {
                    //Moves the right bumper, only if the button is being held
                    Robot.glyphArm.rightGrab();
                    open = true;
                } else if (open) {
                    Robot.glyphArm.rightRelease();
                }


                if (gamepad1.a && ((Math.abs(Robot.glyphArm.liftArm.getCurrentPosition() - Robot.glyphArm.LiftZeroPosition)) > Robot.glyphArm.WiggleRoom)) {
                    //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                    Robot.glyphArm.zeroPosition(this);
                } else if (gamepad1.b && ((Math.abs(Robot.glyphArm.liftArm.getCurrentPosition() - Robot.glyphArm.LiftMidPosition)) > Robot.glyphArm.WiggleRoom)) {
                    //Moves to the middle position. Extra math stuff is designed so that it won't move if it's there or almost there.
                    Robot.glyphArm.midPosition(this);
                } else if (gamepad1.y && ((Math.abs(Robot.glyphArm.liftArm.getCurrentPosition() - Robot.glyphArm.LiftTopPosition)) > Robot.glyphArm.WiggleRoom)) {
                    //Moves to the zero position. Extra math stuff is designed so that it won't move if it's there or almost there.
                    Robot.glyphArm.topPosition(this);
                } else if (gamepad1.x) {
                    Robot.glyphArm.incrementPosition(this);
                } else {
                    //Otherwise, maintains position
                    Robot.glyphArm.stopLifting();
                }
            }
        }
    }

}
