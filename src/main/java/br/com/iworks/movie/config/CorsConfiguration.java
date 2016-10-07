package br.com.iworks.movie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CorsConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods(HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.GET.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.OPTIONS.name(),
                        HttpMethod.HEAD.name(),
                        HttpMethod.TRACE.name())
                .maxAge(3600);
    }
}