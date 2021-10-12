package com.hogwarts.sns.presentation;

import com.hogwarts.sns.application.UserService;
import com.hogwarts.sns.domain.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    private String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzQwNDcxMDksInN1YiI6IjE3MzUwOTkxODIifQ.gf0l4vqwHtDmkrfg5FaGhwG3iqyhrN4lYHkWXf9wdFY";

    @BeforeEach
    public void setUp() {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .addFilter(new CharacterEncodingFilter("UTF-8", true))
                        .alwaysDo(print())
                        .build();
    }

    private void setUser() {
        User user = User.builder()
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
    public void getMyInfo() throws Exception {
        setUser();
        User user = userService.getUser(1L);
        this.mockMvc.perform(get("/api/myInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TOKEN))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.user.id").value(user.getId()))
                .andExpect(jsonPath("$.user.userId").value(user.getUserId()))
                .andExpect(jsonPath("$.user.name").value(user.getName()))
                .andExpect(jsonPath("$.user.profile").value(user.getProfile()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()));
    }

}