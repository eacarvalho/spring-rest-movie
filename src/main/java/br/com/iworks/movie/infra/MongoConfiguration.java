package br.com.iworks.movie.infra;

import com.mongodb.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories(value = MongoConfiguration.REPOSITORY_PACKAGE, mongoTemplateRef = "nsMongoTemplate")
@Slf4j
public class MongoConfiguration {

    static final String REPOSITORY_PACKAGE = "br.com.iworks.movie";

    @Value("${mongo.database}")
    private String mongoDatabase;

    @Value("${mongo.port}")
    private String mongoPort;

    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.password}")
    private String mongoPassword;

    @Value("${mongo.username}")
    private String mongoUsername;

    @Value("${mongo.config.connections.max.per.host}")
    private Integer mongoConfigConnectionsMaxPerHost;

    @Value("${mongo.config.connections.min.per.host}")
    private Integer mongoConfigConnectionsMinPerHost;

    @Value("${mongo.config.connections.timeout}")
    private Integer mongoConfigConnectionsTimeout;

    @Value("${mongo.config.connections.max.idle}")
    private Integer mongoConfigConnectionsMaxIdle;

    @Value("${mongo.config.connections.max.wait}")
    private Integer mongoConfigConnectionsMaxWait;

    @Bean
    public MongoClient createMongoClient() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(getServerAddress(), getCredentials(), getMongoOptions());
        return mongoClient;
    }

    @Bean(name = "nsMongoTemplate")
    public MongoOperations createMongoOperations(MongoClient mongoClient) throws UnknownHostException {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, mongoDatabase);
        MongoTemplate template = new MongoTemplate(factory);
        template.setWriteResultChecking(WriteResultChecking.LOG);
        return template;
    }

    private List<MongoCredential> getCredentials() {
        String usuario = mongoUsername;
        String password = mongoPassword;
        String database = mongoDatabase;

        log.info("Criando conexão ao banco '{}' com usuário '{}'", database, usuario);
        MongoCredential mongoCredential = MongoCredential.createCredential(usuario, database, password.toCharArray());

        List<MongoCredential> credentials = new ArrayList<>();

        credentials.add(mongoCredential);

        return credentials;
    }

    private List<ServerAddress> getServerAddress() throws UnknownHostException {
        String[] arrayPort = mongoPort.split(",");
        String[] arrayHosts = mongoHost.split(",");

        List<ServerAddress> listaAddress = new ArrayList<>();

        for (int i = 0; i < arrayHosts.length; i++) {
            String host = arrayHosts[i];
            String port = arrayPort[i];
            listaAddress.add(new ServerAddress(host, Integer.parseInt(port)));
        }

        return listaAddress;
    }

    private MongoClientOptions getMongoOptions() {
        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();

        optionsBuilder.writeConcern(WriteConcern.ACKNOWLEDGED);
        optionsBuilder.readPreference(ReadPreference.primaryPreferred());

        optionsBuilder.connectionsPerHost(mongoConfigConnectionsMaxPerHost);
        optionsBuilder.minConnectionsPerHost(mongoConfigConnectionsMinPerHost);
        optionsBuilder.connectTimeout(mongoConfigConnectionsTimeout);
        optionsBuilder.maxConnectionIdleTime(mongoConfigConnectionsMaxIdle);
        optionsBuilder.maxWaitTime(mongoConfigConnectionsMaxWait);
        optionsBuilder.socketKeepAlive(true);
        optionsBuilder.socketTimeout(mongoConfigConnectionsTimeout);
        optionsBuilder.alwaysUseMBeans(false);

        return optionsBuilder.build();
    }
}
