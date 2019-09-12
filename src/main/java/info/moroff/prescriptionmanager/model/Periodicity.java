package info.moroff.prescriptionmanager.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Periodicity {

	DAILY(0), //
	WEEKLY(1), //
	MONTHLY(2), //
	QUARTER(3), //
	YEARLY(4);

	private final int value;

	Periodicity(final int newValue) {
		value = newValue;
	}

	@JsonValue
	public int getValue() {
		return value;
	}

}
