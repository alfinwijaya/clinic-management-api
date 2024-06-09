package com.restful.clinic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.clinic.model.GetMedicineParam;
import com.restful.clinic.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class MedicineService {
    @Autowired
    private SatuSehatService satuSehatService;

    @Autowired
    private ObjectMapper objectMapper;

    public WebResponse<Object> getAll(GetMedicineParam param) throws Exception {
        final String baseUri = "https://api-satusehat-stg.kemkes.go.id/kfa-v2/products/all";

        String page = (param.getPage() != null) ? param.getPage() : "1";
        String size = (param.getSize() != null) ? param.getSize() : "10";

        // Build the URI with query parameters from GetMedicineParam
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUri)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("product_type", "farmasi"); // Assuming this is always required

        if (param.getFrom_date() != null) {
            uriBuilder.queryParam("from_date", param.getFrom_date());
        }
        if (param.getTo_date() != null) {
            uriBuilder.queryParam("to_date", param.getTo_date());
        }
        if (param.getFarmalkes_type() != null) {
            uriBuilder.queryParam("farmalkes_type", param.getFarmalkes_type());
        }
        if (param.getKeyword() != null) {
            uriBuilder.queryParam("keyword", param.getKeyword());
        }
        if (param.getTemplate_code() != null) {
            uriBuilder.queryParam("template_code", param.getTemplate_code());
        }
        if (param.getPackaging_code() != null) {
            uriBuilder.queryParam("packaging_code", param.getPackaging_code());
        }

        String uri = uriBuilder.toUriString();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            Object json = objectMapper.readValue(response.getBody(), Object.class);

            return WebResponse.<Object>builder().data(json).build();
        } catch (HttpStatusCodeException e) {
            // Convert the response body to a Map to extract error details
            String responseBody = e.getResponseBodyAsString();
            Map<String, Object> errorResponse = objectMapper.readValue(responseBody, Map.class);

            int status = (int) errorResponse.get("status");
            String message = (String) errorResponse.get("message");

            throw new ResponseStatusException(HttpStatus.valueOf(status), message);
        }
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + satuSehatService.getAccessToken());
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
