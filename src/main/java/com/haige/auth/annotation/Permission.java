package com.haige.auth.annotation;

import com.haige.auth.enums.PermissionType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    PermissionType[] value() default PermissionType.ADMIM;
}
