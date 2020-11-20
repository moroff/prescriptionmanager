package info.moroff.prescriptionmanager.patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

import info.moroff.prescriptionmanager.model.PersonWithAddress;
import info.moroff.prescriptionmanager.therapy.Therapy;
import info.moroff.prescriptionmanager.util.CompareUtils;

/**
 * Simple JavaBean domain object representing a patient.
 *
 * @author Dieter Moroff
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "patients")
public class Patient extends PersonWithAddress {

	@Column(name = "hide")
	private Boolean hide;
	
    @Column(name = "telephone")
//    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch=FetchType.EAGER)
    private Set<DrugBoxItem> drugBoxItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch=FetchType.EAGER, orphanRemoval=true)
    private Set<Therapy> therapies;

    public Boolean getHide() {
		return hide != null ? hide : Boolean.FALSE;
	}
    public void setHide(Boolean hide) {
		this.hide = hide;
	}
    
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    protected Set<DrugBoxItem> getDrugBoxItemsInternal() {
        if (this.drugBoxItems == null) {
            this.drugBoxItems = new HashSet<>();
        }
        return this.drugBoxItems;
    }

    protected void setDrugBoxItemsInternal(Set<DrugBoxItem> drugBoxItems) {
        this.drugBoxItems = drugBoxItems;
    }

    public List<DrugBoxItem> getDrugBoxItems() {
        List<DrugBoxItem> sortedDrugBoxItems = new ArrayList<>(getDrugBoxItemsInternal());
        PropertyComparator.sort(sortedDrugBoxItems, new MutableSortDefinition("id", true, true));
        return Collections.unmodifiableList(sortedDrugBoxItems);
    }

    public List<Therapy> getTherapies() {
        List<Therapy> sortedItems = new ArrayList<>(getTherapyInternal());
        PropertyComparator.sort(sortedItems, new MutableSortDefinition("id", true, true));
        return Collections.unmodifiableList(sortedItems);
    }

    public void deleteTherapy(Therapy therapy) {
    	getTherapyInternal().remove(therapy);
    }
    
    protected Set<Therapy> getTherapyInternal() {
		if (this.therapies == null) {
			this.therapies = new HashSet<>();
		}
		return this.therapies;
	}

	public void addDrugBoxItem(DrugBoxItem drugBoxItem) {
        if (drugBoxItem.isNew()) {
            getDrugBoxItemsInternal().add(drugBoxItem);
        }
        drugBoxItem.setPatient(this);
    }

    public LocalDate getExhaustingDate() {
    	Optional<LocalDate> minDate = getDrugBoxItemsInternal().stream().map(DrugBoxItem::getExhaustingDate).min(CompareUtils::compare);
    	
    	if ( minDate.isPresent() ) 
    		return minDate.get();
    	else
    		return null;
    }
    
    public Integer getRemainingDays() {
    	Optional<Integer> minDays = getDrugBoxItemsInternal().stream().map(DrugBoxItem::getRemainingDays).min(CompareUtils::compare);
    	
    	if ( minDays.isPresent() ) 
    		return minDays.get();
    	else
    		return null;
    	
    }
    
    public LocalDate getNextAppointmentDate() {
    	Optional<LocalDate> minDate = getTherapyInternal().stream().map(t -> t.getLastAppointmentDate()).min(CompareUtils::compare);

    	if ( minDate.isPresent() ) 
    		return minDate.get();
    	else
    		return null;
    }
    
    /**
     * Return the DrugBoxItem with the given drug name, or null if none found for this patient.
     *
     * @param drugName to test
     * @return true if drugBoxItem name is already in use
     */
    public DrugBoxItem getDrugBoxItem(String drugName) {
        return getDrugBoxItem(drugName, false);
    }

    /**
     * Return the DrugBoxItem with the given name, or null if none found for this Owner.
     *
     * @param drugName to test
     * @return true if drugBoxItem name is already in use
     */
    public DrugBoxItem getDrugBoxItem(String drugName, boolean ignoreNew) {
        drugName = drugName.toLowerCase();
        for (DrugBoxItem drugBoxItem : getDrugBoxItemsInternal()) {
            if (!ignoreNew || !drugBoxItem.isNew()) {
                String compName = drugBoxItem.getDrugName();
                compName = compName.toLowerCase();
                if (compName.equals(drugName)) {
                    return drugBoxItem;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

            .append("id", this.getId())
            .append("new", this.isNew())
            .append("lastName", this.getLastName())
            .append("firstName", this.getFirstName())
            .append("telephone", this.telephone)
            .toString();
    }
}
