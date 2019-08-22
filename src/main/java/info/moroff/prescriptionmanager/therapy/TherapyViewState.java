package info.moroff.prescriptionmanager.therapy;

public class TherapyViewState {
	private int prescriptionId;

	public int getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(int viewPrescriptionId) {
		this.prescriptionId = viewPrescriptionId;
	}
	
	private int therapyId;

	public int getTherapyId() {
		return therapyId;
	}
	public void setTherapyId(int therapyId) {
		this.therapyId = therapyId;
	}
}
