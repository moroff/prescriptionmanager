package info.moroff.prescriptionmanager.drug;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Drug</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 */
public interface DrugRepository extends Repository<Drug, Integer> {

    /**
     * Retrieve all <code>Drug</code>s from the data store.
     *
     * @return a <code>Collection</code> of <code>Drug</code>s
     */
    @Transactional(readOnly = true)
    @Cacheable("drugs")
    Collection<Drug> findAll() throws DataAccessException;
	
    /**
     * Retrieve {@link Drug}s from the data store by name, returning all drugs
     * whose name <i>starts</i> with the given name.
     * @param name Value to search for
     * @return a Collection of matching {@link Drug}s (or an empty Collection if none
     * found)
     */
    @Query("SELECT drug FROM Drug drug WHERE drug.name LIKE :name% ORDER BY drug.name")
    @Transactional(readOnly = true)
    Collection<Drug> findByName(@Param("name") String name);

    /**
     * Retrieve an {@link Drug} from the data store by id.
     * @param id the id to search for
     * @return the {@link Drug} if found
     */
    @Query("SELECT drug FROM Drug drug WHERE drug.id =:id")
    @Transactional(readOnly = true)
    Drug findById(@Param("id") Integer id);

    /**
     * Save an {@link Drug} to the data store, either inserting or updating it.
     * @param drug the {@link Drug} to save
     */
    void save(Drug drug);


}
