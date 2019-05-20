package info.moroff.prescriptionmanager.therapy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Patient</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface TherapyPrescriptionRepository extends Repository<TherapyPrescription, Integer> {

    /**
     * Retrieve an {@link TherapyPrescription} from the data store by id.
     * @param id the id to search for
     * @return the {@link TherapyPrescription} if found
     */
    @Query("SELECT prescription FROM TherapyPrescription prescription WHERE prescription.id =:id")
    @Transactional(readOnly = true)
    TherapyPrescription findById(@Param("id") Integer id);

    /**
     * Save an {@link TherapyPrescription} to the data store, either inserting or updating it.
     * @param prescription the {@link TherapyPrescription} to save
     */
    void save(TherapyPrescription prescription);

}
