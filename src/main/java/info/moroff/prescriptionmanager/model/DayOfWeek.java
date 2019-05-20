package info.moroff.prescriptionmanager.model;

public enum DayOfWeek {
	MONDAY(0), //
	TUESDAY(1), //
	WEDNESDAY(2), //
	THURSDAY(3), //
	FRIDAY(4), //
	SATURDAY(5), //
	SUNDAY(6);

	private final int value;
	
	DayOfWeek(final int newValue) {
		value = newValue;
	}

	public int getValue() { return value; }
}
