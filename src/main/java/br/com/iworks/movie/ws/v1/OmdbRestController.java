package br.com.iworks.movie.ws.v1;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiSeasonResource;
import br.com.iworks.movie.service.OmdbApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/omdb-movies")
@Api("/omdb-movies")
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

    @ApiOperation(value = "Get movie detail by Omdb API using filter - http://www.omdbapi.com/")
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
    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OmdbApiResource> listByFilter(WebRequest webRequest) {

        String title = webRequest.getParameter("title");
        Integer year = StringUtils.isNotBlank(webRequest.getParameter("year")) ? Integer.parseInt(webRequest.getParameter("year")) : null;
        String imdbID = webRequest.getParameter("imdbID");

        return ResponseEntity
                .ok()
                .body(service.findMovie(imdbID, title, year));
    }

    @ApiOperation(value = "Get serie detail by Omdb API using filter - http://www.omdbapi.com/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "Serie's english title", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "season", value = "Serie's season", required = false, dataType = "integer", paramType = "query")
    })
    @ResponseBody
    @RequestMapping(value = "/serie/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OmdbApiSeasonResource> listSerieByFilter(WebRequest webRequest) {

        String title = webRequest.getParameter("title");
        String season = webRequest.getParameter("season");

        return ResponseEntity
                .ok()
                .body(service.findSerie(title, season));
    }
}