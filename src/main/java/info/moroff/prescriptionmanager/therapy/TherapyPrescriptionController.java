package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.patient.PatientRepository;
import info.moroff.prescriptionmanager.ui.UITools;

@Controller
@RequestMapping("/patients/{patientId}")
public class TherapyPrescriptionController {
	private Logger logger = LogManager.getLogger();
	private final PatientRepository patientRepository;
	private TherapyRepository therapyRepository;
	private TherapyPrescriptionRepository prescriptionRepository;
	private TherapyAppointmentRepository appointmentRepository;

	@Autowired
	private TherapyViewState viewState;

	@Autowired
	public TherapyPrescriptionController(PatientRepository patientRepository, TherapyRepository therapyRepository,
			TherapyPrescriptionRepository prescriptionRepository, TherapyAppointmentRepository appointmentRepository) {
		this.patientRepository = patientRepository;
		this.therapyRepository = therapyRepository;
		this.prescriptionRepository = prescriptionRepository;
		this.appointmentRepository = appointmentRepository;
	}

	@ModelAttribute("patient")
	public Patient findPatient(@PathVariable("patientId") int patientId) {
		return this.patientRepository.findById(patientId);
	}

	@InitBinder("patient")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	private UITools uiTools;

	@RequestMapping(value = "/therapy/new", method = RequestMethod.GET)
	public String initUpdatePatientForm(@PathVariable("patientId") int patientId, Map<String, Object> model) {
		Therapy therapy = new Therapy();
		TherapyPrescription prescription = new TherapyPrescription();

		therapy.addPrescription(prescription);
		prescription.setPrescriptionDate(LocalDate.now());

		model.put("therapy", therapy);
		model.put("prescription", prescription);
		model.put("uitools", uiTools);

		return "patients/createOrUpdateTherapyForm";
	}

	@RequestMapping(value = "/therapy/new", method = RequestMethod.POST)
	public String processNewForm(@Valid @ModelAttribute("therapy") Therapy therapy, BindingResult therapyResult,
			@Valid @ModelAttribute("prescription") TherapyPrescription prescription, BindingResult prescriptionResult,
			Patient patient, ModelMap model) {
		if (prescriptionResult.hasErrors()) {
			return "patients/createOrUpdateTherapyForm";
		} else {
			therapy.setPatient(patient);
			therapy.addPrescription(prescription);
			this.therapyRepository.save(therapy);
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapy.getId() + "/edit";

		}
	}

	@RequestMapping(value = "/therapies/{therapyId}/delete", method = RequestMethod.GET)
	public String initDelete(@PathVariable("therapyId") int therapyId, Patient patient, Model model) {
		Therapy therapy = therapyRepository.findById(therapyId);
		model.addAttribute("therapy", therapy);
		return "patients/therapyDelete";
	}
	
