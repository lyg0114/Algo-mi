package com.algo.config.security.userinfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.core.annotation.AuthenticationPrincipal;



/**
 * @author : iyeong-gyo
 * @package : com.sportedu.config.security
 * @since : 07.11.23
 */
@AuthenticationPrincipal
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {

}
