package com.hogwarts.sns.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	WebApplicationContext context;

	MockMvc mockMvc;

	@Autowired
	UserService userService;

	String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzIxNTM1ODEsInN1YiI6IjE3MzUwOTkxODIifQ.Q2BpE2KJT_HkDEAh3U4lcCONcF68OleQDTTZa_cdfAo";

	User user;

	@BeforeEach
	void setUp() {
		this.mockMvc =
			MockMvcBuilders.webAppContextSetup(context)
				.addFilter(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();

		user = User.builder()
			.id(1L)
			.userId("1735099182")
			.name("박혜린")
			.profile("profile image url")
			.email("hyerin_0611@naver.com")
			.build();

		userService.create(user);
	}

	@DisplayName("로그인한 사용자 정보 조회하기")
	@Test
	void getMyInfo() throws Exception {
		this.mockMvc.perform(get("/api/myInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", TOKEN))
			.andDo(print())
			.andExpect(status().is(HttpStatus.OK.value()))
			.andExpect(jsonPath("$.user.id").value(1L))
			.andExpect(jsonPath("$.user.userId").value("1735099182"))
			.andExpect(jsonPath("$.user.name").value("박혜린"))
			.andExpect(jsonPath("$.user.profile").value("profile image url"))
			.andExpect(jsonPath("$.user.email").value("hyerin_0611@naver.com"));
	}

}