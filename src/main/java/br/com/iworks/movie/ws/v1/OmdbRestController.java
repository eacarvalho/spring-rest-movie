package br.com.iworks.movie.ws.v1;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.service.OmdbApiService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/omdb-movies")
@Api("/api/omdb-movies")
public class OmdbRestController {

    @Autowired
    private OmdbApiService service;

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
                .body(service.findMovie(null, title, null));
    }

    @ApiOperation(value = "Get movie detail by Omdb API using query filter - http://www.omdbapi.com/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "Movie's english title", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "Movie's year", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "imdbID", value = "Movie's imdb", required = false, dataType = "string", paramType = "query")
    })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OmdbApiResource> listByFilter(WebRequest webRequest) {

        String title = webRequest.getParameter("title");
        String year = webRequest.getParameter("year");
        String imdbID = webRequest.getParameter("imdbID");

        return ResponseEntity
                .ok()
                .body(service.findMovie(imdbID, title, year));
    }

    @ApiOperation(value = "Get series's season detail by Omdb API - http://www.omdbapi.com/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseBody
    @RequestMapping(value = "/{title}/seasons/{season}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OmdbApiSeasonResource> listSerieByNameAndSeason(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                                                          @ApiParam(value = "Serie's season", required = true) @Valid @PathVariable Integer season) {

        return ResponseEntity
                .ok()
                .body(service.findSeason(title, season));
    }
}