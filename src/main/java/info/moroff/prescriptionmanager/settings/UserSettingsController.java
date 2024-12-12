package info.moroff.prescriptionmanager.settings;

import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import info.moroff.prescriptionmanager.ui.UITools;

@Controller
public class UserSettingsController {


    @Autowired
    public UserSettingsController() {
    }

    @Autowired
    private UITools uiTools;
    
    @Autowired
    private UserSettings storedUserSettings;
    
    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String viewPatientDetailsForm(Map<String, Object>  model) {
        model.put("uitools", uiTools);
        model.put("userSettings", storedUserSettings);
        return "userSettingsForm";
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public String processUpdatePatientForm(@Valid UserSettings userSetting, BindingResult result) {
        if (result.hasErrors()) {
            return "userSettingsForm";
        } else {
            storedUserSettings.setCreateNewTherapy(userSetting.getCreateNewTherapy());
            storedUserSettings.setShowInactiveDrugs(userSetting.getShowInactiveDrugs());
            storedUserSettings.setDeleteTherapy(userSetting.getDeleteTherapy());
            storedUserSettings.setHidePatients(userSetting.getHidePatients());
            return "redirect:/";
        }
    }

}
