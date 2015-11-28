package br.com.iworks.movie.ws;

import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> list() {
        return service.list();
    }

    @ResponseBody()
    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Movie read(@PathVariable("code") @NotNull Long code) {
        return service.read(code);
    }
}
