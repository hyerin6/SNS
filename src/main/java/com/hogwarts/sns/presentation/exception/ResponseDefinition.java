package com.hogwarts.sns.presentation.exception;

import java.util.function.Supplier;

public interface ResponseDefinition extends Supplier<ResponseException> {
	ResponseException getResponseException();

	default ResponseException get() {
		return getResponseException().clone();
	}
}
