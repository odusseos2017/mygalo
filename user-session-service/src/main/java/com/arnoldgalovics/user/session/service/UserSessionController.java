package com.arnoldgalovics.user.session.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserSessionController {
	
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/user-sessions/validate")
    public UserSessionValidatorResponse validateV2(@RequestParam("sessionId") UUID sessionId) { 
    			// @RequestHeader(value = "X-Sleep", defaultValue = "0") long sleepTime) throws InterruptedException {
        
        boolean isValid = UUID.fromString("ad8614c1-d3e9-4b62-971a-1e7b19345fcb").equals(sessionId);
        
        log.info("isValid : " + isValid);
        return UserSessionValidatorResponse.builder()
                .sessionId(sessionId)
                .valid(isValid)
                .build();
    }
}
