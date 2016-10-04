package br.com.iworks.movie.model.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = Serie.COLLECTION_NAME)
@Data
@EqualsAndHashCode
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "code_serie_idx", def = "{'code': 1}", unique = true),
        @CompoundIndex(name = "episode_serie_idx", def = "{'episodes.episode': 1}", unique = true, sparse = true)
})
public class Serie {

    public static final String COLLECTION_NAME = "series";

    @Id
    private String id;

    @NotNull(message = "{validation.notnull}")
    private Long code;
    @NotNull(message = "{validation.notnull}")
    private String title;
    @NotNull(message = "{validation.notnull}")
    private String originalTitle;
    @NotNull(message = "{validation.notnull}")
    private String season;

    private String totalSeasons;
    private List<Episode> episodes;
}