package br.com.iworks.movie.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "gateway.omdb")
public class OmdbProperties {

    private String url;
    private String apiKey;
}
