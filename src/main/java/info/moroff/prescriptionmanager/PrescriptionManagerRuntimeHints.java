package info.moroff.prescriptionmanager;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

import info.moroff.prescriptionmanager.drug.Drug;
import info.moroff.prescriptionmanager.patient.Patient;

public class PrescriptionManagerRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.resources().registerPattern("db/*"); 
		hints.resources().registerPattern("messages/*");
		hints.serialization().registerType(Drug.class);
		hints.serialization().registerType(Patient.class);
	}

}
