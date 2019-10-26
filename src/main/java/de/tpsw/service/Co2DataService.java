package de.tpsw.service;

import de.tpsw.domain.Co2Data;
import de.tpsw.repository.Co2DataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Co2Data}.
 */
@Service
@Transactional
public class Co2DataService {

    private final Logger log = LoggerFactory.getLogger(Co2DataService.class);

    private final Co2DataRepository co2DataRepository;

    public Co2DataService(Co2DataRepository co2DataRepository) {
        this.co2DataRepository = co2DataRepository;
    }

    /**
     * Save a co2Data.
     *
     * @param co2Data the entity to save.
     * @return the persisted entity.
     */
    public Co2Data save(Co2Data co2Data) {
        log.debug("Request to save Co2Data : {}", co2Data);
        return co2DataRepository.save(co2Data);
    }

    /**
     * Get all the co2Data.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Co2Data> findAll() {
        log.debug("Request to get all Co2Data");
        return co2DataRepository.findAll();
    }


    /**
     * Get one co2Data by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Co2Data> findOne(Long id) {
        log.debug("Request to get Co2Data : {}", id);
        return co2DataRepository.findById(id);
    }

    /**
     * Delete the co2Data by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Co2Data : {}", id);
        co2DataRepository.deleteById(id);
    }
}
