package info.moroff.prescriptionmanager.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import info.moroff.prescriptionmanager.patient.Patient;

/**
 * Abstract base model class for all kind of prescriptions.
 * 
 * @author dieter
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Prescription extends BaseEntity {

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="patient_id", updatable=false, nullable=false)
	private Patient patient;
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}
