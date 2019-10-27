package de.tpsw.web.rest;

import de.tpsw.TrickPeopleToSaveTheWorldApp;
import de.tpsw.domain.Planet;
import de.tpsw.repository.PlanetRepository;
import de.tpsw.service.PlanetService;
import de.tpsw.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static de.tpsw.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlanetResource} REST controller.
 */
@SpringBootTest(classes = TrickPeopleToSaveTheWorldApp.class)
public class PlanetResourceIT {

    private static final Long DEFAULT_FOREST_POINTS = 1L;
    private static final Long UPDATED_FOREST_POINTS = 2L;

    private static final Long DEFAULT_WATER_POINTS = 1L;
    private static final Long UPDATED_WATER_POINTS = 2L;

    private static final Long DEFAULT_SMOG_POINTS = 1L;
    private static final Long UPDATED_SMOG_POINTS = 2L;

    @Autowired
    private PlanetRepository planetRepository;

    @Mock
    private PlanetRepository planetRepositoryMock;

    @Mock
    private PlanetService planetServiceMock;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPlanetMockMvc;

    private Planet planet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlanetResource planetResource = new PlanetResource(planetService);
        this.restPlanetMockMvc = MockMvcBuilders.standaloneSetup(planetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planet createEntity(EntityManager em) {
        Planet planet = new Planet()
            .forestPoints(DEFAULT_FOREST_POINTS)
            .waterPoints(DEFAULT_WATER_POINTS)
            .smogPoints(DEFAULT_SMOG_POINTS);
        return planet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planet createUpdatedEntity(EntityManager em) {
        Planet planet = new Planet()
            .forestPoints(UPDATED_FOREST_POINTS)
            .waterPoints(UPDATED_WATER_POINTS)
            .smogPoints(UPDATED_SMOG_POINTS);
        return planet;
    }

    @BeforeEach
    public void initTest() {
        planet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanet() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();

        // Create the Planet
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planet)))
            .andExpect(status().isCreated());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate + 1);
        Planet testPlanet = planetList.get(planetList.size() - 1);
        assertThat(testPlanet.getForestPoints()).isEqualTo(DEFAULT_FOREST_POINTS);
        assertThat(testPlanet.getWaterPoints()).isEqualTo(DEFAULT_WATER_POINTS);
        assertThat(testPlanet.getSmogPoints()).isEqualTo(DEFAULT_SMOG_POINTS);
    }

    @Test
    @Transactional
    public void createPlanetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();

        // Create the Planet with an existing ID
        planet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planet)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkForestPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setForestPoints(null);

        // Create the Planet, which fails.

        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planet)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWaterPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setWaterPoints(null);

        // Create the Planet, which fails.

        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planet)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmogPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setSmogPoints(null);

        // Create the Planet, which fails.

        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planet)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanets() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get all the planetList
        restPlanetMockMvc.perform(get("/api/planets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planet.getId().intValue())))
            .andExpect(jsonPath("$.[*].forestPoints").value(hasItem(DEFAULT_FOREST_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].waterPoints").value(hasItem(DEFAULT_WATER_POINTS.intValue())))
            .andExpect(jsonPath("$.[*].smogPoints").value(hasItem(DEFAULT_SMOG_POINTS.intValue())));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlanetsWithEagerRelationshipsIsEnabled() throws Exception {
        PlanetResource planetResource = new PlanetResource(planetServiceMock);
        when(planetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPlanetMockMvc = MockMvcBuilders.standaloneSetup(planetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlanetMockMvc.perform(get("/api/planets?eagerload=true"))
        .andExpect(status().isOk());

        verify(planetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPlanetsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PlanetResource planetResource = new PlanetResource(planetServiceMock);
            when(planetServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPlanetMockMvc = MockMvcBuilders.standaloneSetup(planetResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPlanetMockMvc.perform(get("/api/planets?eagerload=true"))
        .andExpect(status().isOk());

            verify(planetServiceMock, times(1)).findAllWithEagerRelationships(any());
    }


    @Test
    @Transactional
    public void getNonExistingPlanet() throws Exception {
        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanet() throws Exception {
        // Initialize the database
        planetService.save(planet);

        int databaseSizeBeforeUpdate = planetRepository.findAll().size();

        // Update the planet
        Planet updatedPlanet = planetRepository.findById(planet.getId()).get();
        // Disconnect from session so that the updates on updatedPlanet are not directly saved in db
        em.detach(updatedPlanet);
        updatedPlanet
            .forestPoints(UPDATED_FOREST_POINTS)
            .waterPoints(UPDATED_WATER_POINTS)
            .smogPoints(UPDATED_SMOG_POINTS);

        restPlanetMockMvc.perform(put("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlanet)))
            .andExpect(status().isOk());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeUpdate);
        Planet testPlanet = planetList.get(planetList.size() - 1);
        assertThat(testPlanet.getForestPoints()).isEqualTo(UPDATED_FOREST_POINTS);
        assertThat(testPlanet.getWaterPoints()).isEqualTo(UPDATED_WATER_POINTS);
        assertThat(testPlanet.getSmogPoints()).isEqualTo(UPDATED_SMOG_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingPlanet() throws Exception {
        int databaseSizeBeforeUpdate = planetRepository.findAll().size();

        // Create the Planet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanetMockMvc.perform(put("/api/planets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(planet)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlanet() throws Exception {
        // Initialize the database
        planetService.save(planet);

        int databaseSizeBeforeDelete = planetRepository.findAll().size();

        // Delete the planet
        restPlanetMockMvc.perform(delete("/api/planets/{id}", planet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planet.class);
        Planet planet1 = new Planet();
        planet1.setId(1L);
        Planet planet2 = new Planet();
        planet2.setId(planet1.getId());
        assertThat(planet1).isEqualTo(planet2);
        planet2.setId(2L);
        assertThat(planet1).isNotEqualTo(planet2);
        planet1.setId(null);
        assertThat(planet1).isNotEqualTo(planet2);
    }
}
