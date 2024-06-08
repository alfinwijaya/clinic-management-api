package com.restful.clinic.entities;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class SatuSehatAuth {
    @Value("${oauth.client_id}")
    private String clientId;

    @Value("${oauth.client_secret}")
    private String clientSecret;

    @Value("${oauth.token_url}")
    private String tokenUrl;
}
