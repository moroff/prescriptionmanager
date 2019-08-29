package info.moroff.prescriptionmanager;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import info.moroff.prescriptionmanager.settings.UserSettings;
import info.moroff.prescriptionmanager.therapy.TherapyViewState;
import info.moroff.prescriptionmanager.ui.UITools;

@SpringBootApplication
public class PrescriptionManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrescriptionManagerApplication.class, args);
	}
	
	@Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.GERMAN);
	    return slr;
	}
	
	@Bean
	public UITools uiTools() {
		return new UITools();
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) 
	public TherapyViewState therapyViewState() { 
		return new TherapyViewState();
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) 
	public UserSettings userSettings() {
		return new UserSettings();
	}
}
