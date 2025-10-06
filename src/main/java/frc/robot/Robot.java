// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import static frc.robot.Constants.VisionConstants.REEF_CAMERA_NAME;
import static frc.robot.Constants.VisionConstants.ROBOT_TO_REEF_CAM;
import static frc.robot.Constants.VisionConstants.ROBOT_TO_STATION_CAM;
import static frc.robot.Constants.VisionConstants.STATION_CAMERA_NAME;

import org.littletonrobotics.junction.LoggedRobot;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.networktables.NT4Publisher;
import org.littletonrobotics.junction.wpilog.WPILOGWriter;

// WPILib Imports
import frc.robot.systems.DriveFSMSystem;
import frc.robot.systems.Vision;
import frc.robot.systems.VisionIOPhotonPoseEstimator;
import frc.robot.systems.VisionIOPhotonPoseEstimatorSim;

// Systems
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation.
 */
public class Robot extends LoggedRobot {
	private TeleopInput input;

	// Systems
	private DriveFSMSystem drivetrain;
	private Vision vision;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("robotInit");
		input = new TeleopInput();

		Logger.recordMetadata("ProjectName", "MyProject"); // Set a metadata value

		if (isReal()) {
			Logger.addDataReceiver(new WPILOGWriter()); // Log to a USB stick ("/U/logs")
			Logger.addDataReceiver(new NT4Publisher()); // Publish data to NetworkTables
		} else if (isSimulation()) {
			Logger.addDataReceiver(new NT4Publisher());
		}

		Logger.start(); // Start

		// Instantiate all systems here
		drivetrain = new DriveFSMSystem();

		if (isReal()) {
			vision = new Vision(
					drivetrain::addVisionMeasurement,
					new VisionIOPhotonPoseEstimator(REEF_CAMERA_NAME, ROBOT_TO_REEF_CAM),
					new VisionIOPhotonPoseEstimator(STATION_CAMERA_NAME, ROBOT_TO_STATION_CAM));
		} else if (isSimulation()) {
			vision = new Vision(
					drivetrain::addVisionMeasurement,
					new VisionIOPhotonPoseEstimatorSim(
							REEF_CAMERA_NAME, ROBOT_TO_REEF_CAM, drivetrain::getPose),
					new VisionIOPhotonPoseEstimatorSim(
							STATION_CAMERA_NAME, ROBOT_TO_STATION_CAM, drivetrain::getPose));
		}
	}

	@Override
	public void autonomousInit() {
		System.out.println("-------- Autonomous Init --------");
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		System.out.println("-------- Teleop Init --------");
		drivetrain.reset();
	}

	@Override
	public void teleopPeriodic() {
		drivetrain.update(input);
	}

	@Override
	public void disabledInit() {
		System.out.println("-------- Disabled Init --------");
	}

	@Override
	public void disabledPeriodic() {

	}

	@Override
	public void testInit() {
		System.out.println("-------- Test Init --------");
	}

	@Override
	public void testPeriodic() {

	}

	/* Simulation mode handlers, only used for simulation testing */
	@Override
	public void simulationInit() {
		System.out.println("-------- Simulation Init --------");
	}

	@Override
	public void simulationPeriodic() {

	}

	// Do not use robotPeriodic. Use mode specific periodic methods instead.
	@Override
	public void robotPeriodic() {
		vision.periodic();
	}
}
