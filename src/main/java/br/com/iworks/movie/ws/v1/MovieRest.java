package br.com.iworks.movie.ws.v1;

import br.com.iworks.movie.dto.MovieDTO;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/movies")
@Api("/movies")
public class MovieRest {

    @Autowired
    private MovieService service;

    @ApiOperation(value = "Create a new movie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 409, message = "Conflict")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movie", value = "Movie's json", required = false, dataType = "Movie", paramType = "body")
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Movie create(@RequestBody @NotNull @Valid Movie movie) {
        return service.create(movie);
    }

    @ApiOperation(value = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "Movie's code", required = true, dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "movie", value = "Movie's json", required = false, dataType = "Movie", paramType = "body")
    })
    @ResponseBody
    @RequestMapping(value = "/{code}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> update(@Valid @PathVariable Long code, @RequestBody @NotNull @Valid Movie movie) {
        Movie movieReturned = service.update(code, movie);

        if (movieReturned == null) {
            return new ResponseEntity<Movie>(movieReturned, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Movie>(movieReturned, HttpStatus.OK);
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
    public ResponseEntity<List<Movie>> list(WebRequest webRequest) {
        MovieDTO movieDTO = new MovieDTO();

        if (webRequest != null) {
            movieDTO.setTittle(webRequest.getParameter("tittle"));
            movieDTO.setOriginalTitle(webRequest.getParameter("originalTittle"));
            movieDTO.setType(webRequest.getParameter("type"));
        }

        List<Movie> movies = service.list(movieDTO);

        if (CollectionUtils.isEmpty(movies)) {
            return new ResponseEntity<List<Movie>>(movies, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Movie>>(movies, HttpStatus.OK);
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
    public ResponseEntity<Movie> read(@PathVariable("code") @NotNull Long code) {
        Movie movie = service.read(code);

        if (movie == null) {
            return new ResponseEntity<Movie>(movie, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
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
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> delete(@PathVariable Long code) {
        Movie movie = service.delete(code);

        if (movie == null) {
            return new ResponseEntity<Movie>(movie, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }
}