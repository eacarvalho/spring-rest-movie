package br.com.iworks.movie.controller;

import br.com.iworks.movie.model.GenreEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {

    @GetMapping("/api/genres")
    public ResponseEntity<GenreEnum[]> findAll() {
        return new ResponseEntity<>(GenreEnum.values(), HttpStatus.OK);
    }
}