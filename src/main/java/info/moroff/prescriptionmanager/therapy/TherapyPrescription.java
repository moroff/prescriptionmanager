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
 * 
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

	@Column(columnDefinition = "INTEGER")
	Periodicity periodicity;

	public Periodicity getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(Periodicity periodicity) {
		this.periodicity = periodicity;
	}

	/**
	 * Issue date of the prescription.
	 */
	@Column(columnDefinition = "DATE")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
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
	@Column(columnDefinition = "DATE")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
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
	@Column(columnDefinition = "DATE")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	LocalDate nextPrescription;

	public LocalDate getNextPrescription() {
		return nextPrescription;
	}

	public void setNextPrescription(LocalDate nextPrescription) {
		this.nextPrescription = nextPrescription;
	}

	@Column(columnDefinition = "BOOLEAN", name = "MONDAY")
	Boolean onMondays = Boolean.FALSE;

	public Boolean getOnMondays() {
		return onMondays;
	}

	public void setOnMondays(Boolean onMondays) {
		this.onMondays = onMondays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "TUESDAY")
	Boolean onTuesdays;

	public Boolean getOnTuesdays() {
		return onTuesdays;
	}

	public void setOnTuesdays(Boolean onTuesdays) {
		this.onTuesdays = onTuesdays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "WEDNESDAY")
	Boolean onWednesdays;

	public Boolean getOnWednesdays() {
		return onWednesdays;
	}

	public void setOnWednesdays(Boolean onWednesdays) {
		this.onWednesdays = onWednesdays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "THURSDAY")
	Boolean onThursdays;

	public Boolean getOnThursdays() {
		return onThursdays;
	}

	public void setOnThursdays(Boolean onThursdays) {
		this.onThursdays = onThursdays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "FRIDAY")
	Boolean onFridays;

	public Boolean getOnFridays() {
		return onFridays;
	}

	public void setOnFridays(Boolean onFridays) {
		this.onFridays = onFridays;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prescription", fetch = FetchType.EAGER, orphanRemoval = true)
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

	public void addAppointment(TherapyAppointment appointment) {
		if (appointment.isNew()) {
			getAppointmentsInternal().add(appointment);
		}
		appointment.setPrescription(this);
	}

	public void removeAppointment(TherapyAppointment appointment) {
		getAppointmentsInternal().remove(appointment);
	}

	private int dayOffset[][] = {
			{ 7, 1, 2, 3, 4 }, // MONDAY
			{ 6, 7, 1, 2, 3 }, // TUESDAY
			{ 5, 6, 7, 1, 2 }, // WEDNESDAY
			{ 4, 5, 6, 7, 1 }, // THURSDAY
			{ 3, 4, 5, 6, 7 }, // FRIDAY
			{ 2, 3, 4, 5, 6 }, // SATUDAY
			{ 1, 2, 3, 4, 5 }, // SUNDAY
	};
	
	public LocalDate getNextWeekDay(LocalDate currentDate) {
		if (onMondays || onTuesdays || onWednesdays || onThursdays || onFridays) {
			switch (currentDate.getDayOfWeek()) {
			case MONDAY:
				if (onTuesdays) {
					return currentDate.plusDays(1);
				}
				if (onWednesdays) {
					return currentDate.plusDays(2);
				}
				if (onThursdays) {
					return currentDate.plusDays(3);
				}
				if (onFridays) {
					return currentDate.plusDays(4);
				}
				if (onMondays) {
					return currentDate.plusDays(7);
				}
				break;
			case TUESDAY:
				if (onWednesdays) {
					return currentDate.plusDays(1);
				}
				if (onThursdays) {
					return currentDate.plusDays(2);
				}
				if (onFridays) {
					return currentDate.plusDays(3);
				}
				if (onMondays) {
					return currentDate.plusDays(6);
				}
				if (onTuesdays) {
					return currentDate.plusDays(7);
				}
				break;
			case WEDNESDAY:
				if (onThursdays) {
					return currentDate.plusDays(1);
				}
				if (onFridays) {
					return currentDate.plusDays(2);
				}
				if (onMondays) {
					return currentDate.plusDays(5);
				}
				if (onTuesdays) {
					return currentDate.plusDays(6);
				}
				if (onWednesdays) {
					return currentDate.plusDays(7);
				}
				break;
			case THURSDAY:
				if (onFridays) {
					return currentDate.plusDays(1);
				}
				if (onMondays) {
					return currentDate.plusDays(4);
				}
				if (onTuesdays) {
					return currentDate.plusDays(5);
				}
				if (onWednesdays) {
					return currentDate.plusDays(6);
				}
				if (onThursdays) {
					return currentDate.plusDays(7);
				}
				break;
			case FRIDAY:
				if (onMondays) {
					return currentDate.plusDays(3);
				}
				if (onTuesdays) {
					return currentDate.plusDays(4);
				}
				if (onWednesdays) {
					return currentDate.plusDays(5);
				}
				if (onThursdays) {
					return currentDate.plusDays(6);
				}
				if (onFridays) {
					return currentDate.plusDays(7);
				}
				break;
			case SATURDAY:
				if (onMondays) {
					return currentDate.plusDays(2);
				}
				if (onTuesdays) {
					return currentDate.plusDays(3);
				}
				if (onWednesdays) {
					return currentDate.plusDays(4);
				}
				if (onThursdays) {
					return currentDate.plusDays(5);
				}
				if (onFridays) {
					return currentDate.plusDays(6);
				}
				break;
			case SUNDAY:
				if (onMondays) {
					return currentDate.plusDays(1);
				}
				if (onTuesdays) {
					return currentDate.plusDays(2);
				}
				if (onWednesdays) {
					return currentDate.plusDays(3);
				}
				if (onThursdays) {
					return currentDate.plusDays(4);
				}
				if (onFridays) {
					return currentDate.plusDays(5);
				}
				break;
			}
			
			throw new IllegalStateException("Check implementation for "+currentDate.getDayOfWeek());
		} else {
			return currentDate.plusDays(7);
		}
	}

}
