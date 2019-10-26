package de.tpsw.adapter;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class ProscheDataAdapter {
    public ResponseEntity<String> requestPorscheData(){
        String uri = "https://sandbox.rest-api.high-mobility.com/v2/usage";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization","Bearer eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlfdmVyc2lvbiI6IjEuMiIsImFwcF9pZCI6Ijk3QTNGNDJBMTI5OTQ3MEYwNUY2QjhCQiIsImF1ZCI6Imh0dHBzOi8vc2FuZGJveC5yZXN0LWFwaS5oaWdoLW1vYmlsaXR5LmNvbS92MiIsImlhdCI6MTU3MjEyMTU2MSwianRpIjoiMjNhZjFkMjgtYjM2Ny00ZjI3LTk5MDItMDJmMzBiODhhOTRhIiwiYWNjZXNzX3Rva2VuIjoiYTQxOGNhNmYtMWZlZS00Y2Q0LWFiNmMtMjcxY2I5ZWQ4YjBiIn0.VrpI4SlxEF63HMk9oE363u-mxwUGbTliPO6jgAMmxjjdK2_G17oWKGYe7wmKn6Vx0HI7I7Nee6EDopThhqtynw");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(result);
        return result;

    }
}
