package br.com.iworks.movie.config.security;

import java.security.Principal;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = false)
@EnableResourceServer
@RestController
@RequestMapping("/user")
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @RequestMapping({"/user"})
    public Principal user(Principal principal) {
        return principal;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/user").authenticated()
                .anyRequest().permitAll();
    }
}