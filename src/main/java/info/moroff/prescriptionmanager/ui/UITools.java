package info.moroff.prescriptionmanager.ui;

public class UITools {

	public String getLabelForRemainingDays(Integer remainingDays) {
		if (remainingDays == null) {
			return "";
		} else if (remainingDays >= 14) {
			return "label label-success";
		} else if (remainingDays >= 8) {
			return "label label-warning";
		} else {
			return "label label-danger";

		}
	}
}
