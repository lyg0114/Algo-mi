package com.algo.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CheckEmailResponse {
	private String token;
	private String message;
}
