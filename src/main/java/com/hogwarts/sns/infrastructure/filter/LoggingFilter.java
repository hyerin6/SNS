package com.hogwarts.sns.infrastructure.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.hogwarts.sns.presentation.request.LoggingRequest;
import com.hogwarts.sns.utils.JsonUtils;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws IOException {
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

		try {
			filterChain.doFilter(requestWrapper, responseWrapper);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			LoggingRequest loggingRequest = getLoggingRequest(requestWrapper);
			String log = JsonUtils.toJson(loggingRequest);
			logger.info(log);
		}

		responseWrapper.copyBodyToResponse();
	}

	private LoggingRequest getLoggingRequest(ContentCachingRequestWrapper request) {
		String requestURI = request.getRequestURI();
		String method = request.getMethod();
		return new LoggingRequest(requestURI, method);
	}

}
