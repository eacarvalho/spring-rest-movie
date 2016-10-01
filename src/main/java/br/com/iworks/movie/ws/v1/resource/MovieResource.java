package br.com.iworks.movie.ws.v1.resource;

import br.com.iworks.movie.config.util.JsonDateTimeSerializer;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class MovieResource {

    private Long code;

    @NotNull(message = "{validation.notnull}")
    private String tittle;
    private String originalTittle;

    @Max(value = 500, message = "{validation.size}")
    @Min(value = 0, message = "{validation.size}")
    private Integer duration;

    @NotNull(message = "{validation.notnull}")
    private TypeEnum type;
    private List<GenreEnum> genres;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date releasedDate;

    @Max(value = 9999, message = "{validation.size}")
    @Min(value = 1800, message = "{validation.size}")
    private Integer year;

    @NotNull(message = "{validation.notnull}")
    private String plot;
    private String directors;

    @Max(value = 5, message = "{validation.size}")
    @Min(value = 0, message = "{validation.size}")
    private int rating;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public void setType(TypeEnum type) {
        this.type = type;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public TypeEnum getType() {
        return type;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public List<GenreEnum> getGenres() {
        return genres;
    }
}