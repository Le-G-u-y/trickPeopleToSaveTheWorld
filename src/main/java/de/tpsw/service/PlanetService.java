package de.tpsw.service;

import de.tpsw.domain.Planet;
import de.tpsw.repository.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Planet}.
 */
@Service
@Transactional
public class PlanetService {

    private final Logger log = LoggerFactory.getLogger(PlanetService.class);

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    /**
     * Save a planet.
     *
     * @param planet the entity to save.
     * @return the persisted entity.
     */
    public Planet save(Planet planet) {
        log.debug("Request to save Planet : {}", planet);
        return planetRepository.save(planet);
    }

    /**
     * Get all the planets.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Planet> findAll() {
        log.debug("Request to get all Planets");
        return planetRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the planets with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Planet> findAllWithEagerRelationships(Pageable pageable) {
        return planetRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one planet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Planet> findOne(Long id) {
        log.debug("Request to get Planet : {}", id);
        return planetRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the planet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Planet : {}", id);
        planetRepository.deleteById(id);
    }
}
