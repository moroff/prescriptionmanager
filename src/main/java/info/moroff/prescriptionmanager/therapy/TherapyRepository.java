package info.moroff.prescriptionmanager.therapy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Therapy</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 */
public interface TherapyRepository extends Repository<Therapy, Integer> {

    /**
     * Retrieve an {@link Therapy} from the data store by id.
     * @param id the id to search for
     * @return the {@link Therapy} if found
     */
    @Query("SELECT therapy FROM Therapy therapy WHERE therapy.id =:id")
    @Transactional(readOnly = true)
    Therapy findById(@Param("id") Integer id);

    /**
     * Save an {@link Therapy} to the data store, either inserting or updating it.
     * @param prescription the {@link Therapy} to save
     */
    Therapy save(Therapy therapy);

    /**
     * Deletes therapy, prescription and apointments.
     * @param therapy
     */
    @Transactional()
	void delete(Therapy therapy);
 
}
