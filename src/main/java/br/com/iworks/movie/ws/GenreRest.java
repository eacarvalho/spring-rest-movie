package br.com.iworks.movie.ws;

import br.com.iworks.movie.model.GenreEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenreRest {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public GenreEnum[] list() {
        return GenreEnum.values();
    }
}