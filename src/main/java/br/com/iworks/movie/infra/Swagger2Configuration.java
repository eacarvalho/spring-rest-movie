package br.com.iworks.movie.infra;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket configApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("movies")
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, getPostResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, getPutOrDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.PATCH, getPutOrDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, getPutOrDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.GET, getGetResponseMessages())
                .ignoredParameterTypes(WebRequest.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.iworks.movie.ws"))
                .paths(regex("/.*"))
                        // .paths(regex("/v1/.*"))
                        // .paths(PathSelectors.any())
                        // .paths(PathSelectors.ant("v1/*"))
                .build();
    }

    // http://www.restapitutorial.com/lessons/httpmethods.html
    private ArrayList<ResponseMessage> getPostResponseMessages() {
        return newArrayList(new ResponseMessageBuilder()
                        .code(201)
                        .message("Created")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .build(),
                new ResponseMessageBuilder()
                        .code(409)
                        .message("Conflict")
                        .build());
    }

    private ArrayList<ResponseMessage> getPutOrDeleteResponseMessages() {
        return newArrayList(new ResponseMessageBuilder()
                        .code(200)
                        .message("OK")
                        .build(),
                new ResponseMessageBuilder()
                        .code(204)
                        .message("No Content")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .build());
    }

    private ArrayList<ResponseMessage> getGetResponseMessages() {
        return newArrayList(new ResponseMessageBuilder()
                        .code(200)
                        .message("OK")
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("Bad Request")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .build());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API RESTFul for Movie")
                .description("Documentation using Swagger 2 built on Spring API RESTFul")
                .termsOfServiceUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .contact("iWorks.com")
                .license("Apache License Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }
}
