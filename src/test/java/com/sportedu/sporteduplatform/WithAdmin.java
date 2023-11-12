package com.sportedu.sporteduplatform;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithUserDetails;

/**
 * @author : iyeong-gyo
 * @package : com.sportedu.sporteduplatform
 * @since : 07.11.23
 */
@WithUserDetails("admin@example.com")
@Retention(RetentionPolicy.RUNTIME)
public @interface WithAdmin {

}
