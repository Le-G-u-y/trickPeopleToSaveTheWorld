package de.tpsw.adapter;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class BankDataAdapter {

        public ResponseEntity<String>  requestFlightData(){
        String uri = "https://simulator-api.db.com:443/gw/dbapi/banking/transactions/v1/?iban=DE10010000000000005341";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","Bearer eyJraWQiOiJyc2ExIiwiYWxnIjoiUlM1MTIifQ.eyJzdWIiOiIxMDAxMDAzNTYzMDAwMDEiLCJhenAiOiJkZXZlbG9wZXJwb3J0YWwiLCJpc3MiOiJodHRwczpcL1wvc2ltdWxhdG9yLWFwaS5kYi5jb21cL2d3XC9vaWRjXC8iLCJleHAiOjE1NzIxMTMxMjQsImlhdCI6MTU3MjEwOTUyNCwianRpIjoiODgyZmIwMjEtODdiZi00MTQ1LTk1YTktNTgxOWE5NDNjNGQxIn0.cTiY0rpaDSqjaKL49E12vkD9B2IVzF6lvuJOi0_RVsPNZ7By16hgClS1i416f9v3FJyEs9VGDD6TnNc7p3tr7W7PWuPxcbEG0Rejir44SUkLrmq2MTz54e5TQI6rTYf8-5fIvSRXmNUwHS2j1WmI760F72rKJdVgB5FYPfXOlAUY5YMkX_VgTWBpMNCwehffSOLy8AtpWwg9fqXkW4Rozti5FcVYjlR2a9kM5Cat9oOj-R4AYWoDzZCCZg-IvyQB34WBBetfacyBbth85c3mD0-3HoI_CevstVP8Kmjo6QyVMzZUaBByuDLgf0zTLtru7VPnQcPxmBZVFs5CIyQyGg");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(result);
        return result;

    }
}
