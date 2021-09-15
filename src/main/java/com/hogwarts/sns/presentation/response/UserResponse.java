package com.hogwarts.sns.presentation.response;

import com.hogwarts.sns.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private User user;
	
}
