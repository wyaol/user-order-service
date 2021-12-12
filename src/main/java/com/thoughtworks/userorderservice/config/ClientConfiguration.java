package com.thoughtworks.userorderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Logger.Level;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableFeignClients
public class ClientConfiguration {

    @Bean
    @Primary
    public Logger.Level loggerLevel() {
        return Level.FULL;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
