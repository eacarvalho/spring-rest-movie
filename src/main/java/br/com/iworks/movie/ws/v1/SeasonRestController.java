package br.com.iworks.movie.ws.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import br.com.iworks.movie.model.entity.Season;
import br.com.iworks.movie.service.SeasonService;
import br.com.iworks.movie.ws.v1.assembler.SeasonResourceAssembler;
import br.com.iworks.movie.ws.v1.facade.SeasonFacade;
import br.com.iworks.movie.ws.v1.resource.SeasonResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/series")
@Api("/series")
public class SeasonRestController {

    @Autowired
    private SeasonFacade facade;

    @Autowired
    private SeasonService service;

    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;

    @ApiOperation(value = "Create a new season of a serie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{title}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> create(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                                 @ApiParam(value = "Season's json", required = true) @RequestBody @NotNull @Valid SeasonResource seasonResource) {

        return ResponseEntity
                .ok()
                .body(facade.create(title, seasonResource));
    }

    @ApiOperation(value = "Update a season of one serie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ResponseBody
    @RequestMapping(value = "/{title}/seasons/{season}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> update(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                                 @ApiParam(value = "Serie's season", required = true) @Valid @PathVariable Integer season,
                                                 @ApiParam(value = "Season's json", required = true) @RequestBody @NotNull @Valid SeasonResource seasonResource) {

        return ResponseEntity
                .ok()
                .body(facade.update(title, season, seasonResource));
    }

    @ApiOperation(value = "Get the list of season by title")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @ResponseBody
    @RequestMapping(value = "/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SeasonResource>> list(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                                     @PageableDefault(size = 20) Pageable pageable) {

        Page<Season> seasons = service.list(title, pageable);
        Page<SeasonResource> resources = seasonResourceAssembler.toPage(seasons, true);

        return ResponseEntity.ok().body(resources);
    }

    @ApiOperation(value = "Get the list of all series")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SeasonResource>> listAll(@PageableDefault(size = 20) Pageable pageable,
                                                        @ApiParam(value = "Return serie' episodes", required = false, defaultValue = "true") @Valid boolean expand) {

        Page<Season> seasons = service.list(pageable);
        Page<SeasonResource> resources = seasonResourceAssembler.toPage(seasons, expand);

        return ResponseEntity.ok().body(resources);
    }

    @ApiOperation(value = "Get a list of episodes from one season")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @ResponseBody()
    @RequestMapping(value = "/{title}/seasons/{season}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> read(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                               @ApiParam(value = "Serie's season", required = true) @Valid @PathVariable Integer season) {

        SeasonResource resource = seasonResourceAssembler.toResource(service.read(title, season), true);

        return ResponseEntity.ok().body(resource);
    }

    @ApiOperation(value = "Delete one season of one serie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ResponseBody()
    @RequestMapping(value = "/{title}/seasons/{season}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> delete(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                                 @ApiParam(value = "Serie's season", required = true) @Valid @PathVariable Integer season) {

        SeasonResource resource = seasonResourceAssembler.toResource(service.delete(title, season), true);

        return ResponseEntity.ok().body(resource);
    }
}