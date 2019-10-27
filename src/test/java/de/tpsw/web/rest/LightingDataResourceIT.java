package de.tpsw.web.rest;

import de.tpsw.TrickPeopleToSaveTheWorldApp;
import de.tpsw.domain.LightingData;
import de.tpsw.repository.LightingDataRepository;
import de.tpsw.service.LightingDataService;
import de.tpsw.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static de.tpsw.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LightingDataResource} REST controller.
 */
@SpringBootTest(classes = TrickPeopleToSaveTheWorldApp.class)
public class LightingDataResourceIT {

    private static final Long DEFAULT_ON_SECONDS = 1L;
    private static final Long UPDATED_ON_SECONDS = 2L;

    private static final Long DEFAULT_OFF_SECONDS = 1L;
    private static final Long UPDATED_OFF_SECONDS = 2L;

    @Autowired
    private LightingDataRepository lightingDataRepository;

    @Autowired
    private LightingDataService lightingDataService;

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

    private MockMvc restLightingDataMockMvc;

    private LightingData lightingData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LightingDataResource lightingDataResource = new LightingDataResource(lightingDataService);
        this.restLightingDataMockMvc = MockMvcBuilders.standaloneSetup(lightingDataResource)
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
    public static LightingData createEntity(EntityManager em) {
        LightingData lightingData = new LightingData()
            .onSeconds(DEFAULT_ON_SECONDS)
            .offSeconds(DEFAULT_OFF_SECONDS);
        return lightingData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LightingData createUpdatedEntity(EntityManager em) {
        LightingData lightingData = new LightingData()
            .onSeconds(UPDATED_ON_SECONDS)
            .offSeconds(UPDATED_OFF_SECONDS);
        return lightingData;
    }

    @BeforeEach
    public void initTest() {
        lightingData = createEntity(em);
    }

    @Test
    @Transactional
    public void createLightingData() throws Exception {
        int databaseSizeBeforeCreate = lightingDataRepository.findAll().size();

        // Create the LightingData
        restLightingDataMockMvc.perform(post("/api/lighting-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lightingData)))
            .andExpect(status().isCreated());

        // Validate the LightingData in the database
        List<LightingData> lightingDataList = lightingDataRepository.findAll();
        assertThat(lightingDataList).hasSize(databaseSizeBeforeCreate + 1);
        LightingData testLightingData = lightingDataList.get(lightingDataList.size() - 1);
        assertThat(testLightingData.getOnSeconds()).isEqualTo(DEFAULT_ON_SECONDS);
        assertThat(testLightingData.getOffSeconds()).isEqualTo(DEFAULT_OFF_SECONDS);
    }

    @Test
    @Transactional
    public void createLightingDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lightingDataRepository.findAll().size();

        // Create the LightingData with an existing ID
        lightingData.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLightingDataMockMvc.perform(post("/api/lighting-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lightingData)))
            .andExpect(status().isBadRequest());

        // Validate the LightingData in the database
        List<LightingData> lightingDataList = lightingDataRepository.findAll();
        assertThat(lightingDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLightingData() throws Exception {
        // Initialize the database
        lightingDataRepository.saveAndFlush(lightingData);

        // Get all the lightingDataList
        restLightingDataMockMvc.perform(get("/api/lighting-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lightingData.getId().intValue())))
            .andExpect(jsonPath("$.[*].onSeconds").value(hasItem(DEFAULT_ON_SECONDS.intValue())))
            .andExpect(jsonPath("$.[*].offSeconds").value(hasItem(DEFAULT_OFF_SECONDS.intValue())));
    }
    
    @Test
    @Transactional
    public void getLightingData() throws Exception {
        // Initialize the database
        lightingDataRepository.saveAndFlush(lightingData);

        // Get the lightingData
        restLightingDataMockMvc.perform(get("/api/lighting-data/{id}", lightingData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lightingData.getId().intValue()))
            .andExpect(jsonPath("$.onSeconds").value(DEFAULT_ON_SECONDS.intValue()))
            .andExpect(jsonPath("$.offSeconds").value(DEFAULT_OFF_SECONDS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLightingData() throws Exception {
        // Get the lightingData
        restLightingDataMockMvc.perform(get("/api/lighting-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLightingData() throws Exception {
        // Initialize the database
        lightingDataService.save(lightingData);

        int databaseSizeBeforeUpdate = lightingDataRepository.findAll().size();

        // Update the lightingData
        LightingData updatedLightingData = lightingDataRepository.findById(lightingData.getId()).get();
        // Disconnect from session so that the updates on updatedLightingData are not directly saved in db
        em.detach(updatedLightingData);
        updatedLightingData
            .onSeconds(UPDATED_ON_SECONDS)
            .offSeconds(UPDATED_OFF_SECONDS);

        restLightingDataMockMvc.perform(put("/api/lighting-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLightingData)))
            .andExpect(status().isOk());

        // Validate the LightingData in the database
        List<LightingData> lightingDataList = lightingDataRepository.findAll();
        assertThat(lightingDataList).hasSize(databaseSizeBeforeUpdate);
        LightingData testLightingData = lightingDataList.get(lightingDataList.size() - 1);
        assertThat(testLightingData.getOnSeconds()).isEqualTo(UPDATED_ON_SECONDS);
        assertThat(testLightingData.getOffSeconds()).isEqualTo(UPDATED_OFF_SECONDS);
    }

    @Test
    @Transactional
    public void updateNonExistingLightingData() throws Exception {
        int databaseSizeBeforeUpdate = lightingDataRepository.findAll().size();

        // Create the LightingData

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLightingDataMockMvc.perform(put("/api/lighting-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lightingData)))
            .andExpect(status().isBadRequest());

        // Validate the LightingData in the database
        List<LightingData> lightingDataList = lightingDataRepository.findAll();
        assertThat(lightingDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLightingData() throws Exception {
        // Initialize the database
        lightingDataService.save(lightingData);

        int databaseSizeBeforeDelete = lightingDataRepository.findAll().size();

        // Delete the lightingData
        restLightingDataMockMvc.perform(delete("/api/lighting-data/{id}", lightingData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LightingData> lightingDataList = lightingDataRepository.findAll();
        assertThat(lightingDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LightingData.class);
        LightingData lightingData1 = new LightingData();
        lightingData1.setId(1L);
        LightingData lightingData2 = new LightingData();
        lightingData2.setId(lightingData1.getId());
        assertThat(lightingData1).isEqualTo(lightingData2);
        lightingData2.setId(2L);
        assertThat(lightingData1).isNotEqualTo(lightingData2);
        lightingData1.setId(null);
        assertThat(lightingData1).isNotEqualTo(lightingData2);
    }
}
