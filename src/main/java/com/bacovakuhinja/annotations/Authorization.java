package com.bacovakuhinja.annotations;

import com.bacovakuhinja.utility.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
    String role() default Constants.UserRoles.EVERYONE;
}
