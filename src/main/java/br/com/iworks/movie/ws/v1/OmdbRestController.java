package br.com.iworks.movie.ws.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.ws.v1.facade.OmdbFacade;
import br.com.iworks.movie.ws.v1.resource.MovieResource;
import br.com.iworks.movie.ws.v1.resource.OmdbRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/omdb-movies")
@Api("/omdb-movies")
public class OmdbRestController {

    @Autowired
    private OmdbApiGateway gateway;

    @Autowired
    private OmdbFacade omdbFacade;

    @ApiOperation(value = "Create a new movie based on IMDB movie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> create(@ApiParam(value = "IMDB movie's json", required = true) @RequestBody @NotNull @Valid OmdbRequest omdbRequest) {

        return ResponseEntity
                .ok()
                .body(omdbFacade.createMovieByImdb(omdbRequest));
    }

    @ApiOperation(value = "Get movie detail by Omdb API - http://www.omdbapi.com/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @ResponseBody()
    @RequestMapping(value = "/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OmdbApiResource> read(@ApiParam(value = "Movie's english title", required = true) @PathVariable("title") @NotNull String title) {

        return ResponseEntity
                .ok()
                .body(gateway.findByTitle(title));
    }
}