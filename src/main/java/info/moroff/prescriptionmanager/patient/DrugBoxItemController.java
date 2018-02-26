package info.moroff.prescriptionmanager.patient;

import java.util.Optional;

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

@Controller
@RequestMapping("/patients/{patientId}")
class DrugBoxItemController {

	private static final String VIEWS_DRUGS_CREATE_OR_UPDATE_FORM = "patients/createOrUpdateDrugsForm";
	private static final String VIEWS_DRUGS_DETAILS_FORM = "patients/drugsDetails";
	private final PatientRepository patients;
	private DrugBoxItemRepository drugBoxItems;

	@Autowired
	public DrugBoxItemController(PatientRepository patients, DrugBoxItemRepository drugBoxItems) {
		this.patients = patients;
		this.drugBoxItems = drugBoxItems;
	}

	@ModelAttribute("patient")
	public Patient findPatient(@PathVariable("patientId") int patientId) {
		return this.patients.findById(patientId);
	}

	@InitBinder("patient")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	// @RequestMapping(value = "/drugs/{drugId}/api", method =
	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)

	@RequestMapping(value = "/drugs/{drugId}/view", method = RequestMethod.GET)
	public String viewPatientDetailsForm(Patient patient, @PathVariable("drugId") int drugId, Model model) {
		Optional<DrugBoxItem> drugBoxItem = patient.getDrugBoxItemsInternal().stream().filter(d -> d.getId() == drugId)
				.findFirst();
		model.addAttribute("patient", patient);
		model.addAttribute("drugBoxItem", drugBoxItem.get());
		return VIEWS_DRUGS_DETAILS_FORM;
	}

	@RequestMapping(value = "/drugs/{drugId}/edit", method = RequestMethod.GET)
	public String editPatientDetailsForm(Patient patient, @PathVariable("drugId") int drugId, Model model) {
		DrugBoxItem drugBoxItem = drugBoxItems.findById(drugId);
		model.addAttribute("drugBoxItem", drugBoxItem);
		return VIEWS_DRUGS_CREATE_OR_UPDATE_FORM;
	}

	// @RequestMapping(value = "/drugs/{drugId}/api", method =
	// RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces
	// = MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<DrugBoxItem> updateOffer(@Validated @RequestBody
	// DrugBoxItem drugBoxItem, Patient patient, HttpServletResponse response)
	// throws IOException {
	// patient.addDrugBoxItem(drugBoxItem);
	// this.drugBoxItems.save(drugBoxItem);
	// return new ResponseEntity<DrugBoxItem>(drugBoxItem, HttpStatus.OK);
	// }

	@RequestMapping(value = "/drugs/{drugId}/edit", method = RequestMethod.POST)
	public String processUpdateForm(@PathVariable("drugId") int drugId, DrugBoxItem drugBoxItem, BindingResult result,
			Patient patient, ModelMap model) {
		if (result.hasErrors()) {
			return VIEWS_DRUGS_CREATE_OR_UPDATE_FORM;
		} else {
			DrugBoxItem stored = this.drugBoxItems.findById(drugId);
			if (stored != null) {
				stored.setDaylyIntake(drugBoxItem.getDaylyIntake());
				stored.setInventoryAmount(drugBoxItem.getInventoryAmount());
				stored.setInventoryDate(drugBoxItem.getInventoryDate());
				this.drugBoxItems.save(stored);
			}
			else {
				patient.addDrugBoxItem(drugBoxItem);
				this.drugBoxItems.save(drugBoxItem);				
			}
			return "redirect:/patients/{patientId}";
		}
	}

}
