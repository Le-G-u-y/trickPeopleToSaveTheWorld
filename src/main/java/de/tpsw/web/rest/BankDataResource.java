package de.tpsw.web.rest;

import de.tpsw.domain.LightingData;
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
 * REST controller for managing {@link LightingData}.
 */
@RestController
@RequestMapping("/api")
public class BankDataResource {

    private final Logger log = LoggerFactory.getLogger(BankDataResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public BankDataResource() {

    }

    /**
     * {@code POST  /lighting-data} : Create a new lightingData.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lightingData, or with status {@code 400 (Bad Request)} if the lightingData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @GetMapping("/banking-data")
    public void testBankingData() {
        log.debug("REST request to get BankingData ");
        int indexChange = new BankDataEvaluationService().calculateIndexChange();
        log.info("indexChange: "+ indexChange);

    }

}
