package br.com.iworks.movie.ws.v1;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import br.com.iworks.movie.ws.v1.assembler.MovieResourceAssembler;
import br.com.iworks.movie.ws.v1.resource.MovieResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/movies")
@Api("/movies")
public class MovieRest {

    @Autowired
    private MovieService service;

    @Autowired
    private MovieResourceAssembler movieResourceAssembler;

    @ApiOperation(value = "Create a new movie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movieResource", value = "Movie's json", required = false, dataType = "MovieResource", paramType = "body")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> create(@RequestBody @NotNull @Valid MovieResource movieResource) {
        Movie movie = movieResourceAssembler.toModel(movieResource);

        return ResponseEntity
                .ok()
                .body(movieResourceAssembler.toResource(service.create(movie)));
    }

    @ApiOperation(value = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Movie's code", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "movieResource", value = "Movie's json", required = false, dataType = "MovieResource", paramType = "body")
    })
    @ResponseBody
    @RequestMapping(value = "/{code}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> update(@Valid @PathVariable Long code, @RequestBody @NotNull @Valid MovieResource movieResource) {
        Movie movie = movieResourceAssembler.toModel(movieResource);
        MovieResource resource = movieResourceAssembler.toResource(service.update(code, movie));

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity
                .ok()
                .body(resource);
    }

    @ApiOperation(value = "Get the list of movies")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tittle", value = "Movie's tittle", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "originalTittle", value = "Movie's original tittle", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "Movie's type", required = false, dataType = "string", paramType = "query")
    })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovieResource>> list(WebRequest webRequest) {
        MovieResource movieResource = new MovieResource();

        if (webRequest != null) {
            movieResource.setTittle(webRequest.getParameter("tittle"));
            movieResource.setOriginalTittle(webRequest.getParameter("originalTittle"));
            movieResource.setType(TypeEnum.create(webRequest.getParameter("type")));
        }

        List<Movie> movies = service.list(movieResourceAssembler.toModel(movieResource));
        List<MovieResource> resources = movieResourceAssembler.toResources(movies);

        if (CollectionUtils.isEmpty(resources)) {
            return new ResponseEntity<>(resources, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity
                .ok()
                .body(resources);
    }

    @ApiOperation(value = "Get a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Movie's code", required = true, dataType = "long", paramType = "path")
    })
    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> read(@PathVariable("code") @NotNull Long code) {
        MovieResource resource = movieResourceAssembler.toResource(service.read(code));

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity
                .ok()
                .body(resource);
    }

    @ApiOperation(value = "Delete a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Movie's code", required = true, dataType = "long", paramType = "path")
    })
    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> delete(@PathVariable Long code) {
        MovieResource resource = movieResourceAssembler.toResource(service.delete(code));

        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity
                .ok()
                .body(resource);
    }
}