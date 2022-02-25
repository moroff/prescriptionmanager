package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import info.moroff.prescriptionmanager.model.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "therapy_appointment")
public class TherapyAppointment extends BaseEntity {

	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="prescription_id", updatable=true, nullable=true)
	@JsonIgnore
	private TherapyPrescription prescription;
	
	public TherapyPrescription getPrescription() {
		return prescription;
	}
	public void setPrescription(TherapyPrescription prescription) {
		this.prescription = prescription;
	}
	
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

	@Column(name="date")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	LocalDate date;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	@Column(name = "uuid", columnDefinition = "VARCHAR", length = 36)
	String uuid;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
