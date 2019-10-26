package de.tpsw.web.rest;

import de.tpsw.domain.Achievement;
import de.tpsw.service.AchievementService;
import de.tpsw.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.tpsw.domain.Achievement}.
 */
@RestController
@RequestMapping("/api")
public class AchievementResource {

    private final Logger log = LoggerFactory.getLogger(AchievementResource.class);

    private static final String ENTITY_NAME = "achievement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AchievementService achievementService;

    public AchievementResource(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    /**
     * {@code POST  /achievements} : Create a new achievement.
     *
     * @param achievement the achievement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new achievement, or with status {@code 400 (Bad Request)} if the achievement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/achievements")
    public ResponseEntity<Achievement> createAchievement(@Valid @RequestBody Achievement achievement) throws URISyntaxException {
        log.debug("REST request to save Achievement : {}", achievement);
        if (achievement.getId() != null) {
            throw new BadRequestAlertException("A new achievement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Achievement result = achievementService.save(achievement);
        return ResponseEntity.created(new URI("/api/achievements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /achievements} : Updates an existing achievement.
     *
     * @param achievement the achievement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achievement,
     * or with status {@code 400 (Bad Request)} if the achievement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the achievement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/achievements")
    public ResponseEntity<Achievement> updateAchievement(@Valid @RequestBody Achievement achievement) throws URISyntaxException {
        log.debug("REST request to update Achievement : {}", achievement);
        if (achievement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Achievement result = achievementService.save(achievement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, achievement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /achievements} : get all the achievements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of achievements in body.
     */
    @GetMapping("/achievements")
    public List<Achievement> getAllAchievements() {
        log.debug("REST request to get all Achievements");
        return achievementService.findAll();
    }

    /**
     * {@code GET  /achievements/:id} : get the "id" achievement.
     *
     * @param id the id of the achievement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the achievement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/achievements/{id}")
    public ResponseEntity<Achievement> getAchievement(@PathVariable Long id) {
        log.debug("REST request to get Achievement : {}", id);
        Optional<Achievement> achievement = achievementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(achievement);
    }

    /**
     * {@code DELETE  /achievements/:id} : delete the "id" achievement.
     *
     * @param id the id of the achievement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        log.debug("REST request to delete Achievement : {}", id);
        achievementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
