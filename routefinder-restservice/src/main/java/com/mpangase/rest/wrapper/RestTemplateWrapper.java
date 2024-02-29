package com.mpangase.rest.wrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class RestTemplateWrapper {
    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ObjectMapper mapper;


    public <T> T post(String url, String transactionId, @Nullable HttpEntity<?> requestEntity, Class<T> responseType)
            throws Exception {
        return exchange(url, HttpMethod.POST, transactionId, requestEntity, responseType);
    }

    public <T> T put(String url, String transactionId, @Nullable HttpEntity<?> requestEntity, Class<T> responseType)
            throws Exception {
        return exchange(url, HttpMethod.PUT, transactionId, requestEntity, responseType);
    }

    public <T> T get(String url, String transactionId, @Nullable HttpEntity entity, Class<T> responseType) throws Exception {
        return exchange(url, HttpMethod.GET, transactionId, entity, responseType);
    }

    private <T> T exchange(
            String url, HttpMethod method, String transactionId,  @Nullable HttpEntity<?> requestEntity, Class<T> responseType)
            throws Exception {
        try {
            customLogger.LogRequest(url, transactionId, Objects.requireNonNull(requestEntity).getBody());
            ResponseEntity<?> response =
                    restTemplate.exchange(url, method, requestEntity, Object.class);
            T body = mapper.convertValue(response.getBody(), responseType);
            customLogger.LogResponse(url, transactionId, body);
            if (body != null) {
                if (body.getClass() == responseType) {
                    return body;
                }
                throw new Exception();
            }

        } catch (HttpStatusCodeException e) {
            try {
                if(e.getStatusCode().is4xxClientError()){
                   throw e;
                } else {

                    String msg = e.getResponseBodyAsString();
                    T error = mapper.readValue(msg, responseType);
                    customLogger.LogError(transactionId, error);
                    return error;
                }
            } catch (JsonProcessingException var3) {
                throw new Exception(e.getMessage(), e.getCause());
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

}

