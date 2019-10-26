package de.tpsw.service;

import de.tpsw.domain.Animal;
import de.tpsw.domain.Planet;
import de.tpsw.domain.enumeration.AnimalType;
import de.tpsw.repository.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Autowired
    private AnimalService animalService;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    final static Integer DIET_VEGAN = 0;
    final static Integer DIET_VEGETARIAN = 1;
    final static Integer DIET_MEAT = 2;

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
     * Update current Victim
     *
     * @param planetId the entity to save.
     * @return the persisted entity.
     */
    public Planet updateCurrentVictim(Long planetId, Integer dietType) {
        log.debug("Request to save Planet : {} and dietType: {}", planetId, dietType);

        // Get current victim
        Planet planet =  findOne(planetId).get();

        // Calculate conseuqences on victim animal
        // vegan:       +3
        // vegetarian:  +1
        // meeat:       -1
        // as soon as current victim is full, add health to baby.
        // as soon as babys health is full, create a new animal!
        Animal currentVictim = planet.getCurrentVictimAnimal();
        Integer healthDiff = calculateHealthDiff(dietType);

        // increase or decrease victim's health
        // Victim dead? => UI will inform the user
        Integer healthOverflow = currentVictim.alterHealth(healthDiff);
        animalService.save(currentVictim);

        // Add health (positive) overflow to upcoming baby
        Animal nextBaby = planet.getNextBabyAnimal();
        nextBaby.alterHealth(healthOverflow);

        if(nextBaby.checkIsHealthFull()){
            // baby is now adult! it part of the normal animals now
            planet.getAnimals().add(nextBaby);

            // a new baby is born!! create new animal object
            Animal brandNewBaby = new Animal();
            brandNewBaby.setName("BABY");
            brandNewBaby.setAnimalType(AnimalType.getRandomAnimalType());
            brandNewBaby.setCurrentHealth(1);
            brandNewBaby.setMaxHealth(5);
            brandNewBaby.setCreationDate(LocalDate.now());
            brandNewBaby.setHappiness(true);
            brandNewBaby.setDeathNotified(false);
            brandNewBaby.setPlanet(planet);

            // persist
            animalService.save(brandNewBaby);

            planet.setNextBabyAnimal(brandNewBaby);
        }

        return planetRepository.save(planet);
    }

    private Integer calculateHealthDiff(Integer dietType) {
        Integer healthDiff;

        if(DIET_VEGAN.equals(dietType)){
            healthDiff = 3;
        }
        else if(DIET_VEGETARIAN.equals(dietType)){
            healthDiff = 1;
        }
        else if(DIET_MEAT.equals(dietType)){
            healthDiff = -1;
        } else {
            //HACK
            healthDiff = 0;
        }
        return healthDiff;
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
