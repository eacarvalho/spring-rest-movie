package br.com.iworks.movie.config;

import static org.apache.http.client.config.RequestConfig.Builder;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    private final static int TIMEOUT = 5000;

    @Value("${proxy.enabled}")
    private boolean isProxyEnabled;

    @Value("${proxy.host}")
    private String proxyHost;

    @Value("${proxy.port}")
    private Integer proxyPort;

    @Value("${proxy.username}")
    private String proxyUsername;

    @Value("${proxy.password}")
    private String proxyPassword;

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

        if (isProxyEnabled) {
            CredentialsProvider credentialProvider = getCredentialsProvider(requestConfigBuilder);
            httpClientBuilder.setDefaultCredentialsProvider(credentialProvider).setDefaultRequestConfig(requestConfigBuilder.build());
        } else {
            httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());
        }

        return httpClientBuilder;
    }

    private CredentialsProvider getCredentialsProvider(final Builder requestConfigBuilder) {
        requestConfigBuilder.setProxy(new HttpHost(proxyHost, proxyPort));
        CredentialsProvider credentialProvider = new BasicCredentialsProvider();
        credentialProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyUsername, proxyPassword));
        return credentialProvider;
    }
}