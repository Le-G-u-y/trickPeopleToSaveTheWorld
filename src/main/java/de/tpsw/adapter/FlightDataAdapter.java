package de.tpsw.adapter;

import java.util.Arrays;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FlightDataAdapter {

        public ResponseEntity<String>  requestCarData(String amsId){
        String uri = "http://fsg-datahub.azure-api.net/legacy/Apps/AirportSTR/Flights/Get/";
        uri = uri + amsId;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Ocp-Apim-Subscription-Key","d4c18e176491437f84a8afc11ce75aae");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(result);
        return result;

    }
}
