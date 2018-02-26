package info.moroff.prescriptionmanager.drug;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import info.moroff.prescriptionmanager.model.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name="drugs")
public class Drug extends BaseEntity {
	
	@Column(name="name", nullable=false, length=80, unique=true)
	String name;
	
	
	Integer packageSize;
	
	@Column(nullable=true)
	String pzn;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(Integer packageSize) {
		this.packageSize = packageSize;
	}
	
	public String getPzn() {
		return pzn;
	}
	public void setPzn(String pzn) {
		this.pzn = pzn;
	}
	
}
