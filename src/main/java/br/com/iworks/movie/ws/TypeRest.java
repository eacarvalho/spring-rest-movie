package br.com.iworks.movie.ws;

import br.com.iworks.movie.model.TypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/types")
public class TypeRest {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public TypeEnum[] list() {
        return TypeEnum.values();
    }
}