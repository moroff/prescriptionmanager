package info.moroff.prescriptionmanager.therapy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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

import info.moroff.prescriptionmanager.model.Periodicity;
import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.patient.PatientRepository;
import info.moroff.prescriptionmanager.ui.UITools;

@Controller
@RequestMapping("/patients/{patientId}")
public class TherapyPrescriptionController {
	private Logger logger = Logger.getLogger(getClass());
	private final PatientRepository patientRepository;
	private TherapyPrescriptionRepository prescriptionRepository;
	private TherapyAppointmentRepository appointmentRepository;

    @Autowired
    public TherapyPrescriptionController(PatientRepository patientRepository, TherapyPrescriptionRepository prescriptionRepository, TherapyAppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
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

    @RequestMapping(value = "/prescriptions/new", method = RequestMethod.GET)
    public String initUpdatePatientForm(@PathVariable("patientId") int patientId, Map<String, Object> model) {
        TherapyPrescription therapyPrescription = new TherapyPrescription();
        
        therapyPrescription.setCount(10);
        therapyPrescription.setPrescriptionDate(LocalDate.now());
        therapyPrescription.setPeriodicity(Periodicity.WEEKLY);
        
        model.put("therapyPrescription", therapyPrescription);
        model.put("uitools", uiTools);

        return "patients/createOrUpdateTherapyForm";
    }

    @RequestMapping(value = "/prescriptions/new", method = RequestMethod.POST)
	public String processNewForm(@Valid TherapyPrescription therapy, BindingResult result, Patient patient, ModelMap model) {
        if (result.hasErrors()) {
            return "patients/createOrUpdateTherapyForm";
        } else {
        	therapy.setPatient(patient);
        	LocalDate nextTherapyDate = therapy.getFirstTherapyDate();
        	for (int i = 0; i < therapy.getCount(); i++) {
				nextTherapyDate = calcNextDate(therapy, nextTherapyDate);
			}
        	therapy.setNextPrescription(nextTherapyDate);
            this.prescriptionRepository.save(therapy);
            return "redirect:/patients/"+patient.getId();
        }
    }

    @RequestMapping(value = "/prescriptions/{therapyId}/edit", method = RequestMethod.GET)
    public String editPatientDetailsForm(@PathVariable("therapyId")int therapyId, Model model) {
        model.addAttribute(prescriptionRepository.findById(therapyId));
        return "patients/createOrUpdateTherapyForm";
    }

    @RequestMapping(value = "/prescriptions/{therapyId}/edit", method = RequestMethod.POST, params={"save"})
	public String processUpdateForm(@PathVariable("therapyId") int therapyId, @Valid TherapyPrescription therapy, BindingResult result, Patient patient, ModelMap model) {
        if (result.hasErrors()) {
            return "patients/createOrUpdateTherapyForm";
        } else {
        	TherapyPrescription stored = prescriptionRepository.findById(therapyId);
        	
        	if ( stored != null ) {
        		stored.setName(therapy.getName());
        		stored.setFirstTherapyDate(therapy.getFirstTherapyDate());
        		stored.setPrescriptionDate(therapy.getPrescriptionDate());
        		stored.setCount(therapy.getCount());
        		stored.setOnMondays(therapy.getOnMondays());
        		stored.setOnTuesdays(therapy.getOnTuesdays());
        		stored.setOnWednesdays(therapy.getOnWednesdays());
        		stored.setOnThursdays(therapy.getOnThursdays());
        		stored.setOnFridays(therapy.getOnFridays());
        		this.prescriptionRepository.save(stored);
        	}
        	return "redirect:/patients/"+patient.getId();
        }
    }

    @RequestMapping(value = "/prescriptions/{therapyId}/edit", method = RequestMethod.POST, params={"addAppointments"})
	public String processUpdateFormAddAppointments(@PathVariable("therapyId") int therapyId, @Valid TherapyPrescription therapy, BindingResult result, Patient patient, ModelMap model) {
        if (result.hasErrors()) {
            return "patients/createOrUpdateTherapyForm";
        } else {
        	TherapyPrescription stored = prescriptionRepository.findById(therapyId);
        	
        	if ( stored != null ) {
        		stored.setName(therapy.getName());
        		stored.setFirstTherapyDate(therapy.getFirstTherapyDate());
        		stored.setPrescriptionDate(therapy.getPrescriptionDate());
        		stored.setCount(therapy.getCount());
        		stored.setOnMondays(therapy.getOnMondays());
        		stored.setOnTuesdays(therapy.getOnTuesdays());
        		stored.setOnWednesdays(therapy.getOnWednesdays());
        		stored.setOnThursdays(therapy.getOnThursdays());
        		stored.setOnFridays(therapy.getOnFridays());
        		
        		for (TherapyAppointment appointment : stored.getAppointments()) {
        			stored.getAppointmentsInternal().remove(appointment);
        			appointmentRepository.delete(appointment);
        			
        			logger.debug("Deleted appointment #"+appointment.getId());
				}
        		
        		LocalDate therapyDate = therapy.getFirstTherapyDate();
        		for (int i = 0; i < stored.getCount(); i++) {
					TherapyAppointment appointment = new TherapyAppointment();
					
					appointment.setDate(therapyDate);
					appointment.setPrescription(stored);
					appointmentRepository.save(appointment);
					therapyDate = calcNextDate(therapy, therapyDate);
				}
        		this.prescriptionRepository.save(stored);
        	}
        	return "redirect:/patients/"+patient.getId()+"/prescriptions/"+therapyId+"/edit";
        }
    }

    @RequestMapping(value = "/prescriptions/{therapyId}/edit", method = RequestMethod.POST, params={"deleteAppointments"})
	public String processUpdateFormDeleteAppointments(@PathVariable("therapyId") int therapyId, @Valid TherapyPrescription therapy, BindingResult result, Patient patient, ModelMap model) {
        if (result.hasErrors()) {
            return "patients/createOrUpdateTherapyForm";
        } else {
        	TherapyPrescription stored = prescriptionRepository.findById(therapyId);
        	
        	if ( stored != null ) {
        		for (TherapyAppointment appointment : stored.getAppointments()) {
        			stored.removeAppointment(appointment);
				}
        	}
        	prescriptionRepository.save(stored);
        	return "redirect:/patients/"+patient.getId()+"/prescriptions/"+therapyId+"/edit";
        }
    }
    
    @RequestMapping(value = "/prescriptions/{prescriptionId}/appointments/{appointmentId}/move", method = RequestMethod.GET)    
    public String processAppointmentMove(@PathVariable("prescriptionId")int prescriptionId, @PathVariable("appointmentId")int appointmentId, //
    		@RequestParam(name="direction", value="direction", required=true)int direction, //
    		Patient patient) {
    	TherapyAppointment appointment = appointmentRepository.findById(appointmentId);
    	
    	appointment.setDate(appointment.getDate().plusDays(direction));
    	appointmentRepository.save(appointment);
    	return "redirect:/patients/"+patient.getId()+"/prescriptions/"+prescriptionId+"/edit";
    }
    
    @RequestMapping(value = "/prescriptions/{prescriptionId}/appointments/{appointmentId}/moveToEnd", method = RequestMethod.GET)    
    public String processAppointmentMoveToEnd(@PathVariable("prescriptionId")int prescriptionId, @PathVariable("appointmentId")int appointmentId, //
    		Patient patient) {

    	TherapyPrescription prescription = prescriptionRepository.findById(prescriptionId);
    	TherapyAppointment appointment = appointmentRepository.findById(appointmentId);
    	
    	Optional<TherapyAppointment> max = prescription.getAppointments().stream().max((a1,a2)->a1.getDate().compareTo(a2.getDate()));
    	LocalDate maxDate = max.get().getDate();
    	appointment.setDate(calcNextDate(prescription, maxDate));
    	prescription.setNextPrescription(calcNextDate(prescription, appointment.getDate()));

    	appointmentRepository.save(appointment);
    	prescriptionRepository.save(prescription);
    	return "redirect:/patients/"+patient.getId()+"/prescriptions/"+prescriptionId+"/edit";
    }

    LocalDate calcNextDate(TherapyPrescription therapy, LocalDate startDate) {
    	
    	Periodicity periodicity = therapy.getPeriodicity();
    	
    	if ( periodicity == null ) {
    		periodicity = Periodicity.WEEKLY;
    	}
    	
    	
    	
    	switch (periodicity) {
		case DAILY:
			return startDate.plusDays(1);
		case WEEKLY:
			return therapy.getNextWeekDay(startDate);
		case MONTHLY:
			return startDate.plusMonths(1);
		case QUARTER:
			return startDate.plusMonths(3);
		case YEARLY:
			return startDate.plusYears(1);
		default:
			throw new IllegalArgumentException(periodicity.name());
		}
    }
    
}

