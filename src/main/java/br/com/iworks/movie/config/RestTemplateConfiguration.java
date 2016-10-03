package br.com.iworks.movie.config;

import static org.apache.http.client.config.RequestConfig.Builder;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import br.com.iworks.movie.config.properties.ProxyProperties;

@Configuration
public class RestTemplateConfiguration {

    private final static int TIMEOUT = 5000;

    @Autowired
    private ProxyProperties proxyProperties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, ClientHttpRequestFactory clientHttpRequestFactory) {
        return builder.requestFactory(clientHttpRequestFactory).build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        Builder requestConfigBuilder = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setSocketTimeout(TIMEOUT);

        HttpClientBuilder httpClientBuilder = this.getHttpClientBuilder(requestConfigBuilder);

        return new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build());
    }

    private HttpClientBuilder getHttpClientBuilder(final Builder requestConfigBuilder) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        if (proxyProperties.isEnabled()) {
            CredentialsProvider credentialProvider = getCredentialsProvider(requestConfigBuilder);
            httpClientBuilder.setDefaultCredentialsProvider(credentialProvider).setDefaultRequestConfig(requestConfigBuilder.build());
        } else {
            httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
        }

        return httpClientBuilder;
    }

    /**
     * Pode passar o proxy com linha de comando:
     * <p/>
     * -Dproxy.enabled=true -Dproxy.host=seuproxyhost -Dproxy.port=8080 -Dproxy.username=nomedeusuario -Dproxy.password=senha
     *
     * @param requestConfigBuilder
     * @return
     */
    private CredentialsProvider getCredentialsProvider(final Builder requestConfigBuilder) {
        requestConfigBuilder.setProxy(new HttpHost(proxyProperties.getHost(), proxyProperties.getPort()));
        CredentialsProvider credentialProvider = new BasicCredentialsProvider();
        credentialProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyProperties.getUsername(), proxyProperties.getPassword()));
        return credentialProvider;
    }
}