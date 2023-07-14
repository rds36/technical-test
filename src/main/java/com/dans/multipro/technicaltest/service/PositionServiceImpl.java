package com.dans.multipro.technicaltest.service;

import com.dans.multipro.technicaltest.configuration.ObjectMapperConfiguration;
import com.dans.multipro.technicaltest.data.model.Position;
import com.dans.multipro.technicaltest.exception.EntityNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    ObjectMapperConfiguration mapperConfiguration;

    @Override
    public List<Position> getAllPosition() {
        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions.json";
        String response = getResponseFromUrl(url);

        log.info("Response: {}", response);

        ObjectMapper mapper = mapperConfiguration.getObjectMapper();
        List<Position> positions;

        try {
            positions = mapper.readValue(response, new TypeReference<List<Position>>() {});
            if (positions == null) {
                throw new EntityNotFoundException(Position.class);
            }
            return positions;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Position getPositionById(UUID id) {
        String url = "http://dev3.dansmultipro.co.id/api/recruitment/positions/{ID}";
        Map<String, String> params = new HashMap<>();
        params.put("ID", id.toString());
        String response = getResponseFromUrl(url, params);

        ObjectMapper mapper = mapperConfiguration.getObjectMapper();
        Position position = null;
        try {
            position = mapper.readValue(response, Position.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return position;
    }

    public String getResponseFromUrl(String url, Map<String, String> params) {
        return WebClient.create(url)
                .get()
                .uri(url, params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(it -> new HttpTimeoutException(it.getMessage()))
                .block();
    }

    public String getResponseFromUrl(String url) {
        return WebClient.create(url)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorMap(it -> new HttpTimeoutException(it.getMessage()))
                .block();
    }
}
