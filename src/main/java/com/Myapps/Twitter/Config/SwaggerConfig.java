package com.Myapps.Twitter.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myOpenApi(){

        Info info=new Info()
                .title("TwitterClone API")
                .version("1.0")
                .description("Api documentation for the Twitter clone App");

        return new OpenAPI().info(info);
    }

}
