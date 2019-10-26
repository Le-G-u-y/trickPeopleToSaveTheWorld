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
        headers.set("Authorization","Bearer eyJhbGciOiJFUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlfdmVyc2lvbiI6IjEuMiIsImFwcF9pZCI6Ijk3QTNGNDJBMTI5OTQ3MEYwNUY2QjhCQiIsImF1ZCI6Imh0dHBzOi8vc2FuZGJveC5yZXN0LWFwaS5oaWdoLW1vYmlsaXR5LmNvbS92MiIsImlhdCI6MTU3MjExNTY4MywianRpIjoiZjJiOTI2ZDAtMDJlNy00ODQxLThhNGUtZTdjZTFiMjYwOGQyIiwiYWNjZXNzX3Rva2VuIjoiYjU1YWY5NWEtNjA3Yy00N2U4LWFkMzctNTAxNmE4YmVkYTYxIn0.KkNxb6Sc7fV1J3kumfaHHHt7Nx1GFoC8h7Yf6MQFGCXkUSfIP-RpSDUPUiU9hfS1TyYO0xNlZstnF8Bj5C_F7Q");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(result);
        return result;

    }
}
