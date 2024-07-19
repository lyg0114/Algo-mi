package com.algo.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : iyeong-gyo
 * @package : com.algo.profile.dto
 * @since : 26.04.24
 */
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileRequest {
	private String email;
	private String userName;
}
