package frc.robot.motors;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Rotations;
import static edu.wpi.first.units.Units.Volts;
import static java.lang.Math.PI;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

/**
 * TalonFx wrapper class, using DC motor sim + sim states.
 */
public class TalonFXWrapper extends TalonFX implements LoggedMotor {

	// Attributes
	private static final double K_GEAR_RATIO = 10.0;
	private static final double INERTIA_CONSTANT = 0.001;

	// Components
	private final DCMotor configs;
	// Sim model for calculations
	// getSimState() acccesses the default sim
	private final DCMotorSim motorSimModel;

	public static final double LOOP_PERIOD_MS = 0.020;

	/**
	 * Constructor with device ID.
	 * @param deviceId the CAN ID of the motor
	 */
	public TalonFXWrapper(int deviceId) {
		this(deviceId, MotorConstants.DEFAULT_TALONFX_CONFIG);
	}

	/**
	 * Constructor with device ID and motor type.
	 * @param deviceId the CAN ID of the motor
	 * @param motorConfigs the motor type
	 */
	public TalonFXWrapper(int deviceId, DCMotor motorConfigs) {
		this(deviceId, "", motorConfigs);
	}

	/**
	 * Constructor with device ID and CAN bus string.
	 * @param deviceId the CAN ID of the motor
	 * @param canbus the string form of the canbus
	 */
	public TalonFXWrapper(int deviceId, String canbus) {
		this(deviceId, canbus, DCMotor.getKrakenX60Foc(1));
	}

	/**
	 * Constructor with device ID, CAN bus string, and motor type.
	 * @param deviceId the CAN ID of the motor
	 * @param canbus the string form of the canbus
	 * @param motorConfigs the motor type
	 */
	public TalonFXWrapper(int deviceId, String canbus, DCMotor motorConfigs) {
		// Initialize motor
		super(deviceId, canbus);
		init();

		// Create sim instance
		configs = motorConfigs;
		motorSimModel = new DCMotorSim(
			LinearSystemId.createDCMotorSystem(
				configs,
				INERTIA_CONSTANT,
				K_GEAR_RATIO
			),
			configs
		);
	}

	@Override
	public void updateSimState() {
		var talonFXSim = getSimState();

		// set the supply voltage of the TalonFX
		talonFXSim.setSupplyVoltage(RobotController.getBatteryVoltage());

		// get the motor voltage of the TalonFX
		var motorVoltage = talonFXSim.getMotorVoltageMeasure();

		// use the motor voltage to calculate new position and velocity
		// using WPILib's DCMotorSim class for physics simulation
		motorSimModel.setInputVoltage(motorVoltage.in(Volts));
		motorSimModel.update(LOOP_PERIOD_MS); // assume 20 ms loop time

		// apply the new rotor position and velocity to the TalonFX;
		// note that this is rotor position/velocity (before gear ratio), but
		// DCMotorSim returns mechanism position/velocity (after gear ratio)
		talonFXSim.setRawRotorPosition(motorSimModel.getAngularPosition().times(K_GEAR_RATIO));
		talonFXSim.setRotorVelocity(motorSimModel.getAngularVelocity().times(K_GEAR_RATIO));
	}

	@Override
	public String getIdentifier() {
		return Integer.toString(getDeviceID());
	}

	@Override
	public double getLoggedPosition() {
		return this.getPosition().getValue().in(Rotations);
	}

	@Override
	public double getLoggedVelocity() {
		return this.getVelocity().getValue().in(RPM);
	}

	@Override
	public double getLoggedSetpoint() {
		return get();
	}

	@Override
	public double getLoggedVoltage() {
		return getMotorVoltage().getValue().in(Volts);
	}

	@Override
	public double getLoggedCurrent() {
		return getSupplyCurrent().getValue().in(Amps);
	}

	/**
	 * Sets the raw position of the motor.
	 * @param pos the position to set
	 */
	public void setRawPosition(double pos) {
		motorSimModel.setAngle(pos * 2 * PI);
		getSimState().setRawRotorPosition(pos);
	}
}
