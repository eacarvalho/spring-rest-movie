package br.com.iworks.movie.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @ConditionalOnProperty(value = "spring-rest-movie.security.enabled", havingValue = "false", matchIfMissing = true)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
            .permitAll()
            .anyRequest().permitAll()
            .and().httpBasic()
            .and().csrf().disable();

        http.headers().frameOptions().disable();
    }
}