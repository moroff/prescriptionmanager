package info.moroff.prescriptionmanager.therapy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>TherapyAppointment</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 */
public interface TherapyAppointmentRepository extends Repository<TherapyAppointment, Integer> {

    /**
     * Save an {@link TherapyAppointment} to the data store, either inserting or updating it.
     * @param appointment the {@link TherapyAppointment} to save
     */
	@Transactional
	void save(TherapyAppointment appointment);

    @Query("SELECT appointment FROM TherapyAppointment appointment WHERE appointment.id =:id")
    @Transactional(readOnly = true)
	TherapyAppointment findById(@Param("id") Integer id);

    @Transactional()
	void delete(TherapyAppointment appointment);

}
