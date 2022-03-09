package com.arnoldgalovics.online.store.service.external.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arnoldgalovics.online.store.service.external.inventory.InventoryServiceClient;
import com.arnoldgalovics.online.store.service.external.session.UserSessionClient;

import feign.Feign;
import feign.Logger;
import feign.Request;
//import feign.AsyncFeign;
import feign.slf4j.Slf4jLogger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@Configuration
public class FeignConfiguration {

    @Value("${user.session.url}")
    private String userSessionUrl;

    @Value("${inventory.url}")
    private String inventoryUrl;
	
    @Bean
    public UserSessionClient userSessionClient() {
    	return Feign.builder()
              .logLevel(Logger.Level.FULL)
              .logger(new Slf4jLogger())
              .encoder(new JacksonEncoder())
              .decoder(new JacksonDecoder())
              .requestInterceptor(new SourceRequestInterceptor())
              .target(UserSessionClient.class, userSessionUrl);
    }	
    
    @Bean
    public InventoryServiceClient inventoryServiceClient() {
        return Feign.builder()
                .logLevel(Logger.Level.FULL)
                .logger(new Slf4jLogger())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .errorDecoder(new InventoryServiceErrorDecoder())
                .requestInterceptor(new SourceRequestInterceptor())
                .options(new Request.Options(10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true))
                .target(InventoryServiceClient.class, inventoryUrl);
    }    

}
