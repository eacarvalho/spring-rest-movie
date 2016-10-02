package br.com.iworks.movie.ws.v1;

import br.com.iworks.movie.gateway.omdb.OmdbApiGateway;
import br.com.iworks.movie.gateway.omdb.resource.OmdbApiResource;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/omdb")
@Api("/omdb")
public class OmdbRest {

    @Autowired
    private OmdbApiGateway gateway;

    @ApiOperation(value = "Get movie detail by Omdb API - http://www.omdbapi.com/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "Movie's english title", required = true, dataType = "string", paramType = "path")
    })
    @ResponseBody()
    @RequestMapping(value = "/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OmdbApiResource> read(@PathVariable("title") @NotNull String title) {
        return ResponseEntity.ok().body(gateway.findByTitle(title));
    }
}