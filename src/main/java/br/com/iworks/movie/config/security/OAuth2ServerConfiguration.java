package br.com.iworks.movie.config.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(value = "spring.security.enabled", havingValue = "true", matchIfMissing = false)
@RestController
@EnableOAuth2Client
@EnableAuthorizationServer
@Order(6)
public class OAuth2ServerConfiguration /* extends AuthorizationServerConfigurerAdapter */ {

    /*
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("fish")
                .secret("memory")
                .authorizedGrantTypes("password", "client_credentials", "authorization_code", "refresh_token", "implicit")
                .authorities("ROLE_CLIENT", "ROLE_ADMIN")
                .scopes("read", "write")
                        // .resourceIds("sparklr")
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7));
    }
    */
}