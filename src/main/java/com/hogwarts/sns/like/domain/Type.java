package com.hogwarts.sns.like.domain;

public enum Type {

	POST("POST"), COMMENT("COMMENT");

	private String type;

	Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
