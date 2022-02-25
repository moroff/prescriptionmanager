package info.moroff.prescriptionmanager.ical;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import biweekly.Biweekly;
import biweekly.ICalVersion;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import biweekly.property.CalendarScale;
import biweekly.util.Duration;
import info.moroff.prescriptionmanager.IOutputGenerator;
import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.therapy.Therapy;
import info.moroff.prescriptionmanager.therapy.TherapyAppointment;
import info.moroff.prescriptionmanager.therapy.TherapyAppointmentRepository;

public class TherapyAppointmentCalendar implements IOutputGenerator {

	private TherapyAppointmentRepository appointmentRepository;
	
	@Autowired
	public TherapyAppointmentCalendar(TherapyAppointmentRepository appointmentRepository) {
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public String generateOutput(Patient patient) {
		ICalendar calendar = new ICalendar();
		calendar.setDescription("Prescription Manager Calendar for "+patient.getLastName()+", "+patient.getFirstName());
		calendar.setProductId("//Prescription Manager//v1 ");
		calendar.setVersion(ICalVersion.V2_0);
		calendar.setCalendarScale(CalendarScale.gregorian());
		
		// Add appointments
		patient.getTherapies().stream()//
			.map(Therapy::getAppointments)//
			.flatMap(List<TherapyAppointment>::stream)//
			.sorted((a1, a2) -> a1.getDate().compareTo(a2.getDate()))
			.forEach(a -> {
				// generate unique identifier if not present
				if ( a.getUuid() == null ) {
					a.setUuid(UUID.randomUUID().toString());
					appointmentRepository.save(a);
				}
				// create event and add to calendar
				calendar.addEvent(createEvent(a));
			});
		
		
		return Biweekly.write(calendar).go();
	}

	public VEvent createEvent(TherapyAppointment a) {
		Integer therapyDuration = a.getPrescription().getTherapyDuration();
		LocalTime startTime = a.getPrescription().getTherapyStartTime();
		Date date = toDate(a.getDate(), startTime);
		String name = a.getTherapy().getName();
		VEvent appointment = new VEvent();
		
		appointment.setSummary(name);
		appointment.setDateStart(date, startTime != null);
		if ( startTime != null && therapyDuration != null ) {
			appointment.setDuration(Duration.builder().minutes(therapyDuration).build());
		}
		appointment.setUid(a.getUuid());
		int appointmentNumber = a.getPrescription().getAppointments().indexOf(a) + 1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		appointment.setDescription(appointmentNumber+". Termin der Verschreibung vom "+a.getPrescription().getPrescriptionDate().format(formatter ));
		return appointment;
	}
	
	private Date toDate(LocalDate localDate, LocalTime localTime) {
		java.util.Calendar date = java.util.Calendar.getInstance();
		
		if ( localTime != null ) {
			date.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth(), localTime.getHour(), localTime.getMinute());
			date.setTimeZone(TimeZone.getTimeZone("MET"));
		}
		else {
			date.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth());
		}
		return date.getTime();
	}
}
