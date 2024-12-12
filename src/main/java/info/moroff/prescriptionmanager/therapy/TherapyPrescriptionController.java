package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.util.Map;

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
import info.moroff.prescriptionmanager.patient.PatientService;
import info.moroff.prescriptionmanager.ui.UITools;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/patients/{patientId}")
public class TherapyPrescriptionController {
	private final TherapyService therapyService;
	private final PatientService patientService;

	@Autowired
	private TherapyViewState viewState;

	@Autowired
	private UITools uiTools;

	@Autowired
	public TherapyPrescriptionController(PatientService patientService, TherapyService therapyService) {
		this.therapyService = therapyService;
		this.patientService = patientService; 
	}

	@ModelAttribute("patient")
	public Patient findPatient(@PathVariable("patientId") int patientId) {
		return patientService.findById(patientId);
	}

	@InitBinder("patient")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/therapy/new", method = RequestMethod.GET)
	public String initUpdatePatientForm(@PathVariable("patientId") int patientId, Map<String, Object> model) {
		Therapy therapy = new Therapy();
		TherapyPrescription prescription = new TherapyPrescription();

		therapy.addPrescription(prescription);
		prescription.setPrescriptionDate(LocalDate.now());
		prescription.setFirstTherapyDate(LocalDate.now());

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
			therapyService.add(patient, therapy, prescription);
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapy.getId() + "/edit";

		}
	}

	@RequestMapping(value = "/therapies/{therapyId}/delete", method = RequestMethod.GET)
	public String initDelete(@PathVariable("therapyId") int therapyId, Patient patient, Model model) {
		Therapy therapy = therapyService.findById(therapyId);
		model.addAttribute("therapy", therapy);
		return "patients/therapyDelete";
	}
	
	@RequestMapping(value = "/therapies/{therapyId}/delete", method = RequestMethod.POST)
	public String processDelete(@PathVariable("therapyId") int therapyId, Patient patient) {
		therapyService.delete(patient, therapyId);
		return "redirect:/patients/" + patient.getId();
	}
	
	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.GET)
	public String editPatientDetailsForm(@PathVariable("therapyId") int therapyId, Model model) {
		Therapy therapy = therapyService.findById(therapyId);
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
			therapyService.updateTherapy(therapy, therapyId, prescription, viewState.getPrescriptionId());
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}

	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.POST, params = { "addPrescription" })
	public String processUpdateFormAddPrescription(@PathVariable("therapyId") int therapyId, @Valid Therapy therapy,
			BindingResult result, Patient patient, ModelMap model) {
		if (result.hasErrors()) {
			return "patients/createOrUpdateTherapyForm";
		} else {
			var prescription = therapyService.addPrescription(therapy, therapyId);
			viewState.setPrescriptionId(prescription.getId());
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
			therapyService.addAppointments(therapy, therapyId, prescription, therapyId);
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}

	@RequestMapping(value = "/therapies/{therapyId}/edit", method = RequestMethod.POST, params = { "deletePrescription" })
	public String processUpdateFormDeleteAppointments(@PathVariable("therapyId") int therapyId, Patient patient, ModelMap model) {
		Therapy stored = therapyService.deletePrescription(patient, therapyId, viewState.getPrescriptionId());
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
		therapyService.moveAppointment(appointmentId, direction);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/moveToEnd", method = RequestMethod.GET)
	public String processAppointmentMoveToEnd(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			Patient patient) {
		therapyService.moveAppointmentToEnd(prescriptionId, appointmentId);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/delete", method = RequestMethod.GET)
	public String processAppointmentDelete(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			Patient patient) {
		therapyService.deleteAppointment(prescriptionId, appointmentId);
		return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
	}

	@RequestMapping(value = "/therapies/{therapyId}/prescriptions/{prescriptionId}/appointments/{appointmentId}/edit", method = RequestMethod.GET)
	public String processAppointmentEdit(@PathVariable("therapyId") int therapyId,
			@PathVariable("prescriptionId") int prescriptionId, @PathVariable("appointmentId") int appointmentId, //
			Patient patient, ModelMap model) {

		model.addAttribute("therapy", therapyService.findById(therapyId));
		model.addAttribute("prescription", therapyService.findPrescriptionById(prescriptionId));
		model.addAttribute("appointment", therapyService.findAppointemtnById(appointmentId));
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
			therapyService.editAppointment(appointment, appointmentId);
			return "redirect:/patients/" + patient.getId() + "/therapies/" + therapyId + "/edit";
		}
	}

}