	@RequestMapping(value = "/therapies/{therapyId}/delete", method = RequestMethod.POST)
	public String processDelete(@PathVariable("therapyId") int therapyId, Patient patient) {
		Therapy stored = therapyRepository.findById(therapyId);
		patient.deleteTherapy(stored);
		patientRepository.save(patient);
		return "redirect:/patients/" + patient.getId();
	}
	
	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.GET)
	public String editPatientDetailsForm(@PathVariable("therapyId") int therapyId, Model model) {
		Therapy therapy = therapyRepository.findById(therapyId);
		model.addAttribute("therapy", therapy);
		if (viewState.getTherapyId() != therapyId || viewState.getPrescriptionId() == 0) {
			viewState.setPrescriptionId(therapy.getPrescription() != null ? therapy.getPrescription().getId() : 0);
			viewState.setTherapyId(therapyId);
			model.addAttribute("prescription", therapy.getPrescription());
		} else {
			model.addAttribute("prescription", therapy.getPrescriptions().stream()
					.filter(p -> p.getId() == viewState.getPrescriptionId()).findFirst().get());
		}
		model.addAttribute("viewState", viewState);
		return "patients/createOrUpdateTherapyForm";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/edit", method = RequestMethod.GET)
	public String editPatientDetailsFormWithPrescription(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, Patient patient) {
		viewState.setPrescriptionId(prescriptionId);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.POST, params = { "save" })
	public String processUpdateForm(@PathVariable("therapyId") int therapyId, @Valid Therapy therapy, BindingResult resultTherapy, 
			@Valid TherapyPrescription prescription, BindingResult result, Patient patient, ModelMap model) {
		if (result.hasErrors()) {
			return "patients/createOrUpdateTherapyForm";
		} else {
			Therapy stored = therapyRepository.findById(therapyId);
			updateTherapy(therapy, prescription, stored);
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}


	private void updateTherapy(Therapy therapy, TherapyPrescription prescription, Therapy stored) {
		TherapyPrescription storedPrescription;

		if (stored != null) {
			stored.setName(therapy.getName());
			this.therapyRepository.save(stored);
		}

		if (viewState.getPrescriptionId() != 0) {
			storedPrescription = prescriptionRepository.findById(viewState.getPrescriptionId());
		} else {
			storedPrescription = new TherapyPrescription();
			stored.addPrescription(storedPrescription);
		}

		if (storedPrescription != null) {
			storedPrescription.setFirstTherapyDate(prescription.getFirstTherapyDate());
			storedPrescription.setPrescriptionDate(prescription.getPrescriptionDate());
			storedPrescription.setCount(prescription.getCount());
			storedPrescription.setOnMondays(prescription.getOnMondays());
			storedPrescription.setOnTuesdays(prescription.getOnTuesdays());
			storedPrescription.setOnWednesdays(prescription.getOnWednesdays());
			storedPrescription.setOnThursdays(prescription.getOnThursdays());
			storedPrescription.setOnFridays(prescription.getOnFridays());
			storedPrescription.setTherapyStartTime(prescription.getTherapyStartTime());
			storedPrescription.setTherapyDuration(prescription.getTherapyDuration());
			prescriptionRepository.save(storedPrescription);
		}
	}

	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.POST, params = { "addPrescription" })
	public String processUpdateFormAddPrescription(@PathVariable("therapyId") int therapyId, @Valid Therapy therapy,
			BindingResult result, Patient patient, ModelMap model) {
		if (result.hasErrors()) {
			return "patients/createOrUpdateTherapyForm";
		} else {
			Therapy stored = therapyRepository.findById(therapyId);

			if (stored != null) {
				stored.setName(therapy.getName());
				TherapyPrescription prescription = new TherapyPrescription();
				TherapyPrescription lastPrescription = stored.getPrescription();

				if ( lastPrescription == null || !lastPrescription.getPrescriptionDate().equals(LocalDate.now()) ) {
					prescription.setPrescriptionDate(LocalDate.now());
				}
				if (lastPrescription != null) {
					prescription.setCount(lastPrescription.getCount());
					prescription.setOnMondays(lastPrescription.getOnMondays());
					prescription.setOnTuesdays(lastPrescription.getOnTuesdays());
					prescription.setOnWednesdays(lastPrescription.getOnWednesdays());
					prescription.setOnThursdays(lastPrescription.getOnThursdays());
					prescription.setOnFridays(lastPrescription.getOnFridays());
				}
				stored.addPrescription(prescription);
				therapyRepository.save(stored);
				viewState.setPrescriptionId(stored.getPrescription().getId());
			}
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}

	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.POST, params = { "addAppointments" })
	public String processUpdateFormAddAppointments(@PathVariable("therapyId") int therapyId,
			@Valid @ModelAttribute("therapy") Therapy therapy, BindingResult therapyResult,
			@Valid @ModelAttribute("prescription") TherapyPrescription prescription, BindingResult prescriptionResult,
			Patient patient, ModelMap model) {
		if (therapyResult.hasErrors() || prescriptionResult.hasErrors()) {
			return "patients/createOrUpdateTherapyForm";
		} else {
			Therapy stored = therapyRepository.findById(therapyId);
			updateTherapy(therapy, prescription, stored);

			if (stored != null) {
				for (TherapyAppointment appointment : stored.getAppointments()) {

					stored.getAppointmentsInternal().remove(appointment);
					appointmentRepository.delete(appointment);

					logger.debug("Deleted appointment #" + appointment.getId());
				}

				getViewedPrescription().addAppointments(stored).forEach((a) -> {
					this.appointmentRepository.save(a);
				});
				this.therapyRepository.save(stored);
			}
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}


	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.POST, params = { "deletePrescription" })
	public String processUpdateFormDeleteAppointments(@PathVariable("therapyId") int therapyId, Patient patient, ModelMap model) {
		Therapy stored = therapyRepository.findById(therapyId);
		TherapyPrescription storedPrescription = prescriptionRepository.findById(viewState.getPrescriptionId());

		if (stored != null && storedPrescription != null) {
			for (TherapyAppointment appointment : stored.getAppointments()) {
				if (appointment.getPrescription() == storedPrescription) {
					stored.removeAppointment(appointment);
				}
			}
		}
		stored.removePrescription(storedPrescription);
		therapyRepository.save(stored);
		if ( stored.getPrescription() != null ) {
			viewState.setPrescriptionId(stored.getPrescription().getId());
		}
		else {
			viewState.setPrescriptionId(0);
		}
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/appointments/{appointmentId}/move", method = RequestMethod.GET)
	public String processAppointmentMove(@PathVariable("therapyId") int therapyId,
			@PathVariable("appointmentId") int appointmentId, //
			@RequestParam(name = "direction", value = "direction", required = true) int direction, //
			Patient patient) {
		TherapyAppointment appointment = appointmentRepository.findById(appointmentId);

		appointment.setDate(appointment.getDate().plusDays(direction));
		appointmentRepository.save(appointment);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/moveToEnd", method = RequestMethod.GET)
	public String processAppointmentMoveToEnd(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			Patient patient) {

		TherapyPrescription prescription = prescriptionRepository.findById(prescriptionId);
		TherapyAppointment appointment = appointmentRepository.findById(appointmentId);

		Optional<TherapyAppointment> max = prescription.getAppointments().stream()
				.max((a1, a2) -> a1.getDate().compareTo(a2.getDate()));
		LocalDate maxDate = max.get().getDate();
		appointment.setDate(prescription.calcNextDate(maxDate));
		prescription.setNextPrescription(prescription.calcNextDate(appointment.getDate()));

		appointmentRepository.save(appointment);
		prescriptionRepository.save(prescription);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/delete", method = RequestMethod.GET)
	public String processAppointmentDelete(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			Patient patient) {

		TherapyPrescription prescription = prescriptionRepository.findById(prescriptionId);
		TherapyAppointment appointment = appointmentRepository.findById(appointmentId);

		prescription.getAppointmentsInternal().remove(appointment);
		prescriptionRepository.save(prescription);
		appointment.setPrescription(null);
		appointmentRepository.delete(appointment);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/edit", method = RequestMethod.GET)
	public String processAppointmentEdit(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			Patient patient, ModelMap model) {

		model.addAttribute("therapy", therapyRepository.findById(therapyId));
		model.addAttribute("prescription", prescriptionRepository.findById(prescriptionId));
		model.addAttribute("appointment", appointmentRepository.findById(appointmentId));
		return "patients/editAppointmentForm";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/edit", method = RequestMethod.POST)
	public String processAppointmentUpdate(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			@Valid @ModelAttribute("appointment") TherapyAppointment appointment, BindingResult appointmentResult, //
			Patient patient) {
		if ( appointmentResult.hasErrors() ) {
			return "patients/editAppointmentForm";
		}
		else {
			TherapyAppointment stored = appointmentRepository.findById(appointmentId);
			
			stored.setDate(appointment.getDate());
			appointmentRepository.save(stored);
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}

	TherapyPrescription getViewedPrescription() {
		return prescriptionRepository.findById(viewState.getPrescriptionId());
	}
}
