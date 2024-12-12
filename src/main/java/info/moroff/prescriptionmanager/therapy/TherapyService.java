package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.patient.PatientService;

/**
 * Service dealing with therapies, prescriptions and appointments.
 */
@Service
public class TherapyService {
	private final Logger logger = LogManager.getLogger();

	private final TherapyRepository therapyRepository;
	private final TherapyPrescriptionRepository prescriptionRepository;
	private final TherapyAppointmentRepository appointmentRepository;
	private final PatientService patientService;
	
	@Autowired
	public TherapyService( //
			TherapyRepository therapyRepository, //
			TherapyPrescriptionRepository prescriptionRepository, //
			TherapyAppointmentRepository appointmentRepository,
			PatientService patientService) {
		super();
		this.therapyRepository = therapyRepository;
		this.prescriptionRepository = prescriptionRepository;
		this.appointmentRepository = appointmentRepository;
		this.patientService = patientService;
	}

	@Transactional
	Therapy add(Patient patient, Therapy therapy, TherapyPrescription prescription) {
		therapy.setPatient(patient);
		therapy.addPrescription(prescription);
		therapyRepository.save(therapy);
		return therapy;
	}

	@Transactional(readOnly = true)
	public Therapy findById(int therapyId) {
		return therapyRepository.findById(therapyId);
	}

	@Transactional
	public void updateTherapy(Therapy viewedTherapy, int therapyId, TherapyPrescription viewedPrescription, int prescriptionId) {
		updateTherapy(viewedTherapy, therapyRepository.findById(therapyId), viewedPrescription, prescriptionId);
	}

	private void updateTherapy(Therapy viewedTherapy, Therapy storedTherapy, TherapyPrescription viewedPrescription, int prescriptionId) {
		TherapyPrescription storedPrescription;

		if (storedTherapy != null) {
			storedTherapy.setName(viewedTherapy.getName());
			this.therapyRepository.save(storedTherapy);
		}

		if (prescriptionId != 0) {
			storedPrescription = prescriptionRepository.findById(prescriptionId);
		} else {
			storedPrescription = new TherapyPrescription();
			storedTherapy.addPrescription(storedPrescription);
		}

		if (storedPrescription != null) {
			storedPrescription.setFirstTherapyDate(viewedPrescription.getFirstTherapyDate());
			storedPrescription.setPrescriptionDate(viewedPrescription.getPrescriptionDate());
			storedPrescription.setCount(viewedPrescription.getCount());
			storedPrescription.setOnMondays(viewedPrescription.getOnMondays());
			storedPrescription.setOnTuesdays(viewedPrescription.getOnTuesdays());
			storedPrescription.setOnWednesdays(viewedPrescription.getOnWednesdays());
			storedPrescription.setOnThursdays(viewedPrescription.getOnThursdays());
			storedPrescription.setOnFridays(viewedPrescription.getOnFridays());
			storedPrescription.setTherapyStartTime(viewedPrescription.getTherapyStartTime());
			storedPrescription.setTherapyDuration(viewedPrescription.getTherapyDuration());
			prescriptionRepository.save(storedPrescription);
		}
	}
	
	@Transactional
	public void delete(Patient patient, int therapyId) {
		patient.deleteTherapy(findById(therapyId));
		patientService.save(patient);
	}

	@Transactional
	public TherapyPrescription addPrescription(Therapy viewedTherapy, int therapyId) {
		var storedTherapy = therapyRepository.findById(therapyId);
		var newPrescription = new TherapyPrescription();
		var lastPrescription = storedTherapy.getPrescription();

		storedTherapy.setName(viewedTherapy.getName());

		if ( lastPrescription == null || !lastPrescription.getPrescriptionDate().equals(LocalDate.now()) ) {
			newPrescription.setPrescriptionDate(LocalDate.now());
		}
		if (lastPrescription != null) {
			newPrescription.setCount(lastPrescription.getCount());
			newPrescription.setOnMondays(lastPrescription.getOnMondays());
			newPrescription.setOnTuesdays(lastPrescription.getOnTuesdays());
			newPrescription.setOnWednesdays(lastPrescription.getOnWednesdays());
			newPrescription.setOnThursdays(lastPrescription.getOnThursdays());
			newPrescription.setOnFridays(lastPrescription.getOnFridays());
		}
		storedTherapy.addPrescription(newPrescription);
		therapyRepository.save(storedTherapy);
		return newPrescription;
	}
	
