package br.com.iworks.movie.controller;

import br.com.iworks.movie.model.TypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {

    @GetMapping("/api/types")
    public ResponseEntity<TypeEnum[]> findAll() {
        return new ResponseEntity<>(TypeEnum.values(), HttpStatus.OK);
    }
}