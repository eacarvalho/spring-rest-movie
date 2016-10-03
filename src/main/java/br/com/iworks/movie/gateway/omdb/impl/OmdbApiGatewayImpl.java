package br.com.iworks.movie.gateway.omdb.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import lombok.extern.slf4j.Slf4j;

/**
 * URL: http://www.omdbapi.com/
 * <p/>
 * Implementar consultar por séries como http://www.omdbapi.com/?t=Game of Thrones&Season=1
 * para trazer todas as datas de lançamentos dos episódios
 * <p/>
 * Implementar consulta com todos os filmes de determinado título
 * como http://www.omdbapi.com/?s=Batman&page=2
 */
@Service
@Slf4j
public class OmdbApiGatewayImpl implements OmdbApiGateway {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gateway.omdb.url}")
    private String omdbUrl;

    @Override
    public OmdbApiResource findByTitle(String title) {
        OmdbApiResource resource = new OmdbApiResource();

        try {
            URI target = UriComponentsBuilder.fromUriString(omdbUrl)
                    .queryParam("t", title)
                    .queryParam("plot", "short")
                    .queryParam("r", "json")
                    .build()
                    .toUri();

            log.info("Resquest to Omdb: {}", target.toString());

            ResponseEntity<OmdbApiResource> omdbApiResource = restTemplate.exchange(
                    target,
                    HttpMethod.GET,
                    new HttpEntity<>(getHttpHeaders()),
                    OmdbApiResource.class);

            resource = omdbApiResource.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Error requesting Omdb API, Status Code: {},  Message: {}, Response: {}",
                    ex.getStatusCode(), ex.getMessage(), ex.getResponseBodyAsString());
        }

        return resource;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}