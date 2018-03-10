package org.firstinspires.ftc.Tempest_2017_2018.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Jewel_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.ColorSensorClass;
import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.NavX;

/**
 * Created by AshQuinn on 12/2/17.
 */

public class Robot2017_2018 {
    //Switch for red/blue mode. True means red mode (unlike last year)
    public DigitalChannel BlueSwitch;
    public DigitalChannel LeftRightSwitch;
    public ColorSensorClass color;
    public Jewel_Arm jewelArm;
    public Glyph_Arm glyphArm;
    public HolonomicDrive holoDrive;
    public LinearOpMode currop;
    public static boolean leftturn;
    //public NavX navx;

    public Robot2017_2018(LinearOpMode currop){
        this.currop = currop;
    }

    public void init (HardwareMap HWMap) {
        color = new ColorSensorClass();
        color.init(HWMap);

        jewelArm = new Jewel_Arm();
        jewelArm.init(HWMap);

        glyphArm = new Glyph_Arm();
        glyphArm.init(HWMap);

        BlueSwitch = HWMap.digitalChannel.get("LEDBlueSwitch");
        BlueSwitch.setMode(DigitalChannel.Mode.INPUT);
        LeftRightSwitch = HWMap.digitalChannel.get("LeftRightSwitch");
        LeftRightSwitch.setMode(DigitalChannel.Mode.INPUT);

        holoDrive = new HolonomicDrive();
        holoDrive.init (HWMap);
        //navx = new NavX();
        //navx.init(HWMap);

    }
    public void Sleep(long ticks) throws InterruptedException {
        long timer = System.currentTimeMillis();
        while (System.currentTimeMillis() - timer < ticks) {
            currop.idle();
        }
    }
    /**
     *
     * @param angle1 vector angle for initial movement
     * @param dist1 distance for initial movement
     * @param turndeg angle of degrees for putting the glyph in the correct spot
     * @param angle2 vector angle for driving(should always be forward)
     * @param dist2 distance for driving into the box
     */
    public void panturnpan(double angle1, double dist1, double turndeg, double angle2, double dist2) {
        double currdist = holoDrive.NW.getCurrentPosition();
        holoDrive.pan(angle1, 0.3);
        while ((Math.abs(holoDrive.NW.getCurrentPosition() - currdist)) <= dist1) {
            currop.idle();
        }
        holoDrive.stopmotors();
        if (leftturn) {
            currdist = holoDrive.NW.getCurrentPosition();
            holoDrive.turnleftunlim(0.3);
            while ((Math.abs(holoDrive.NW.getCurrentPosition() - currdist)) <= turndeg * 200 / 36) {
                currop.idle();
            }
        } else if (!leftturn) {
            currdist = holoDrive.NW.getCurrentPosition();
            holoDrive.turnrightunlim(0.3);
            while ((Math.abs(holoDrive.NW.getCurrentPosition() - currdist)) <= turndeg * 200 / 36) {
                currop.idle();
            }
        }
        holoDrive.stopmotors();
        currdist = holoDrive.NW.getCurrentPosition();
        holoDrive.pan(angle2, 0.3);
        while ((Math.abs(holoDrive.NW.getCurrentPosition() - currdist)) <= dist2) {
            currop.idle();
        }
        holoDrive.stopmotors();
    }
    public void backUp() throws InterruptedException{
        holoDrive.pan(Math.PI*5/4, 0.3);
        Sleep(250);
        holoDrive.stopmotors();
        glyphArm.zeroPosition(currop);
        Sleep(1000);
        glyphArm.grab();
        Sleep(500);
        holoDrive.pan(Math.PI/4, 0.3);
        Sleep(250);
        holoDrive.pan(Math.PI * 5 / 4, 0.3);
        Sleep(500);
        holoDrive.stopmotors();
    }
    public void backUpMore() throws InterruptedException{
        holoDrive.pan(Math.PI*5/4, 0.3);
        Sleep(400);
        holoDrive.stopmotors();
        glyphArm.zeroPosition(currop);
        Sleep(1000);
        glyphArm.grab();
        Sleep(500);
        holoDrive.pan(Math.PI/4, 0.3);
        Sleep(400);
        holoDrive.pan(Math.PI * 5 / 4, 0.3);
        Sleep(500);
        holoDrive.stopmotors();
    }

}