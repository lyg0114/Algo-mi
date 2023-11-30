package com.algo.mock.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.sporteduplatform
 * @since : 07.11.23
 * 사용자를 Mocking 해서 테스트를 할 경우 사용하는 어노테니션
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

  String email() default "user@example.com";

  int id() default 1;

}
