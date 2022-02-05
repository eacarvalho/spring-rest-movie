package br.com.iworks.movie.controller;

import static org.springframework.http.HttpStatus.CREATED;

import br.com.iworks.movie.model.entity.Movie;
import br.com.iworks.movie.model.resource.MovieResource;
import br.com.iworks.movie.service.MovieService;
import br.com.iworks.movie.transformer.MovieResourceMapper;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MovieController {

    private final MovieService movieService;
    private final MovieResourceMapper movieResourceMapper;

    @PostMapping("/api/movies")
    public ResponseEntity<MovieResource> create(@RequestBody @NotNull @Valid MovieResource movieResource,
        @RequestParam(value = "accessImdb", required = false, defaultValue = "true") Boolean accessImdb) {

        Movie movie = movieService.create(movieResource, accessImdb);
        return ResponseEntity.status(CREATED).body(movieResourceMapper.toResource(movie));
    }

    @PutMapping("/api/movies/{code}")
    public ResponseEntity<MovieResource> update(@Valid @PathVariable Long code,
        @RequestBody @NotNull @Valid MovieResource movieResource,
        @RequestParam(value = "accessImdb", required = false, defaultValue = "true") Boolean accessImdb) {

        Movie movie = movieService.update(code, movieResource, accessImdb);
        return ResponseEntity.ok().body(movieResourceMapper.toResource(movie));
    }

    @GetMapping("/api/movies/{code}")
    public ResponseEntity<MovieResource> find(@PathVariable("code") @NotNull Long code) {

        Movie movie = movieService.find(code);
        return ResponseEntity.ok().body(movieResourceMapper.toResource(movie));
    }

    @DeleteMapping("/api/movies/{code}")
    public ResponseEntity<Void> delete(@PathVariable("code") @NotNull Long code) {

        movieService.delete(code);
        return ResponseEntity.noContent().build();
    }
}
