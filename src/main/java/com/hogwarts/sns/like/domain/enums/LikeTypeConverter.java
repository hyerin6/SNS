package com.hogwarts.sns.like.domain.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LikeTypeConverter implements AttributeConverter<Type, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Type attribute) {
		return attribute.toDbValue();
	}

	@Override
	public Type convertToEntityAttribute(Integer dbData) {
		return Type.from(dbData);
	}
}
