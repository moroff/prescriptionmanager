package info.moroff.prescriptionmanager.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public class PersonWithAddress extends Person {

	@Column(name="street")
//	@NotEmpty
	String street;

	@Column(name="house_number")
//	@NotEmpty
	String houseNumber;

	@Column(name="zip")
//	@NotEmpty
	String zipCode;

	@Column(name="city")
//	@NotEmpty
	String city;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHouseNumber() {
		return houseNumber;
	}
	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
		
}
