package info.moroff.prescriptionmanager.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import info.moroff.prescriptionmanager.patient.DrugBoxItem;
import info.moroff.prescriptionmanager.patient.DrugBoxItemRepository;
import info.moroff.prescriptionmanager.patient.Patient;
import info.moroff.prescriptionmanager.patient.PatientInfo;
import info.moroff.prescriptionmanager.patient.PatientRepository;
import info.moroff.prescriptionmanager.patient.Patients;

@RestController
@RequestMapping("/api")
public class ApiRestController {

	private final PatientRepository patients;
	private DrugBoxItemRepository drugBoxItems;

	@Autowired
	public ApiRestController(//
			PatientRepository patients,//
			DrugBoxItemRepository drugBoxItems) {
		this.patients = patients;
		this.drugBoxItems = drugBoxItems;
	}
	
    @GetMapping(value = { "/patients.json" })
    @CrossOrigin(origins = {
    		"http://localhost:4200",
    		"https://www.schoenfisch-moroff.de"
    })
    public @ResponseBody List<PatientInfo> showPatientList() {
        // Here we are returning an object of type 'Patients' rather than a collection of Patient
        // objects so it is simpler for JSon/Object mapping
        Patients patients = new Patients();
        patients.addAll(this.patients.findAll());
        return patients.getPatientList();
    }
	
    @GetMapping(value = "/patients/{patientId}")
    @CrossOrigin(origins = {
    		"http://localhost:4200",
    		"https://www.schoenfisch-moroff.de"
    })
    public @ResponseBody Patient showPatientDetails(@PathVariable("patientId") int patientId) {
    	return this.patients.findById(patientId);
    }
    
	@PutMapping(value = "/patients/{patientId}/drugs/{drugId}")
	@ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = {
    		"http://localhost:4200",
    		"https://www.schoenfisch-moroff.de"
    })
	public Patient updateDrugBoxItem(@PathVariable("patientId") int patientId, @PathVariable("drugId") int drugId, @RequestBody DrugBoxItem drugBoxItem) {
		DrugBoxItem stored = this.drugBoxItems.findById(drugId);
		if (stored != null) {
			stored.setDaylyIntake(drugBoxItem.getDaylyIntake());
			stored.setInventoryAmount(drugBoxItem.getInventoryAmount());
			stored.setInventoryDate(drugBoxItem.getInventoryDate());
			this.drugBoxItems.save(stored);
		}
    	return this.patients.findById(patientId);
	}
    
    
}
