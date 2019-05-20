package info.moroff.prescriptionmanager.patient;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import info.moroff.prescriptionmanager.therapy.TherapyPrescription;

/**
 * Repository class for <code>Patient</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface PatientRepository extends Repository<Patient, Integer> {

    /**
     * Retrieve all <code>Patient</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Patient</code>s
     */
    @Transactional(readOnly = true)
    @Cacheable("patients")
    Collection<Patient> findAll() throws DataAccessException;
	
    /**
     * Retrieve an {@link Patient} from the data store by id.
     * @param id the id to search for
     * @return the {@link Patient} if found
     */
    @Query("SELECT patient FROM Patient patient WHERE patient.id =:id")
    @Transactional(readOnly = true)
    Patient findById(@Param("id") Integer id);

    /**
     * Save an {@link Patient} to the data store, either inserting or updating it.
     * @param patient the {@link Patient} to save
     */
    void save(Patient patient);

    /**
     * Save an {@link DrugBoxItem} to the data store, either inserting or updating it.
     * @param drugBoxItem
     */
	void save(DrugBoxItem drugBoxItem);


}
