package br.com.iworks.movie.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
