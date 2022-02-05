package br.com.iworks.movie.omdb.impl;

import br.com.iworks.movie.omdb.OmdbApiGateway;
import br.com.iworks.movie.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.properties.OmdbProperties;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * URL: http://www.omdbapi.com/
 * <p/>
 * Example how to retrieve list of serie per season: http://www.omdbapi.com/?t=Game of Thrones&Season=1
 * <p/>
 * Example how to retrieve list of movies by title: http://www.omdbapi.com/?s=Batman&page=2
 */
@AllArgsConstructor
@Component
@Slf4j
public class OmdbApiGatewayImpl implements OmdbApiGateway {

    private final RestTemplate restTemplate;
    private final OmdbProperties omdbProperties;

    @Override
    public OmdbApiResource findByTitle(String title) {
        OmdbApiResource resource = new OmdbApiResource();

        try {
            URI target = UriComponentsBuilder.fromUriString(omdbProperties.getUrl())
                .queryParam("apikey", omdbProperties.getApiKey())
                .queryParam("t", title)
                .queryParam("plot", "short")
                .queryParam("r", "json")
                .build()
                .toUri();

            log.info("Request to Omdb: {}", target.toString());

            ResponseEntity<OmdbApiResource> omdbApiResource = callApiResource(target);

            resource = omdbApiResource.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Error requesting Omdb API, Status Code: {},  Message: {}, Response: {}",
                ex.getStatusCode(), ex.getMessage(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Error requesting Omdb API, Message: {}", ex.getMessage());
        }

        return resource;
    }

    @Override
    public OmdbApiResource findByTitleAndYear(String title, String year) {
        OmdbApiResource resource = new OmdbApiResource();

        try {
            URI target = UriComponentsBuilder.fromUriString(omdbProperties.getUrl())
                .queryParam("apikey", omdbProperties.getApiKey())
                .queryParam("t", title)
                .queryParam("y", year)
                .queryParam("plot", "short")
                .queryParam("r", "json")
                .build()
                .toUri();

            log.info("Request to Omdb: {}", target.toString());

            ResponseEntity<OmdbApiResource> omdbApiResource = callApiResource(target);

            resource = omdbApiResource.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Error requesting Omdb API, Status Code: {},  Message: {}, Response: {}",
                ex.getStatusCode(), ex.getMessage(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Error requesting Omdb API, Message: {}", ex.getMessage());
        }

        return resource;
    }

    @Override
    public OmdbApiResource findByImdbID(String imdbID) {
        OmdbApiResource resource = new OmdbApiResource();

        try {
            URI target = UriComponentsBuilder.fromUriString(omdbProperties.getUrl())
                .queryParam("apikey", omdbProperties.getApiKey())
                .queryParam("i", imdbID)
                .queryParam("plot", "short")
                .queryParam("r", "json")
                .build()
                .toUri();

            log.info("Request to Omdb: {}", target.toString());

            ResponseEntity<OmdbApiResource> omdbApiResource = callApiResource(target);

            resource = omdbApiResource.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Error requesting Omdb API, Status Code: {},  Message: {}, Response: {}",
                ex.getStatusCode(), ex.getMessage(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Error requesting Omdb API, Message: {}", ex.getMessage());
        }

        return resource;
    }

    @Override
    public OmdbApiSeasonResource findSerieByTitleAndSeason(String title, Integer season) {
        OmdbApiSeasonResource resource = new OmdbApiSeasonResource();

        try {
            URI target = UriComponentsBuilder.fromUriString(omdbProperties.getUrl())
                .queryParam("apikey", omdbProperties.getApiKey())
                .queryParam("t", title)
                .queryParam("Season", season)
                .build()
                .toUri();

            log.info("Request to Omdb: {}", target.toString());

            ResponseEntity<OmdbApiSeasonResource> omdbApiResource = callApiSeasonResource(target);

            resource = omdbApiResource.getBody();
        } catch (HttpStatusCodeException ex) {
            log.error("Error requesting Omdb API, Status Code: {},  Message: {}, Response: {}",
                ex.getStatusCode(), ex.getMessage(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.error("Error requesting Omdb API, Message: {}", ex.getMessage());
        }

        return resource;
    }

    private ResponseEntity<OmdbApiResource> callApiResource(URI target) {
        return restTemplate.exchange(
            target,
            HttpMethod.GET,
            new HttpEntity<>(getHttpHeaders()),
            OmdbApiResource.class);
    }

    private ResponseEntity<OmdbApiSeasonResource> callApiSeasonResource(URI target) {
        return restTemplate.exchange(
            target,
            HttpMethod.GET,
            new HttpEntity<>(getHttpHeaders()),
            OmdbApiSeasonResource.class);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}