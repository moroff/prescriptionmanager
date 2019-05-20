package info.moroff.prescriptionmanager.therapy;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import info.moroff.prescriptionmanager.model.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "therapy_appointment")
public class TherapyAppointment extends BaseEntity {

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="prescription_id", updatable=false, nullable=false)
	private TherapyPrescription prescription;
	public TherapyPrescription getPrescription() {
		return prescription;
	}
	public void setPrescription(TherapyPrescription prescription) {
		this.prescription = prescription;
	}
	
	@Column(name="date")
	LocalDate date;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
}
