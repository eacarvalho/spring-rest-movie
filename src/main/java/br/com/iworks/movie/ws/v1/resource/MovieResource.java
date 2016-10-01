package br.com.iworks.movie.ws.v1.resource;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.iworks.movie.config.util.JsonDateSerializer;
import br.com.iworks.movie.model.GenreEnum;
import br.com.iworks.movie.model.TypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private Date releasedDate;

    @Max(value = 9999, message = "{validation.size}")
    @Min(value = 1800, message = "{validation.size}")
    private Integer year;

    @NotNull(message = "{validation.notnull}")
    private String plot;
    private String directors;
    private int rating;

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getReleasedDate() {
        return releasedDate;
    }

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