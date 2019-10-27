package de.tpsw.web.rest;

import de.tpsw.TrickPeopleToSaveTheWorldApp;
import de.tpsw.domain.Co2Data;
import de.tpsw.repository.Co2DataRepository;
import de.tpsw.service.Co2DataService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static de.tpsw.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link Co2DataResource} REST controller.
 */
@SpringBootTest(classes = TrickPeopleToSaveTheWorldApp.class)
public class Co2DataResourceIT {

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CO_2_VALUE = 1;
    private static final Integer UPDATED_CO_2_VALUE = 2;

    private static final Float DEFAULT_TEMP = 1F;
    private static final Float UPDATED_TEMP = 2F;

    private static final Float DEFAULT_HUMIDITY = 1F;
    private static final Float UPDATED_HUMIDITY = 2F;

    @Autowired
    private Co2DataRepository co2DataRepository;

    @Autowired
    private Co2DataService co2DataService;

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

    private MockMvc restCo2DataMockMvc;

    private Co2Data co2Data;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Co2DataResource co2DataResource = new Co2DataResource(co2DataService);
        this.restCo2DataMockMvc = MockMvcBuilders.standaloneSetup(co2DataResource)
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
    public static Co2Data createEntity(EntityManager em) {
        Co2Data co2Data = new Co2Data()
            .timestamp(DEFAULT_TIMESTAMP)
            .co2Value(DEFAULT_CO_2_VALUE)
            .temp(DEFAULT_TEMP)
            .humidity(DEFAULT_HUMIDITY);
        return co2Data;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Co2Data createUpdatedEntity(EntityManager em) {
        Co2Data co2Data = new Co2Data()
            .timestamp(UPDATED_TIMESTAMP)
            .co2Value(UPDATED_CO_2_VALUE)
            .temp(UPDATED_TEMP)
            .humidity(UPDATED_HUMIDITY);
        return co2Data;
    }

    @BeforeEach
    public void initTest() {
        co2Data = createEntity(em);
    }

    @Test
    @Transactional
    public void createCo2Data() throws Exception {
        int databaseSizeBeforeCreate = co2DataRepository.findAll().size();

        // Create the Co2Data
        restCo2DataMockMvc.perform(post("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(co2Data)))
            .andExpect(status().isCreated());

        // Validate the Co2Data in the database
        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeCreate + 1);
        Co2Data testCo2Data = co2DataList.get(co2DataList.size() - 1);
        assertThat(testCo2Data.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testCo2Data.getCo2Value()).isEqualTo(DEFAULT_CO_2_VALUE);
        assertThat(testCo2Data.getTemp()).isEqualTo(DEFAULT_TEMP);
        assertThat(testCo2Data.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
    }

    @Test
    @Transactional
    public void createCo2DataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = co2DataRepository.findAll().size();

        // Create the Co2Data with an existing ID
        co2Data.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCo2DataMockMvc.perform(post("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(co2Data)))
            .andExpect(status().isBadRequest());

        // Validate the Co2Data in the database
        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = co2DataRepository.findAll().size();
        // set the field null
        co2Data.setTimestamp(null);

        // Create the Co2Data, which fails.

        restCo2DataMockMvc.perform(post("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(co2Data)))
            .andExpect(status().isBadRequest());

        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCo2ValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = co2DataRepository.findAll().size();
        // set the field null
        co2Data.setCo2Value(null);

        // Create the Co2Data, which fails.

        restCo2DataMockMvc.perform(post("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(co2Data)))
            .andExpect(status().isBadRequest());

        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempIsRequired() throws Exception {
        int databaseSizeBeforeTest = co2DataRepository.findAll().size();
        // set the field null
        co2Data.setTemp(null);

        // Create the Co2Data, which fails.

        restCo2DataMockMvc.perform(post("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(co2Data)))
            .andExpect(status().isBadRequest());

        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCo2Data() throws Exception {
        // Initialize the database
        co2DataRepository.saveAndFlush(co2Data);

        // Get all the co2DataList
        restCo2DataMockMvc.perform(get("/api/co-2-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(co2Data.getId().intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].co2Value").value(hasItem(DEFAULT_CO_2_VALUE)))
            .andExpect(jsonPath("$.[*].temp").value(hasItem(DEFAULT_TEMP.doubleValue())))
            .andExpect(jsonPath("$.[*].humidity").value(hasItem(DEFAULT_HUMIDITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getCo2Data() throws Exception {
        // Initialize the database
        co2DataRepository.saveAndFlush(co2Data);

        // Get the co2Data
        restCo2DataMockMvc.perform(get("/api/co-2-data/{id}", co2Data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(co2Data.getId().intValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.co2Value").value(DEFAULT_CO_2_VALUE))
            .andExpect(jsonPath("$.temp").value(DEFAULT_TEMP.doubleValue()))
            .andExpect(jsonPath("$.humidity").value(DEFAULT_HUMIDITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCo2Data() throws Exception {
        // Get the co2Data
        restCo2DataMockMvc.perform(get("/api/co-2-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCo2Data() throws Exception {
        // Initialize the database
        co2DataService.save(co2Data);

        int databaseSizeBeforeUpdate = co2DataRepository.findAll().size();

        // Update the co2Data
        Co2Data updatedCo2Data = co2DataRepository.findById(co2Data.getId()).get();
        // Disconnect from session so that the updates on updatedCo2Data are not directly saved in db
        em.detach(updatedCo2Data);
        updatedCo2Data
            .timestamp(UPDATED_TIMESTAMP)
            .co2Value(UPDATED_CO_2_VALUE)
            .temp(UPDATED_TEMP)
            .humidity(UPDATED_HUMIDITY);

        restCo2DataMockMvc.perform(put("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCo2Data)))
            .andExpect(status().isOk());

        // Validate the Co2Data in the database
        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeUpdate);
        Co2Data testCo2Data = co2DataList.get(co2DataList.size() - 1);
        assertThat(testCo2Data.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testCo2Data.getCo2Value()).isEqualTo(UPDATED_CO_2_VALUE);
        assertThat(testCo2Data.getTemp()).isEqualTo(UPDATED_TEMP);
        assertThat(testCo2Data.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCo2Data() throws Exception {
        int databaseSizeBeforeUpdate = co2DataRepository.findAll().size();

        // Create the Co2Data

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCo2DataMockMvc.perform(put("/api/co-2-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(co2Data)))
            .andExpect(status().isBadRequest());

        // Validate the Co2Data in the database
        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCo2Data() throws Exception {
        // Initialize the database
        co2DataService.save(co2Data);

        int databaseSizeBeforeDelete = co2DataRepository.findAll().size();

        // Delete the co2Data
        restCo2DataMockMvc.perform(delete("/api/co-2-data/{id}", co2Data.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Co2Data> co2DataList = co2DataRepository.findAll();
        assertThat(co2DataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Co2Data.class);
        Co2Data co2Data1 = new Co2Data();
        co2Data1.setId(1L);
        Co2Data co2Data2 = new Co2Data();
        co2Data2.setId(co2Data1.getId());
        assertThat(co2Data1).isEqualTo(co2Data2);
        co2Data2.setId(2L);
        assertThat(co2Data1).isNotEqualTo(co2Data2);
        co2Data1.setId(null);
        assertThat(co2Data1).isNotEqualTo(co2Data2);
    }
}
