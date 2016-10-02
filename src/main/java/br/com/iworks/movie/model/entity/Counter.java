package br.com.iworks.movie.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counters")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Counter {

    @Id
    private String id;
    private Long seq;
}