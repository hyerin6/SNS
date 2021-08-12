package com.hogwarts.sns.common.exception;

public class FileUploadException extends RuntimeException {

	public FileUploadException() {
	}

	public FileUploadException(Throwable t) {
		super(t);
	}

	public FileUploadException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
