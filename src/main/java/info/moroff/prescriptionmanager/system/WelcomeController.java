package info.moroff.prescriptionmanager.system;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class WelcomeController {

    @RequestMapping("/home")
    public String welcome() {
        return "welcome";
    }
}
