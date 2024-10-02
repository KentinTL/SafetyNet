package com.openclassrooms.safetynet.exceptions;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestResponseInterceptor implements Filter {

	Logger logger = LoggerFactory.getLogger(RequestResponseInterceptor.class);

	private FilterConfig filterConfig;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		logger.info("Incoming request - {} {} ", httpServletRequest.getMethod(), httpServletRequest.getRequestURL());

		if (httpServletRequest.getMethod().equals("POST") || httpServletRequest.getMethod().equals("PUT")) {
			var requestBody = new RequestWrapper((HttpServletRequest) request);
			logger.info("Incoming body - {} ", requestBody.getBody());
		}

//		chain.doFilter(httpServletRequest, httpServletResponse);
		logger.info("Response sent with status code = {} ", httpServletResponse.getStatus());
	}

	public void init(FilterConfig filterConfiguration) throws ServletException {
		this.filterConfig = filterConfiguration;
	}

	public void destroy() {
		this.filterConfig = null;
	}
}