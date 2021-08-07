package com.hogwarts.sns.like.domain.enums;

import java.util.stream.Stream;

public enum Type {
	POST(1), COMMENT(2);

	public int type;

	Type(int type) {
		this.type = type;
	}

	public int toDbValue() {
		return type;
	}

	public static Type from(Integer dbData) {
		return Stream.of(Type.values())
			.filter(x -> x.type == dbData)
			.findFirst()
			.orElseThrow(IllegalArgumentException::new);
	}

}