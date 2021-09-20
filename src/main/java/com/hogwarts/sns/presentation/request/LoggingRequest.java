package com.hogwarts.sns.presentation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoggingRequest {
	@JsonProperty("requestURI")
	String requestURI;

	@JsonProperty("method")
	String method;
}
