package org.firstinspires.ftc.Tempest_2017_2018.teamcode;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.Tempest_2017_2018.teamcode.DriveTrains.HolonomicDrive;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Glyph_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Manipulators.Jewel_Arm;
import org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors.ColorSensorClass;
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
    //public NavX navx;

    public void init (HardwareMap HWMap) {
        color = new ColorSensorClass();
        color.init(HWMap);

        jewelArm = new Jewel_Arm();
        jewelArm.init(HWMap);

        glyphArm = new Glyph_Arm();
        glyphArm.init(HWMap);

        BlueSwitch = HWMap.digitalChannel.get("LEDBlueSwitch");
        LeftRightSwitch = HWMap.digitalChannel.get("LeftRightSwitch");

        holoDrive = new HolonomicDrive();
        holoDrive.init (HWMap);
        //navx = new NavX();
        //navx.init(HWMap);

    }
}