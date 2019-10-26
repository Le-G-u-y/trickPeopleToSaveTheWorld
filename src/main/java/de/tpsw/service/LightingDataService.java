package de.tpsw.service;

import de.tpsw.domain.LightingData;
import de.tpsw.repository.LightingDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LightingData}.
 */
@Service
@Transactional
public class LightingDataService {

    private final Logger log = LoggerFactory.getLogger(LightingDataService.class);

    private final LightingDataRepository lightingDataRepository;

    public LightingDataService(LightingDataRepository lightingDataRepository) {
        this.lightingDataRepository = lightingDataRepository;
    }

    /**
     * Save a lightingData.
     *
     * @param lightingData the entity to save.
     * @return the persisted entity.
     */
    public LightingData save(LightingData lightingData) {
        log.debug("Request to save LightingData : {}", lightingData);
        return lightingDataRepository.save(lightingData);
    }

    /**
     * Get all the lightingData.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LightingData> findAll() {
        log.debug("Request to get all LightingData");
        return lightingDataRepository.findAll();
    }


    /**
     * Get one lightingData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LightingData> findOne(Long id) {
        log.debug("Request to get LightingData : {}", id);
        return lightingDataRepository.findById(id);
    }

    /**
     * Delete the lightingData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LightingData : {}", id);
        lightingDataRepository.deleteById(id);
    }
}
