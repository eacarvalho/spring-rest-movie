package br.com.iworks.movie.model.entity;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Document(collection = Season.COLLECTION_NAME)
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "title_number_season_idx", def = "{'title': 1, 'number': 1}", unique = true)
})
public class Season {

    public static final String COLLECTION_NAME = "seasons";

    @Id
    private String id;

    @NotNull(message = "{validation.notnull}")
    private String title;
    @NotNull(message = "{validation.notnull}")
    private Integer number;

    @Min(value = 0, message = "{validation.size}")
    @Max(value = 5, message = "{validation.size}")
    private Integer rating;

    private String totalSeasons;
    private List<Episode> episodes;
}