package frc.robot;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;

public class Constants {
	public class DriveConstants {
		public static final double TRANSLATION_DEADBAND = 0.05;
		public static final double ROTATION_DEADBAND = 0.05;

		public static final AngularVelocity MAX_ANGULAR_VELO_RPS = RotationsPerSecond.of(0.75);

		public static final int SYS_ID_VOLT_DAMP = 6;

		//Set to the decimal corresponding to the percentage of how fast you want the bot to go
		// 1 = 100% speed, 0.5 = 50% speed, 0.3 = 30% speed, and so on
		public static final double TRANSLATIONAL_DAMP = 1;
		public static final double ROTATIONAL_DAMP = 1;
	}

	public class AutoConstants {
		public static final double TRANSLATION_P = 0;
		public static final double TRANSLATION_I = 0;
		public static final double TRANSLATION_D = 0;

		public static final double ROTATION_P = 0;
		public static final double ROTATION_I = 0;
		public static final double ROTATION_D = 0;
	}

	public static final class ModuleConstants {
		public static final double DRIVE_P = 0.1;
		public static final double DRIVE_I = 0;
		public static final double DRIVE_D = 0;
		public static final double DRIVE_V = 0.124;

		public static final double DRIVE_CURRENT_LIMIT = 60;
		public static final double STEER_CURRENT_LIMIT = 60;

		public static final double STEER_P = 100;
		public static final double STEER_I = 0;
		public static final double STEER_D = 0.5;
		public static final double STEER_V = 0.1;
		public static final double STEER_S = 0;
	}

	public static final class VisionConstants {

		public static final AprilTagFieldLayout TAG_LAYOUT =
			AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);

		public static final int TAG_ID_TEST_STATION = 1;
		public static final int TAG_ID_TEST_REEF_LEFT = 2;
		public static final int TAG_ID_TEST_REEF_RIGHT = 3;

		public static final String REEF_CAMERA_NAME = "Reef_Camera";
		public static final String STATION_CAMERA_NAME = "Source_Camera";

		public static final Transform3d ROBOT_TO_REEF_CAM =
			new Transform3d(Units.inchesToMeters(7.129),
				-Units.inchesToMeters(4.306),
				Units.inchesToMeters(14.56), new Rotation3d(0.0, 0.0, 0.0));
		public static final Transform3d ROBOT_TO_STATION_CAM = new Transform3d(
				-Units.inchesToMeters(8.875),
				-Units.inchesToMeters(9.5),
				Units.inchesToMeters(37.596), new Rotation3d(0.0, -Math.toRadians(19), Math.PI));

		public static final double MAX_AMBIGUITY = 0.1;
		public static final double MAX_Z_ERROR = 0.3; // meters
		public static final Distance STOP_PATHFINDING_UPDATES = Meters.of(2);

		public static final double LINEAR_STD_DEV_BASELINE = 0.02;
		public static final double ANGULAR_STD_DEV_BASELINE = 0.06;

		public static final double[] CAMERA_STD_DEV_FACTORS = new double[] {
			1.0, // Reef Camera
			0.4 // Station Camera
		};
	}
}
