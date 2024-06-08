package com.restful.clinic.service;
import com.restful.clinic.entities.SatuSehatAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class SatuSehatService {
    @Autowired
    private SatuSehatAuth satuSehatAuth;

    private String accessToken;
    public String getAccessToken() {
        if (accessToken == null) {
            fetchAccessToken();
        }
        return accessToken;
    }

    private void fetchAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        String body = "client_id=" + satuSehatAuth.getClientId() +
                "&client_secret=" + satuSehatAuth.getClientSecret();

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                satuSehatAuth.getTokenUrl(), HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            accessToken = (String) responseBody.get("access_token");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Failed to fetch access token");
        }
    }
}
