package de.tpsw.web.rest;

import de.tpsw.domain.LightingData;
import de.tpsw.repository.LightingDataRepository;
import de.tpsw.service.BankDataEvaluationService;
import de.tpsw.service.LightingDataService;
import de.tpsw.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.tpsw.domain.LightingData}.
 */
@RestController
@RequestMapping("/api")
public class LightingDataResource {

    private final Logger log = LoggerFactory.getLogger(LightingDataResource.class);

    private static final String ENTITY_NAME = "lightingData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LightingDataService lightingDataService;

    public LightingDataResource(LightingDataService lightingDataService) {
        this.lightingDataService = lightingDataService;
    }

    /**
     * {@code POST  /lighting-data} : Create a new lightingData.
     *
     * @param lightingDataJson the lightingData to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lightingData, or with status {@code 400 (Bad Request)} if the lightingData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lighting-data")
    public ResponseEntity<LightingData> createLightingData(@RequestBody String lightingDataJson) throws URISyntaxException {
        log.debug("REST request to save LightingData : {}", lightingDataJson);
        try {
            JSONObject json = new JSONObject(lightingDataJson);

        LightingData lightingData = new LightingData();
        Boolean on = json.getBoolean("on");
        if(Boolean.TRUE.equals(on)){
            lightingData.setOnSeconds(10l);
        }
        else{
            lightingData.setOffSeconds(10l);
        }
        LightingData result = lightingDataService.save(lightingData);
        return ResponseEntity.created(new URI("/api/lighting-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@code PUT  /lighting-data} : Updates an existing lightingData.
     *
     * @param lightingData the lightingData to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lightingData,
     * or with status {@code 400 (Bad Request)} if the lightingData is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lightingData couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lighting-data")
    public ResponseEntity<LightingData> updateLightingData(@RequestBody LightingData lightingData) throws URISyntaxException {
        log.debug("REST request to update LightingData : {}", lightingData);
        if (lightingData.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LightingData result = lightingDataService.save(lightingData);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lightingData.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lighting-data} : get all the lightingData.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lightingData in body.
     */
    @GetMapping("/lighting-data")
    public List<LightingData> getAllLightingData() {
        log.debug("REST request to get all LightingData");
        return lightingDataService.findAll();
    }

    /**
     * {@code GET  /lighting-data/:id} : get the "id" lightingData.
     *
     * @param id the id of the lightingData to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lightingData, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lighting-data/{id}")
    public ResponseEntity<LightingData> getLightingData(@PathVariable Long id) {
        log.debug("REST request to get LightingData : {}", id);
        Optional<LightingData> lightingData = lightingDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lightingData);
    }

    /**
     * {@code DELETE  /lighting-data/:id} : delete the "id" lightingData.
     *
     * @param id the id of the lightingData to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lighting-data/{id}")
    public ResponseEntity<Void> deleteLightingData(@PathVariable Long id) {
        log.debug("REST request to delete LightingData : {}", id);
        lightingDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/banking-data")
    public void testBankingData() {
        log.debug("REST request to get BankingData ");
        int indexChange = new BankDataEvaluationService().calculateIndexChange();
        log.info("indexChange: "+ indexChange);

    }
}
