package com.sportedu.config.security;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @since : 07.11.23
 */
public interface CustomUserRepository {

  CustomUser findCustomUserByEmail(String email);
}
