package info.moroff.prescriptionmanager.therapy;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class TherapyTest {
	Logger logger = LogManager.getLogger();
	
	@Test
	void testPreviousTreatments() {
		Therapy therapy = new Therapy();
		TherapyPrescription prescription;
		
		therapy.setName("Test Therapy");

		assertEquals("keine", therapy.getFutureAppointments());
		assertNull(therapy.getLastAppointmentDate());
		
		// Search next monday
		LocalDate date = LocalDate.now();
		date = nextWeekday(date, MONDAY);
		
		// First prescription, ...
		prescription = new TherapyPrescription();
		therapy.addPrescription(prescription);

		prescription.setCount(4);
		prescription.setPrescriptionDate(date.minusWeeks(6));
		prescription.setOnFridays(true);
		
		assertEquals("keine", therapy.getFutureAppointments());
		assertNull(therapy.getLastAppointmentDate());

		// ... add appointments in the past
		prescription.addAppointments(therapy);

		
		int appointmentCount = prescription.getAppointments().size();
		assertEquals(4, appointmentCount);
		assertEquals("keine", therapy.getFutureAppointments());
		assertEquals(nextWeekday(date.minusWeeks(3), FRIDAY), therapy.getLastAppointmentDate());
		
		// Second prescription, yet without appointments
		prescription = new TherapyPrescription();
		therapy.addPrescription(prescription);
		prescription.setCount(4);
		prescription.setPrescriptionDate(date.minusWeeks(2));
		prescription.setOnFridays(true);

		assertEquals("keine", therapy.getFutureAppointments());
		assertEquals(nextWeekday(date.minusWeeks(3), FRIDAY), therapy.getLastAppointmentDate());

		// Added appointments to second prescription
		prescription.addAppointments(therapy);

		assertEquals("2", therapy.getFutureAppointments());
	
		// Add prescription in future
		prescription = new TherapyPrescription();
		therapy.addPrescription(prescription);
		prescription.setCount(4);
		prescription.setPrescriptionDate(date.plusWeeks(3));
		prescription.setOnFridays(true);
		prescription.addAppointments(therapy);
		
		therapy.getPrescriptions().forEach((p) -> logger.info(p.toString()));
		prescription.getAppointments().forEach((a) -> logger.info(a.toString()));
		
		assertEquals("6", therapy.getFutureAppointments());
	}

	public static LocalDate nextWeekday(LocalDate date, DayOfWeek dayOfWeek) {
		while ( date.getDayOfWeek() != dayOfWeek ) {
			date = date.plusDays(1);
		}
		return date;
	}

}
