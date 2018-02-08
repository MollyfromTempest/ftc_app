package org.firstinspires.ftc.Tempest_2017_2018.teamcode.Sensors;

/**
 * Created by Chris on 1/27/2018.
 */
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

    @Autonomous(name="Vuforia")
    public class Vuforia extends LinearOpMode {
        public static final String TAG = "Vuforia VuMark Sample";
        OpenGLMatrix lastLocation = null;
        VuforiaLocalizer vuforia;
        @Override
        public void runOpMode() throws InterruptedException{
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
            parameters.vuforiaLicenseKey = "AdWgUmr/////AAAAmdti1qKm9EedjDMUSL69hX+HJOhoZN/4cP0fCM8xi1/R0wjPyqh42r8h3X/xU4ONO+d0z+Of5xBeLnjXGG/UAmVWo7idgTEklCIYYQbNXGu4RWYoh2jsg9tpMSBvbhaTVkDJizAQa+XeSYDVb5N3S41xBjCiMB0zj8ECwGBp7cEHxZSjfTbm++fm3rBPMy0zMKd1vj1Gy7plv1YqDhPJYqXGK1sEOyMaUgtvbtf9lzKfz6pGv7ky3iD6U1QcPOHJ2FFEZhVrYynupvTy3T+kYVdKjuiv6eNuBTFZZAlnsq5pTUgyoZDfn7c4kKsEbbF0kmXwzpHWhhPSvcPRrAY2Z4UJz31/fJ0P835eibOQFjeU";
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
            VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
            VuforiaTrackable relicTemplate = relicTrackables.get(0);
            relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
            telemetry.addData(">", "Press Play to start");
            telemetry.update();
            waitForStart();
            relicTrackables.activate();
            while (opModeIsActive()) {
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
               /* Found an instance of the template. In the actual game, you will probably
                * loop until this condition occurs, then move on to act accordingly depending
                * on which VuMark was visible. */
                    telemetry.addData("VuMark", "%s visible", vuMark);
               /* For fun, we also exhibit the navigational pose. In the Relic Recovery game,
                * it is perhaps unlikely that you will actually need to act on this pose information, but
                * we illustrate it nevertheless, for completeness. */
                    OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                    telemetry.addData("Pose", format(pose));
               /* We further illustrate how to decompose the pose into useful rotational and
                * translational components */
                    if (pose != null) {
                        VectorF trans = pose.getTranslation();
                        Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                        // Extract the X, Y, and Z components of the offset of the target relative to the robot
                        double tX = trans.get(0);
                        double tY = trans.get(1);
                        double tZ = trans.get(2);
                        // Extract the rotational components of the target relative to the robot
                        double rX = rot.firstAngle;
                        double rY = rot.secondAngle;
                        double rZ = rot.thirdAngle;
                    }
                }
                else {
                    telemetry.addData("VuMark", "not visible");
                }
                telemetry.update();
            }
        }
        String format(OpenGLMatrix transformationMatrix) {
            return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
        }
    }
