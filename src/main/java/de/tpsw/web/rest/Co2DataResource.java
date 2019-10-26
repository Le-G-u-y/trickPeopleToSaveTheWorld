package de.tpsw.web.rest;

import de.tpsw.domain.Co2Data;
import de.tpsw.service.Co2DataService;
import de.tpsw.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.tpsw.domain.Co2Data}.
 */
@RestController
@RequestMapping("/api")
public class Co2DataResource {

    private final Logger log = LoggerFactory.getLogger(Co2DataResource.class);

    private static final String ENTITY_NAME = "co2Data";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Co2DataService co2DataService;

    public Co2DataResource(Co2DataService co2DataService) {
        this.co2DataService = co2DataService;
    }

    /**
     * {@code POST  /co-2-data} : Create a new co2Data.
     *
     * @param co2DataJsonString the co2Data to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new co2Data, or with status {@code 400 (Bad Request)} if the co2Data has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/co-2-data")
    public ResponseEntity<Co2Data> createCo2Data(@RequestBody String co2DataJsonString) throws URISyntaxException {
        log.debug("REST request to save Co2Data : {}", co2DataJsonString);
        Co2Data result = null;
        try {
            JSONObject jsonObject = new JSONObject(co2DataJsonString);

            Co2Data co2Data = new Co2Data();
            Object co2 = jsonObject.get("co2");
            if (co2 != null) {
                co2Data.setCo2Value((Integer) co2);
            }
            Object hum = jsonObject.get("hum");
            if (hum != null) {
                co2Data.setHumidity(((Double) hum).floatValue());
            }
            Object temp = jsonObject.get("temp");
            if (temp != null) {
                co2Data.setTemp(((Double) temp).floatValue());
            }
            result = co2DataService.save(co2Data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (result != null) {
            return ResponseEntity.created(new URI("/api/co-2-data/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        }
        return null;

    }

    /**
     * {@code PUT  /co-2-data} : Updates an existing co2Data.
     *
     * @param co2Data the co2Data to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated co2Data,
     * or with status {@code 400 (Bad Request)} if the co2Data is not valid,
     * or with status {@code 500 (Internal Server Error)} if the co2Data couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/co-2-data")
    public ResponseEntity<Co2Data> updateCo2Data(@Valid @RequestBody Co2Data co2Data) throws URISyntaxException {
        log.debug("REST request to update Co2Data : {}", co2Data);
        if (co2Data.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Co2Data result = co2DataService.save(co2Data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, co2Data.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /co-2-data} : get all the co2Data.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of co2Data in body.
     */
    @GetMapping("/co-2-data")
    public List<Co2Data> getAllCo2Data() {
        log.debug("REST request to get all Co2Data");
        return co2DataService.findAll();
    }

    /**
     * {@code GET  /co-2-data/:id} : get the "id" co2Data.
     *
     * @param id the id of the co2Data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the co2Data, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/co-2-data/{id}")
    public ResponseEntity<Co2Data> getCo2Data(@PathVariable Long id) {
        log.debug("REST request to get Co2Data : {}", id);
        Optional<Co2Data> co2Data = co2DataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(co2Data);
    }

    /**
     * {@code DELETE  /co-2-data/:id} : delete the "id" co2Data.
     *
     * @param id the id of the co2Data to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/co-2-data/{id}")
    public ResponseEntity<Void> deleteCo2Data(@PathVariable Long id) {
        log.debug("REST request to delete Co2Data : {}", id);
        co2DataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
