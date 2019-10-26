package de.tpsw.repository;
import de.tpsw.domain.Co2Data;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Co2Data entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Co2DataRepository extends JpaRepository<Co2Data, Long> {

}
