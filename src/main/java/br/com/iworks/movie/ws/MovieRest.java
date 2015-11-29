package br.com.iworks.movie.ws;

import br.com.iworks.movie.dto.MovieDTO;
import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieRest {

    @Autowired
    private MovieService service;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Movie create(@RequestBody @NotNull @Valid Movie movie) {
        return service.create(movie);
    }

    @ResponseBody
    @RequestMapping(value = "/{code}", method = RequestMethod.PUT)
    public ResponseEntity<Movie> update(@Valid @PathVariable Long code, @RequestBody @NotNull @Valid Movie movie) {
        Movie movieReturned = service.update(code, movie);

        if (movieReturned == null) {
            return new ResponseEntity<Movie>(movieReturned, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Movie>(movieReturned, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
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

    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ResponseEntity<Movie> read(@PathVariable("code") @NotNull Long code) {
        Movie movie = service.read(code);

        if (movie == null) {
            return new ResponseEntity<Movie>(movie, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
    public ResponseEntity<Movie> delete(@PathVariable Long code) {
        Movie movie = service.delete(code);

        if (movie == null) {
            return new ResponseEntity<Movie>(movie, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }
}
