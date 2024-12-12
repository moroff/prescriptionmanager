package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.moroff.prescriptionmanager.model.BaseEntity;
import info.moroff.prescriptionmanager.model.Periodicity;
import info.moroff.prescriptionmanager.util.LocalDateAdapter;

/**
 * A therapy prescription
 * 
 * @author dieter
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "therapy_prescription")
public class TherapyPrescription extends BaseEntity {

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="therapy_id", updatable=false, nullable=false)
	@JsonIgnore
	private Therapy therapy;

	public Therapy getTherapy() {
		return therapy;
	}
	public void setTherapy(Therapy therapy) {
		this.therapy = therapy;
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
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
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

	@Column(columnDefinition = "TIME")
	@DateTimeFormat(pattern = "HH:mm")
	LocalTime therapyStartTime;
	
	public LocalTime getTherapyStartTime() {
		return therapyStartTime;
	}
	public void setTherapyStartTime(LocalTime therapyStartTime) {
		this.therapyStartTime = therapyStartTime;
	}
	
	@Column(columnDefinition =  "INTEGER")
	Integer therapyDuration;
	
	public Integer getTherapyDuration() {
		return therapyDuration;
	}
	public void setTherapyDuration(Integer therapyDuration) {
		this.therapyDuration = therapyDuration;
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
		if ( !Objects.equals(this.nextPrescription, nextPrescription) ) {
			this.nextPrescription = nextPrescription;
		}
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
	Boolean onTuesdays = Boolean.FALSE;

	public Boolean getOnTuesdays() {
		return onTuesdays;
	}

	public void setOnTuesdays(Boolean onTuesdays) {
		this.onTuesdays = onTuesdays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "WEDNESDAY")
	Boolean onWednesdays = Boolean.FALSE;

	public Boolean getOnWednesdays() {
		return onWednesdays;
	}

	public void setOnWednesdays(Boolean onWednesdays) {
		this.onWednesdays = onWednesdays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "THURSDAY")
	Boolean onThursdays = Boolean.FALSE;

	public Boolean getOnThursdays() {
		return onThursdays;
	}

	public void setOnThursdays(Boolean onThursdays) {
		this.onThursdays = onThursdays;
	}

	@Column(columnDefinition = "BOOLEAN", name = "FRIDAY")
	Boolean onFridays = Boolean.FALSE;

	public Boolean getOnFridays() {
		return onFridays;
	}

	public void setOnFridays(Boolean onFridays) {
		this.onFridays = onFridays;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "prescription", fetch = FetchType.EAGER, orphanRemoval = false)
	private Set<TherapyAppointment> appointments;

	protected Set<TherapyAppointment> getAppointmentsInternal() {
		if (this.appointments == null) {
			this.appointments = new HashSet<>();
		}
		return this.appointments;
	}

	public List<TherapyAppointment> getAppointments() {
		List<TherapyAppointment> items = new ArrayList<>(getAppointmentsInternal());
		List<TherapyAppointment> sorted = items.stream().//
				filter(a -> a.getPrescription() == this).//
				sorted((a1, a2) -> compareDate(a1.getDate(),a2.getDate())).//
				collect(Collectors.toList());
		return Collections.unmodifiableList(sorted);
	}

	public static int compareDate(LocalDate d1, LocalDate d2) {
		if ( d1 == null && d2 == null ) {
			return 0;
		}
		else if ( d1 == null && d2 != null ) {
			return 1;
		}
		else if ( d1 != null && d2 == null ) {
			return -1;
		}
		else {
			return d1.compareTo(d2);
		}
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

	@JsonIgnore
	public List<String> getPeriodicities() {
		List<String> periodicities = new ArrayList<>();
		
		for (Periodicity string : Periodicity.values()) {
			periodicities.add(Integer.toString(string.getValue()));
		}
		return periodicities;
	}
	
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
			return null;
		}
	}
	
	public LocalDate calcNextDate(LocalDate startDate) {

		if ( startDate == null ) {
			return null;
		}
		
		Periodicity periodicity = getPeriodicity();

		if (periodicity == null) {
			periodicity = Periodicity.WEEKLY;
		}

		switch (periodicity) {
		case DAILY:
			return startDate.plusDays(1);
		case WEEKLY:
			return getNextWeekDay(startDate);
		case MONTHLY:
			return startDate.plusMonths(1);
		case QUARTER:
			return startDate.plusMonths(3);
		case YEARLY:
			return startDate.plusYears(1);
		default:
			throw new IllegalArgumentException(periodicity.name());
		}
	}
	
	public List<TherapyAppointment> addAppointments(Therapy stored) {
		LocalDate therapyDate = getFirstTherapyDate();
		if (therapyDate == null && prescriptionDate != null) {
			therapyDate = calcNextDate(prescriptionDate);
			setFirstTherapyDate(therapyDate);
		}
		if ( getCount() != null ) {
			for (int i = 0; i < getCount(); i++) {
				TherapyAppointment appointment = new TherapyAppointment();

				appointment.setDate(therapyDate);
				appointment.setTherapy(stored);
				appointment.setUuid(UUID.randomUUID().toString());
				therapyDate = calcNextDate(therapyDate);
				addAppointment(appointment);
			}
		}
		return getAppointments();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if ( prescriptionDate != null ) {
			sb.append("prescriptionDate=").append(prescriptionDate.format(DateTimeFormatter.BASIC_ISO_DATE));
		}
		return sb.toString();
	}

}
