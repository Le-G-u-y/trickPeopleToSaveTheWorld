package de.tpsw.adapter;

import java.util.Arrays;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class FlightDataAdapter {

        public ResponseEntity<String>  requestFlightData(){
        final String uri = "https://fsg-datahub.azure-api.net/flightstate/*";

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
