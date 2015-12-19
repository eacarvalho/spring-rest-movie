package br.com.iworks.movie.app;

import br.com.iworks.movie.infra.ModuleConfiguration;
import br.com.iworks.movie.infra.MongoConfiguration;
import br.com.iworks.movie.infra.Swagger2Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("### - Lendo propriedades do Application: " + System.getProperties().toString());
        new SpringApplicationBuilder(Application.class, ModuleConfiguration.class, MongoConfiguration.class,
                Swagger2Configuration.class, "classpath*:applicationContext.xml").run(args);
    }
}
