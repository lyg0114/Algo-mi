package com.algo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : iyeong-gyo
 * @package : com.algo.common.dto
 * @since : 30.03.24
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfoRequest {

  private String email;
}

