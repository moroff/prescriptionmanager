package info.moroff.prescriptionmanager.model;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

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
