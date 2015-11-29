package br.com.iworks.movie.dto;

import br.com.iworks.movie.infra.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class MovieDTO implements Serializable {

    private String tittle;
    private String originalTitle;
    private Integer duration;
    private String type;
    private String category;
    private int rating;
}
