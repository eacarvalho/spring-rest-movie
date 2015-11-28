package br.com.iworks.movie.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counters")
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Counter {

    @Id
    private String id;
    private Long seq;
}
