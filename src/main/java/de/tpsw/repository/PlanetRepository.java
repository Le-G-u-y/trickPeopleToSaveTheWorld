package de.tpsw.repository;
import de.tpsw.domain.Planet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Planet entity.
 */
@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    @Query(value = "select distinct planet from Planet planet left join fetch planet.achievements",
        countQuery = "select count(distinct planet) from Planet planet")
    Page<Planet> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct planet from Planet planet left join fetch planet.achievements")
    List<Planet> findAllWithEagerRelationships();

    @Query("select planet from Planet planet left join fetch planet.achievements where planet.id =:id")
    Optional<Planet> findOneWithEagerRelationships(@Param("id") Long id);

}
