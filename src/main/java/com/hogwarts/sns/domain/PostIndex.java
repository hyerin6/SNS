package com.hogwarts.sns.domain;

import java.time.ZonedDateTime;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setting(settingPath = "/tokenizer/setting.json")
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(indexName = "post")
public class PostIndex {

	@Id
	private String id;

	@Field(type = FieldType.Text, analyzer = "word_analyzer")
	private String content;

	@Field(type = FieldType.Date)
	private ZonedDateTime createdAt;

	@Field(type = FieldType.Date)
	private ZonedDateTime updatedAt;

}
