package com.algo.exception.custom;

/**
 * @author : iyeong-gyo
 * @package : com.algo.exception.custom
 * @since : 06.04.24
 */
public class SignUpFailException extends RuntimeException {

	public SignUpFailException(String message) {
		super(message);
	}
}
