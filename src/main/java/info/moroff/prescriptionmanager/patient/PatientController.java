package info.moroff.prescriptionmanager.patient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import info.moroff.prescriptionmanager.settings.UserSettings;
import info.moroff.prescriptionmanager.ui.UITools;

@Controller
class PatientController {

    private static final String VIEWS_PATIENT_CREATE_OR_UPDATE_FORM = "patients/createOrUpdatePatientForm";
	private static final String VIEWS_PATIENT_DETAILS_FORM = "patients/patientDetails";
	private final PatientRepository patients;

    @Autowired
    public PatientController(PatientRepository clinicService) {
        this.patients = clinicService;
    }

    @Autowired
    private UITools uiTools;
    
    @Autowired
    private UserSettings userSettings;

    @RequestMapping(value = { "/" })
    public String showHome(Map<String, Object> model) {
    	return showPatientList(model);
    }
    
    @RequestMapping(value = { "/patients.html" })
    public String showPatientList(Map<String, Object> model) {
        // Here we are returning an object of type 'Patients' rather than a collection of Patient
        // objects so it is simpler for Object-Xml mapping
        model.put("patients", showResourcesPatientList(userSettings.getHidePatients()));
        model.put("uitools", uiTools);
        return "patients/patientList";
    }

    public @ResponseBody List<PatientInfo> showResourcesPatientList(boolean showHiddenPatients) {
        // Here we are returning an object of type 'Patients' rather than a collection of Patient
        // objects so it is simpler for JSon/Object mapping
        Patients patients = new Patients();
        patients.addAll(this.patients.findAll().stream().filter(p -> showHiddenPatients || !p.getHide()).sorted((p1,p2) -> p1.getId().compareTo(p2.getId())).collect(Collectors.toList()));
        return patients.getPatientList();
    }
    
    @RequestMapping(value = "/patients/{patientId}", method = RequestMethod.GET)
    public String viewPatientDetailsForm(@PathVariable("patientId") int patientId, Map<String, Object>  model) {
        Patient patient = this.patients.findById(patientId);
        model.put("patient", patient);
        model.put("uitools", uiTools);
        model.put("userSettings", userSettings);
        return VIEWS_PATIENT_DETAILS_FORM;
    }

    @RequestMapping(value = "/patients/{patientId}/edit", method = RequestMethod.GET)
    public String initUpdatePatientForm(@PathVariable("patientId") int patientId, Model model) {
        Patient patient = this.patients.findById(patientId);
        model.addAttribute(patient);
        model.addAttribute("userSettings", userSettings);
        return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/patients/{patientId}/edit", method = RequestMethod.POST)
    public String processUpdatePatientForm(@Valid Patient patient, BindingResult result, @PathVariable("patientId") int patientId) {
        if (result.hasErrors()) {
            return VIEWS_PATIENT_CREATE_OR_UPDATE_FORM;
        } else {
            patient.setId(patientId);
            this.patients.save(patient);
            return "redirect:/patients";
        }
    }

    @RequestMapping(value = "/patients/{patientId}/toggleHide", method = RequestMethod.GET)
    public String processToggleHide(@PathVariable("patientId") int patientId, Model model) {
        Patient patient = this.patients.findById(patientId);
        
        if ( !patient.getHide() ) {
        	patient.setHide(Boolean.TRUE);
        }
        else {
        	patient.setHide(Boolean.FALSE);        	
        }
        this.patients.save(patient);
        
        return "redirect:/patients/"+patientId;
    }
}
