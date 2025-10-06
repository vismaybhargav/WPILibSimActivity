package frc.robot.systems;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import org.littletonrobotics.junction.AutoLogOutput;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearVelocity;
import frc.robot.CommandSwerveDrivetrain;
import frc.robot.Constants.DriveConstants;
import frc.robot.TeleopInput;
import frc.robot.TunerConstants;

public class DriveFSMSystem extends FSMSystem<DriveFSMSystem.DriveFSMState> {
	public enum DriveFSMState {
		TELEOP
	}

	private static final LinearVelocity MAX_SPEED = TunerConstants.SPEED_AT_12_VOLTS;
		// kSpeedAt12Volts desired top speed
	private static final AngularVelocity MAX_ANGULAR_RATE = DriveConstants.MAX_ANGULAR_VELO_RPS;

	private final SwerveRequest.FieldCentric drive
		= new SwerveRequest.FieldCentric()
		.withDeadband(MAX_SPEED.in(MetersPerSecond)
		* DriveConstants.TRANSLATION_DEADBAND) // 4% deadband
		.withRotationalDeadband(MAX_ANGULAR_RATE.in(RadiansPerSecond)
		* DriveConstants.ROTATION_DEADBAND) //4% deadband
		.withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop for drive motors

	private static CommandSwerveDrivetrain drivetrain;

	/**
	 * Create DriveFSMSystem and initialize to starting state. Also perform any
	 */
	public DriveFSMSystem() {
		setCurrentState(DriveFSMState.TELEOP);

		drivetrain = TunerConstants.createDrivetrain();

	}

	@Override
	public void reset() {
		setCurrentState(DriveFSMState.TELEOP);

		update(null);
	}

	@Override
	public void update(TeleopInput input) {
		switch (getCurrentState()) {
			case TELEOP:
				handleTeleopState(input);
				break;
			default:
				throw new IllegalStateException("Invalid state: " + getCurrentState().toString());
		}
		setCurrentState(nextState(input));

	}

	@Override
	protected DriveFSMState nextState(TeleopInput input) {
		switch (getCurrentState()) {
			case TELEOP:
				return DriveFSMState.TELEOP;
			default:
				throw new IllegalStateException("Invalid state: " + getCurrentState().toString());
		}
	}

	private void handleTeleopState(TeleopInput input) {
		if (input == null) {
			return;
		}

		double xSpeed = MathUtil.applyDeadband(
			-input.getDriveLeftJoystickY(),
			DriveConstants.TRANSLATION_DEADBAND
		) * MAX_SPEED.in(MetersPerSecond);

		double ySpeed = MathUtil.applyDeadband(
			-input.getDriveLeftJoystickX(),
			DriveConstants.TRANSLATION_DEADBAND
		) * MAX_SPEED.in(MetersPerSecond);

		double thetaSpeed = MathUtil.applyDeadband(
			-input.getDriveRightJoystickX(),
			DriveConstants.ROTATION_DEADBAND
		) * MAX_ANGULAR_RATE.in(RadiansPerSecond);

		drivetrain.setControl(
			drive
				.withVelocityX(xSpeed * DriveConstants.TRANSLATIONAL_DAMP)
				.withVelocityY(ySpeed * DriveConstants.TRANSLATIONAL_DAMP)
				.withRotationalRate(thetaSpeed * DriveConstants.ROTATIONAL_DAMP)
		);
	}

	/**
	 * Get the current robot pose.
	 * @return Current robot pose
	 */
	@AutoLogOutput(key = "Swerve/Drivetrain Pose")
	public Pose2d getPose() {
		return drivetrain.getState().Pose;
	}

	/**
	 * Get the chassis speeds of the drivetrain.
	 * @return the drivetrain chassis speeds
	 */
	@AutoLogOutput(key = "Swerve/Chassis Speeds")
	public ChassisSpeeds getChassisSpeeds() {
		return drivetrain.getState().Speeds;
	}

	/**
	 * Get the drivetrain states.
	 * @return the swerve module states
	 */
	@AutoLogOutput(key = "Swerve/States/Measured")
	public SwerveModuleState[] getModuleStates() {
		return drivetrain.getState().ModuleStates;
	}

	/**
	 * Get the drivetrain targets.
	 * @return drivetrain targets
	 */
	@AutoLogOutput(key = "Swerve/States/Targets")
	public SwerveModuleState[] getModuleTargets() {
		return drivetrain.getState().ModuleTargets;
	}

	/**
	 * Adds a new timestamped vision measurement.
	 *
	 * @param visionPoseMeters The pose of the robot in the camera's coordinate
	 *                         frame
	 * @param timestampSeconds The timestamp of the measurement
	 * @param visionStdDevs    The standard deviations of the measurement in the x,
	 *                         y, and theta directions
	 */
	public void addVisionMeasurement(
			Pose2d visionPoseMeters,
			double timestampSeconds,
			Matrix<N3, N1> visionStdDevs) {
		drivetrain.addVisionMeasurement(
				visionPoseMeters,
				timestampSeconds,
				visionStdDevs);
	}
}
