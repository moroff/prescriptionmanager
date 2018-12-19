package info.moroff.prescriptionmanager.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UITools {

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	
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
	
	public String formatDate(LocalDate date) {
		if ( date == null) {
			return "";
		}
		else {
			return date.format(formatter);
		}
	}
}
