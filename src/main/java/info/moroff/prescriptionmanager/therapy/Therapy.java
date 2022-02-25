package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
			Optional<TherapyPrescription> lastPrescription = getLastPrescriptionWithAppointments();

			if ( lastPrescription.isPresent() ) {
				List<TherapyAppointment> appointments = lastPrescription.get().getAppointments();
				return appointments.get(appointments.size()-1).getDate();
			}
			else {
				return null;
			}
		} 
		return null;
    }
    
    public String getFutureAppointments() {
    	List<TherapyAppointment> futureAppointments = getPrescriptionsInternal().stream() //
    			.flatMap((p) -> p.getAppointments().stream())//
    			.filter((a) -> a.getDate().isAfter(LocalDate.now()))//
    			.collect(Collectors.toList());
    	
		if ( futureAppointments.isEmpty() ) {
			return "keine";
		}
		else {
			return Integer.toString(futureAppointments.size());
		}
    }
    
	protected Optional<TherapyPrescription> getLastPrescriptionWithAppointments() {
		List<TherapyPrescription> prescriptions = getPrescriptions();
		Optional<TherapyPrescription> lastPrescription = prescriptions.stream() //
			.filter((p) -> !p.getAppointments().isEmpty()) //
			.sorted((p1,p2) -> p2.getFirstTherapyDate().compareTo(p1.getFirstTherapyDate()))
			.findFirst();
		return lastPrescription;
	}
}
