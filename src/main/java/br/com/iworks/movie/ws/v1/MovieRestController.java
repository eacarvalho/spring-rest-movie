package br.com.iworks.movie.ws.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.context.request.WebRequest;

import br.com.iworks.movie.model.TypeEnum;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import br.com.iworks.movie.ws.v1.assembler.MovieResourceAssembler;
import br.com.iworks.movie.ws.v1.facade.MovieFacade;
import br.com.iworks.movie.ws.v1.resource.MovieResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/movies")
@Api("/api/movies")
public class MovieRestController {

    @Autowired
    private MovieFacade movieFacade;

    @Autowired
    private MovieService service;

    @Autowired
    private MovieResourceAssembler resourceAssembler;

    @ApiOperation(value = "Create a new movie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessImdb", value = "Save movie based on Imdb API", required = false, defaultValue = "true", dataType = "boolean", paramType = "query")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> create(@ApiParam(value = "Movie's json", required = true) @RequestBody @NotNull @Valid MovieResource movieResource, WebRequest webRequest) {

        MovieResource resource = null;
        String accessImdb = webRequest.getParameter("accessImdb");

        if (StringUtils.isNotBlank(accessImdb) && accessImdb.equalsIgnoreCase("false")) {
            resource = resourceAssembler.toResource(service.create(resourceAssembler.toModel(movieResource)));
        } else {
            resource = movieFacade.create(movieResource);
        }

        return ResponseEntity.ok().body(resource);
    }

    @ApiOperation(value = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessImdb", value = "Update movie based on Imdb API", required = false, defaultValue = "true", dataType = "boolean", paramType = "query")
    })
    @ResponseBody
    @RequestMapping(value = "/{code}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> update(@ApiParam(value = "Movie's code", required = true) @Valid @PathVariable Long code,
                                                @ApiParam(value = "Movie's json", required = true) @RequestBody @NotNull @Valid MovieResource movieResource,
                                                WebRequest webRequest) {

        MovieResource resource = null;
        String accessImdb = webRequest.getParameter("accessImdb");

        if (StringUtils.isNotBlank(accessImdb) && accessImdb.equalsIgnoreCase("false")) {
            resource = resourceAssembler.toResource(service.update(code, resourceAssembler.toModel(movieResource)));
        } else {
            resource = movieFacade.update(code, movieResource);
        }

        return ResponseEntity.ok().body(resource);
    }

    @ApiOperation(value = "Get the list of all movies or by query filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "Movie's title", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "originalTitle", value = "Movie's original title", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "Movie's type", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<MovieResource>> listByFilter(WebRequest webRequest, @PageableDefault(size = 20) Pageable pageable) {
        MovieResource movieResource = new MovieResource();

        if (webRequest != null) {
            movieResource.setTitle(webRequest.getParameter("title"));
            movieResource.setOriginalTitle(webRequest.getParameter("originalTitle"));
            movieResource.setType(TypeEnum.create(webRequest.getParameter("type")));
        }

        Page<Movie> movies = service.list(resourceAssembler.toModel(movieResource), pageable);
        Page<MovieResource> resources = resourceAssembler.toPage(movies);

        return ResponseEntity.ok().body(resources);
    }

    @ApiOperation(value = "Get a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> read(@ApiParam(value = "Movie's code", required = true) @PathVariable("code") @NotNull Long code) {
        MovieResource resource = resourceAssembler.toResource(service.read(code));

        return ResponseEntity.ok().body(resource);
    }

    @ApiOperation(value = "Delete a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieResource> delete(@ApiParam(value = "Movie's code", required = true) @PathVariable Long code) {
        MovieResource resource = resourceAssembler.toResource(service.delete(code));

        return ResponseEntity.ok().body(resource);
    }
}