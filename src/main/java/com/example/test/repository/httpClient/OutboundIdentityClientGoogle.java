package com.example.test.repository.httpClient;


import com.example.test.dto.request.ExchangeTokenGoogleRequest;
import com.example.test.dto.response.httpClient.ExchangeTokenGoogleResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "outbound-google-identity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClientGoogle {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenGoogleResponse exchangeToken(@QueryMap ExchangeTokenGoogleRequest request);
}
