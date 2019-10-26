package de.tpsw.repository;
import de.tpsw.domain.Achievement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Achievement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {

}
