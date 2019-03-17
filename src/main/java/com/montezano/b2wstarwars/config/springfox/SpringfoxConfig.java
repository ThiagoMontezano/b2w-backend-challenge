package com.montezano.b2wstarwars.config.springfox;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2WebFlux
@Profile("!live")
public class SpringfoxConfig {

    private static final String BASE_PACKAGE = "com.montezano.b2wstarwars.http";
    private static final String MSG_NOT_FOUND = "Not found";
    private static final String MSG_INTERNAL_SERVER_ERROR = "Internal Server Error";
    private static final String MSG_BAD_REQUEST = "Bad Request";
    private static final String MODEL_REF_ERROR = "Error";

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .globalResponseMessage(RequestMethod.POST, getErrors())
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> getErrors() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(HttpStatus.NOT_FOUND.value())
                        .message(MSG_NOT_FOUND)
                        .responseModel(new ModelRef(MODEL_REF_ERROR)).build(),
                new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value())
                        .message(MSG_BAD_REQUEST)
                        .responseModel(new ModelRef(MODEL_REF_ERROR)).build(),
                new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(MSG_INTERNAL_SERVER_ERROR).build()
        );
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder().build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Montezano B2W StarWars API")
                .description("B2W StarWars module.")
                .version("1.0")
                .build();
    }
}
