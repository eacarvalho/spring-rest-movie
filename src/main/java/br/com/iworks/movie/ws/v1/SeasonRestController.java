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

import br.com.iworks.movie.ws.v1.facade.SeasonFacade;
import br.com.iworks.movie.ws.v1.resource.SeasonResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/series")
@Api("/series")
public class SeasonRestController {

    @Autowired
    private SeasonFacade seasonFacade;

    @ApiOperation(value = "Create a new season of a serie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> create(@ApiParam(value = "Season's json", required = true) @RequestBody @NotNull @Valid SeasonResource seasonResource) {

        return ResponseEntity
                .ok()
                .body(seasonFacade.create(seasonResource));
    }

    @ApiOperation(value = "Update a new season of a serie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ResponseBody
    @RequestMapping(value = "/{title}/season/{number}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SeasonResource> update(@ApiParam(value = "Serie's title", required = true) @Valid @PathVariable String title,
                                                 @ApiParam(value = "Serie's season", required = true) @Valid @PathVariable Integer number,
                                                 @ApiParam(value = "Season's json", required = true) @RequestBody @NotNull @Valid SeasonResource seasonResource) {

        return ResponseEntity
                .ok()
                .body(seasonFacade.update(title, number, seasonResource));
    }
}