package frc.robot.systems;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class VisionIOInputsAutoLogged extends
	VisionIO.VisionIOInputs implements LoggableInputs, Cloneable {
	@Override
	public void toLog(LogTable table) {
		table.put("Connected", connected);
		table.put("LatestTargetObservation", latestTargetObservation);
		table.put("PoseObservations", poseObservations);
		table.put("TagObservations", tagObservations);
		table.put("TagIds", tagIds);
	}

	@Override
	public void fromLog(LogTable table) {
		connected = table.get("Connected", connected);
		latestTargetObservation = table.get("LatestTargetObservation", latestTargetObservation);
		poseObservations = table.get("PoseObservations", poseObservations);
		tagObservations = table.get("TagObservations", tagObservations);
		tagIds = table.get("TagIds", tagIds);
	}

	/**
	 * Creates and returns a copy of this object.
	 * @return A copy of this object.
	 */
	public VisionIOInputsAutoLogged clone() {
		VisionIOInputsAutoLogged copy = new VisionIOInputsAutoLogged();
		copy.connected = this.connected;
		copy.latestTargetObservation = this.latestTargetObservation;
		copy.poseObservations = this.poseObservations.clone();
		copy.tagObservations = this.tagObservations.clone();
		copy.tagIds = this.tagIds.clone();
		return copy;
	}
}
