package com.library.utils;

import com.library.entities.MessageRequest;
import com.library.entities.OutboundSMSMessageRequest;
import com.library.entities.OutboundSMSTextMessage;
import com.library.message.response.ResponseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.Collections;

@Component
public class SendSMS {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendSMS.class);

    private static final String url = "https://api.orange.com";
    @Value("${orange.client_id}")
    private String clientId;
    @Value("${orange.client_secret}")
    private String clientSecret;
    @Value("${orange.almine_phone_number}")
    private String alminePhoneNumber;
    @Autowired
    private RestTemplate restTemplate;

    private ResponseEntity<ResponseToken> getToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setBasicAuth(clientId, clientSecret, Charset.defaultCharset());

        HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", httpHeaders);

        return sendRequest(url + "/oauth/v3/token", HttpMethod.POST, entity, ResponseToken.class);
    }

    public Object sendSMS(String userPhoneNumber, String message) {
        String token = getToken().getBody().getAccess_token();

        HttpHeaders httpHeaders = getHttpHeaders(token);

        OutboundSMSTextMessage outboundSMSTextMessage = new OutboundSMSTextMessage(message);
        OutboundSMSMessageRequest outboundSMSMessageRequest = new OutboundSMSMessageRequest();
        outboundSMSMessageRequest.setAddress("tel:+221" + userPhoneNumber);
        outboundSMSMessageRequest.setSenderAddress("tel:+" + alminePhoneNumber);
        outboundSMSMessageRequest.setOutboundSMSTextMessage(outboundSMSTextMessage);

        MessageRequest messageRequest = new MessageRequest(outboundSMSMessageRequest);
        HttpEntity<MessageRequest> entity = new HttpEntity<>(messageRequest, httpHeaders);

        String urls = UriComponentsBuilder.fromUriString(url + "/smsmessaging/v1/outbound/tel:+" + alminePhoneNumber + "/requests")
                .toUriString();

        return sendRequest(urls, HttpMethod.POST, entity, Object.class).getBody();
    }

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(token);
        return httpHeaders;
    }

    private <T> ResponseEntity<T> sendRequest(String url, HttpMethod requestMethod, HttpEntity<?> requestEntity, Class<T> responseType) {
        try {
            return restTemplate.exchange(url, requestMethod, requestEntity, responseType);
        } catch (RuntimeException e) {
            LOGGER.error("error URL : {}: {}", url, e.getMessage());
            throw e;
        }
    }

}