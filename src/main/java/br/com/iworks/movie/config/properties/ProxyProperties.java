package br.com.iworks.movie.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "proxy")
public class ProxyProperties {

    private boolean enabled;
    private String host;
    private Integer port;
    private String username;
    private String password;
}