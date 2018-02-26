package info.moroff.prescriptionmanager.patient;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import org.springframework.format.annotation.DateTimeFormat;

import info.moroff.prescriptionmanager.drug.Drug;
import info.moroff.prescriptionmanager.model.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "drugbox")
public class DrugBoxItem extends BaseEntity {

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="patient_id", updatable=false, nullable=false)
	private Patient patient;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="drug_id", updatable=false, nullable=false)
	private Drug drug;

	/**
	 * Date of last inventory.
	 */
	@Column(columnDefinition="DATE")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	private LocalDate inventoryDate;
	
	/**
	 * Amount at last inventory;
	 */
	@DecimalMin(value="0.0")
	private Double inventoryAmount;
	
	/**
	 * Daily intake of this drug
	 */
	@DecimalMin(value="0.25")
	private Double daylyIntake;

	// Business logic
	public String getDrugName() {
		return drug.getName();
	}

	/**
	 * Returns the date when the inventory is exhausted.
	 */
	public LocalDate getExhaustingDate() {
		if ( inventoryDate != null && inventoryAmount != null && daylyIntake != null ) {
			Double remainingDays = Math.floor(inventoryAmount / daylyIntake);
			
			return inventoryDate.plusDays(remainingDays.longValue());
		}
		else {
			return null;
		}
	}
	
	// Setter-/Getters
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Drug getDrug() {
		return drug;
	}
	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public LocalDate getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(LocalDate inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public Double getInventoryAmount() {
		return inventoryAmount;
	}

	public void setInventoryAmount(Double inventoryAmount) {
		this.inventoryAmount = inventoryAmount;
	}

	public Double getDaylyIntake() {
		return daylyIntake;
	}

	public void setDaylyIntake(Double daylyIntake) {
		this.daylyIntake = daylyIntake;
	}


}
