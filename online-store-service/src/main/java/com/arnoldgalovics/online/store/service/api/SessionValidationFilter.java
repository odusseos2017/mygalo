package com.arnoldgalovics.online.store.service.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.arnoldgalovics.online.store.service.external.session.UserSessionClient;
import com.arnoldgalovics.online.store.service.external.session.UserSessionValidatorResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SessionValidationFilter implements Filter {

	private final UserSessionClient userSessionClient;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
        String sessionIdHeader = httpServletRequest.getHeader("X-Session-Id");
        if(sessionIdHeader == null) {
          httpServletResponse.sendError(HttpStatus.FORBIDDEN.value());
        } else {
          UUID sessionIdUuid = UUID.fromString(sessionIdHeader);
          UserSessionValidatorResponse userSessionValidatorResponse =
              userSessionClient.validateSession(sessionIdUuid);
          if(!userSessionValidatorResponse.isValid()) {
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value());
          } else {
            chain.doFilter(request,response);
          }
        }
	}
}
