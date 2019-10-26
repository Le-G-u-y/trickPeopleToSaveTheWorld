package de.tpsw.repository;
import de.tpsw.domain.LightingData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LightingData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LightingDataRepository extends JpaRepository<LightingData, Long> {

}
