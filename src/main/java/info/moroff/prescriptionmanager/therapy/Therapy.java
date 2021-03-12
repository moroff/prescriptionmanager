package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.moroff.prescriptionmanager.model.BaseEntity;
import info.moroff.prescriptionmanager.patient.Patient;

@SuppressWarnings("serial")
@Entity
@Table(name = "therapy")
public class Therapy extends BaseEntity {

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="patient_id", updatable=false, nullable=false)
	@JsonIgnore
	private Patient patient;
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	/**
	 * Prescription name.
	 */
	String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "therapy", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<TherapyPrescription> prescriptions;

	protected Set<TherapyPrescription> getPrescriptionsInternal() {
		if (this.prescriptions == null) {
			this.prescriptions = new HashSet<>();
		}
		return this.prescriptions;
	}

	public List<TherapyPrescription> getPrescriptions() {
		List<TherapyPrescription> sorted = new ArrayList<>(getPrescriptionsInternal());
		PropertyComparator.sort(sorted, new MutableSortDefinition("prescriptionDate", true, true));
		return Collections.unmodifiableList(sorted);
	}

	public void addPrescription(TherapyPrescription prescription) {
		if (prescription.isNew()) {
			getPrescriptionsInternal().add(prescription);
		}
		prescription.setTherapy(this);
	}

	public void removePrescription(TherapyPrescription prescription) {
		getPrescriptionsInternal().remove(prescription);
	}

	public TherapyPrescription getPrescription() {
		if ( getPrescriptionsInternal().isEmpty() ) {
			return null;
		}
		else {
			return getPrescriptions().get(getPrescriptionsInternal().size()-1);
		}
			
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "therapy", fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonIgnore
	private Set<TherapyAppointment> appointments;

	protected Set<TherapyAppointment> getAppointmentsInternal() {
		if (this.appointments == null) {
			this.appointments = new HashSet<>();
		}
		return this.appointments;
	}

	public List<TherapyAppointment> getAppointments() {
		List<TherapyAppointment> sorted = new ArrayList<>(getAppointmentsInternal());
		PropertyComparator.sort(sorted, new MutableSortDefinition("date", true, true));
		return Collections.unmodifiableList(sorted);
	}

	public void addAppointment(TherapyAppointment appointment) {
		if (appointment.isNew()) {
			getAppointments().add(appointment);
		}
		appointment.setTherapy(this);
	}

	public void removeAppointment(TherapyAppointment appointment) {
		getAppointmentsInternal().remove(appointment);
	}

    public LocalDate getLastAppointmentDate() {
		if ( getPrescriptionsInternal().size() > 0) {
			List<TherapyPrescription> prescriptions = getPrescriptions();
    		TherapyPrescription lastPrescription = prescriptions.get(prescriptions.size()-1);
    		List<TherapyAppointment> appointments = lastPrescription.getAppointments();
    		
    		if ( lastPrescription.getPrescriptionDate() == null && appointments.size() > 0 ) {
    			return appointments.get(0).getDate();
    		}
    		else if ( lastPrescription.getPrescriptionDate() != null && appointments.size() > 0) {
    			TherapyAppointment lastAppointment = appointments.get(appointments.size()-1);
    				
   				return lastAppointment.getDate();
    		}

		} 
		return null;
    }
}
