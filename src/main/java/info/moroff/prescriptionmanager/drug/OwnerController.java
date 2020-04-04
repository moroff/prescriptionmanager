package info.moroff.prescriptionmanager.drug;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 */
@Controller
class DrugController {

    private static final String VIEWS_DRUG_CREATE_OR_UPDATE_FORM = "drugs/createOrUpdateDrugForm";
    private final DrugRepository repository;


    @Autowired
    public DrugController(DrugRepository repository) {
        this.repository = repository;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/drugs/new", method = RequestMethod.GET)
    public String initCreationForm(@RequestParam(name="patientId", required=false) Integer patientId, Map<String, Object> model) {
        Drug drug = new Drug();
        if( patientId != null ) {
        	model.put("patientId", patientId);
        }
        model.put("drug", drug);
        return VIEWS_DRUG_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/drugs/new", method = RequestMethod.POST)
    public String processCreationForm(@RequestParam(name="patientId", required=false) Integer patientId, @Valid Drug drug, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_DRUG_CREATE_OR_UPDATE_FORM;
        } else {
            this.repository.save(drug);
            if ( patientId != null ) {
            	return "redirect:/patients/" + patientId + "/drugs/new/" + drug.getId();
            }
            else {
            	return "redirect:/drugs/" + drug.getId();
            }
        }
    }

    @RequestMapping(value = "/drugs/find", method = RequestMethod.GET)
    public String initFindForm(@RequestParam(name="patientId", required=false) Integer patientId, Map<String, Object> model) {
        model.put("drug", new Drug());
        if( patientId != null ) {
        	model.put("patientId", patientId);
        }
        return "drugs/findDrugs";
    }

    @RequestMapping(value = "/drugs", method = RequestMethod.GET)
    public String processFindForm(Drug drug, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /drugs to return all records
        if (drug.getName() == null) {
            drug.setName(""); // empty string signifies broadest possible search
        }

        // find drugs by last name
        Collection<Drug> results = this.repository.findByName(drug.getName());
        if (results.isEmpty()) {
            // no drugs found
            result.rejectValue("name", "notFound", "not found");
            return "drugs/findDrugs";
        } else if (results.size() == 1) {
            // 1 drug found
            drug = results.iterator().next();
            return "redirect:/drugs/" + drug.getId();
        } else {
            // multiple drugs found
            model.put("selections", results);
            return "drugs/drugsList";
        }
    }

    @RequestMapping(value = "/drugs/{drugId}/edit", method = RequestMethod.GET)
    public String initUpdateDrugForm(@PathVariable("drugId") int drugId, Model model) {
        Drug drug = this.repository.findById(drugId);
        model.addAttribute(drug);
        return VIEWS_DRUG_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping(value = "/drugs/{drugId}/edit", method = RequestMethod.POST)
    public String processUpdateDrugForm(@Valid Drug drug, BindingResult result, @PathVariable("drugId") int drugId) {
        if (result.hasErrors()) {
            return VIEWS_DRUG_CREATE_OR_UPDATE_FORM;
        } else {
            drug.setId(drugId);
            this.repository.save(drug);
            return "redirect:/drugs/{drugId}";
        }
    }

    /**
     * Custom handler for displaying an drug.
     *
     * @param drugId the ID of the drug to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/drugs/{drugId}")
    public ModelAndView showDrug(@PathVariable("drugId") int drugId) {
        ModelAndView mav = new ModelAndView("drugs/drugDetails");
        mav.addObject(this.repository.findById(drugId));
        return mav;
    }

}
