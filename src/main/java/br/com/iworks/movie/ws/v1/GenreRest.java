package br.com.iworks.movie.ws.v1;

import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.entity.Movie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
@Api("/genres")
public class GenreRest {

    @ApiOperation(value = "Get the list of genres")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = GenreEnum.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad Request")})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public GenreEnum[] list() {
        return GenreEnum.values();
    }
}