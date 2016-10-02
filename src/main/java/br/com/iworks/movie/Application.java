package br.com.iworks.movie;

import br.com.iworks.movie.config.ModuleConfiguration;
import br.com.iworks.movie.config.RestTemplateConfiguration;
import br.com.iworks.movie.config.Swagger2Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("### - Lendo propriedades do Application: " + System.getProperties().toString());

        new SpringApplicationBuilder(Application.class,
                ModuleConfiguration.class,
                RestTemplateConfiguration.class,
                Swagger2Configuration.class).run(args);
    }
}