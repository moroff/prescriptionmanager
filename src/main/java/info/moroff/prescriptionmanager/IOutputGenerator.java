package info.moroff.prescriptionmanager;

import info.moroff.prescriptionmanager.patient.Patient;

public interface IOutputGenerator {

	String generateOutput(Patient patient);

}