	@Transactional
	public Therapy deletePrescription(Patient patient, int therapyId, int prescriptionId) {
		var storedTherapy = therapyRepository.findById(therapyId);
		var storedPrescription = prescriptionRepository.findById(prescriptionId);

		if (storedTherapy != null && storedPrescription != null) {
			for (TherapyAppointment appointment : storedTherapy.getAppointments()) {
				if (appointment.getPrescription() == storedPrescription) {
					storedTherapy.removeAppointment(appointment);
				}
			}
		}
		storedTherapy.removePrescription(storedPrescription);
		therapyRepository.save(storedTherapy);
		return storedTherapy;
	}

	@Transactional(readOnly = true)
	public TherapyAppointment findAppointemtnById(int appointmentId) {
		return appointmentRepository.findById(appointmentId);
	}

	@Transactional(readOnly = true)
	public TherapyPrescription findPrescriptionById(int prescriptionId) {
		return prescriptionRepository.findById(prescriptionId);
	}
	
	@Transactional
	public void addAppointments(Therapy viewedTherapy, int therapyId, TherapyPrescription viewedPrescription, int prescriptionId) {
		var storedTherapy = therapyRepository.findById(therapyId);
		var storedPrescription = prescriptionRepository.findById(prescriptionId);
		
		updateTherapy(viewedTherapy, storedTherapy, viewedPrescription, prescriptionId);

		if (storedTherapy != null) {
			for (TherapyAppointment appointment : storedTherapy.getAppointments()) {

				storedTherapy.getAppointmentsInternal().remove(appointment);
				appointmentRepository.delete(appointment);

				logger.debug("Deleted appointment #" + appointment.getId());
			}

			storedPrescription.addAppointments(storedTherapy).forEach((a) -> {
				this.appointmentRepository.save(a);
			});
			this.therapyRepository.save(storedTherapy);
		}
	}
	
	@Transactional
	public void moveAppointment(int appointmentId, int direction) {
		TherapyAppointment savedAppointment = appointmentRepository.findById(appointmentId);

		savedAppointment.setDate(savedAppointment.getDate().plusDays(direction));
		appointmentRepository.save(savedAppointment);
	
	}

	@Transactional
	public void moveAppointmentToEnd(int prescriptionId, int appointmentId) {
		var storedPrescription = prescriptionRepository.findById(prescriptionId);
		var storedAppointment = appointmentRepository.findById(appointmentId);
		var lastAppointment = storedPrescription.getAppointments().stream().max((a1, a2) -> a1.getDate().compareTo(a2.getDate()));
		var maxDate = lastAppointment.get().getDate();
		var nextDate = storedPrescription.calcNextDate(maxDate);

		storedAppointment.setDate(nextDate);
		storedPrescription.setNextPrescription(storedPrescription.calcNextDate(nextDate));

		appointmentRepository.save(storedAppointment);
		prescriptionRepository.save(storedPrescription);
	}
	
	@Transactional
	public void deleteAppointment(int prescriptionId, int appointmentId) {
		var storedPrescription = prescriptionRepository.findById(prescriptionId);
		var storedAppointment = appointmentRepository.findById(appointmentId);

		storedPrescription.getAppointmentsInternal().remove(storedAppointment);
		prescriptionRepository.save(storedPrescription);
		storedAppointment.setPrescription(null);
		appointmentRepository.delete(storedAppointment);
	}

	@Transactional
	public void editAppointment(TherapyAppointment viewedAppointment, int appointmentId) {
		var storedAppointment = appointmentRepository.findById(appointmentId);

		storedAppointment.setDate(viewedAppointment.getDate());
		appointmentRepository.save(storedAppointment);
	}



}
