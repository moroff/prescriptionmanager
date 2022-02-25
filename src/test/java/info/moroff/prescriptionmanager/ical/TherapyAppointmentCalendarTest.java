package info.moroff.prescriptionmanager.ical;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import info.moroff.prescriptionmanager.IOutputGenerator;
import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.therapy.Therapy;
import info.moroff.prescriptionmanager.therapy.TherapyAppointment;
import info.moroff.prescriptionmanager.therapy.TherapyAppointmentRepository;
import info.moroff.prescriptionmanager.therapy.TherapyPrescription;

@ExtendWith(MockitoExtension.class)
class TherapyAppointmentCalendarTest {
	private Logger logger = LogManager.getLogger();
	
	@Mock
	Patient patientMock;
	
	@Mock
	Therapy therapyMock;
	
	@Mock
	TherapyAppointment appointmentMock;
	
	@Mock
	TherapyAppointmentRepository appointmentRepository;
	
	@Mock
	TherapyPrescription prescriptionMock;
	
	@Test
	void testGenerateOutput_simple() {
		IOutputGenerator calendar = new TherapyAppointmentCalendar(appointmentRepository);
		
		Mockito.when(patientMock.toString()).thenReturn("Unit test patient");
		
		String output = calendar.generateOutput(patientMock);
		
		assertNotNull(output);
		System.out.println(output);
	}

	@Test
	void testGenerateOutput_withAppointment() {
		IOutputGenerator calendar = new TherapyAppointmentCalendar(appointmentRepository);
		
		when(patientMock.getLastName()).thenReturn("Test");
		when(patientMock.getFirstName()).thenReturn("Unit");
		when(patientMock.getTherapies()).thenReturn(createList(therapyMock));
		when(therapyMock.getName()).thenReturn("Unit test therapy");
		when(therapyMock.getAppointments()).thenReturn(createList(appointmentMock));
		when(appointmentMock.getDate()).thenReturn(LocalDate.now());
		when(appointmentMock.getTherapy()).thenReturn(therapyMock);
		when(appointmentMock.getPrescription()).thenReturn(prescriptionMock);
		when(appointmentMock.getUuid()).thenReturn("Unit-Test-UUId");
		when(prescriptionMock.getTherapyDuration()).thenReturn(60);
		when(prescriptionMock.getTherapyStartTime()).thenReturn(LocalTime.now());
		when(prescriptionMock.getPrescriptionDate()).thenReturn(LocalDate.now());
		
		String output = calendar.generateOutput(patientMock);
		
		assertNotNull(output);
		System.out.println(output);
	}

	private <T> List<T> createList(T listItem) {
		List<T> list = new ArrayList<>();
		list.add(listItem);
		return list;
	}
	
}
