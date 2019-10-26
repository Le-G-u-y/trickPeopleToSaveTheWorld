package de.tpsw.service;

import de.tpsw.domain.Achievement;
import de.tpsw.repository.AchievementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Achievement}.
 */
@Service
@Transactional
public class AchievementService {

    private final Logger log = LoggerFactory.getLogger(AchievementService.class);

    private final AchievementRepository achievementRepository;

    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    /**
     * Save a achievement.
     *
     * @param achievement the entity to save.
     * @return the persisted entity.
     */
    public Achievement save(Achievement achievement) {
        log.debug("Request to save Achievement : {}", achievement);
        return achievementRepository.save(achievement);
    }

    /**
     * Get all the achievements.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Achievement> findAll() {
        log.debug("Request to get all Achievements");
        return achievementRepository.findAll();
    }


    /**
     * Get one achievement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Achievement> findOne(Long id) {
        log.debug("Request to get Achievement : {}", id);
        return achievementRepository.findById(id);
    }

    /**
     * Delete the achievement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Achievement : {}", id);
        achievementRepository.deleteById(id);
    }
}
