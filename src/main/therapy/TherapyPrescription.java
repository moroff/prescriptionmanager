package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import info.moroff.prescriptionmanager.model.Periodicity;
import info.moroff.prescriptionmanager.model.Prescription;
import info.moroff.prescriptionmanager.patient.DrugBoxItem;

/**
 * A therapy prescription
 * @author dieter
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "therapy_prescription")
public class TherapyPrescription extends Prescription {

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
	
	/**
	 * Therapy counts on this prescription;
	 */
	Integer count;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	@Column(columnDefinition="INTEGER")
	Periodicity periodicity;
	public Periodicity getPeriodicity() {
		return periodicity;
	}
	public void setPeriodicity(Periodicity periodicity) {
		this.periodicity = periodicity;
	}
	
	Boolean weekDays[] = new Boolean[6];
	
	/**
	 * Issue date of the prescription.
	 */
	@Column(columnDefinition="DATE")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDate prescriptionDate;
	public LocalDate getPrescriptionDate() {
		return prescriptionDate;
	}
	public void setPrescriptionDate(LocalDate prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}
	
	LocalDate expireDate;

	/**
	 * Date of the first appointment made for the prescription.
	 */
	@Column(columnDefinition="DATE")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDate firstTherapyDate;
	public LocalDate getFirstTherapyDate() {
		return firstTherapyDate;
	}
	public void setFirstTherapyDate(LocalDate firstTherapyDate) {
		this.firstTherapyDate = firstTherapyDate;
	}
	
	/**
	 * Virtual appointment date of first appointment on next prescription. 
	 */
	@Column(columnDefinition="DATE")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDate nextPrescription;
	public LocalDate getNextPrescription() {
		return nextPrescription;
	}
	public void setNextPrescription(LocalDate nextPrescription) {
		this.nextPrescription = nextPrescription;
	}
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prescription", fetch=FetchType.EAGER)
    private Set<TherapyAppointment> appointments;

    protected Set<TherapyAppointment> getAppointmentsInternal() {
        if (this.appointments == null) {
            this.appointments = new HashSet<>();
        }
        return this.appointments;
    }
   
    public List<TherapyAppointment> getAppointments() {
        List<TherapyAppointment> sortedDrugBoxItems = new ArrayList<>(getAppointmentsInternal());
        PropertyComparator.sort(sortedDrugBoxItems, new MutableSortDefinition("date", true, true));
        return Collections.unmodifiableList(sortedDrugBoxItems);
    }

}
