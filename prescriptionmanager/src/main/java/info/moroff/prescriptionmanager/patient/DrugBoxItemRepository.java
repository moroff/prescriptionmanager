package info.moroff.prescriptionmanager.patient;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Patient</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 */
public interface DrugBoxItemRepository extends Repository<DrugBoxItem, Integer> {

    /**
     * Retrieve an {@link DrugBoxItem} from the data store by id.
     * @param id the id to search for
     * @return the {@link DrugBoxItem} if found
     */
    @Query("SELECT drugBoxItem FROM DrugBoxItem drugBoxItem WHERE drugBoxItem.id =:id")
    @Transactional(readOnly = true)
    DrugBoxItem findById(@Param("id") Integer id);

    /**
     * Save an {@link DrugBoxItem} to the data store, either inserting or updating it.
     * @param drugBoxItem the {@link DrugBoxItem} to save
     */
    void save(DrugBoxItem drugBoxItem);

}
