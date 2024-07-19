package com.algo.auth.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.algo.auth.domain.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SignUpRequest {

	private String email;
	private String userName;
	private String password;

	public UserInfo convertToUserInfo(PasswordEncoder encoder) {
		return UserInfo
			.builder()
			.email(this.email)
			.userName(this.userName)
			.passwd(encoder.encode(this.password))
			.role("USER")
			.build();
	}
}
