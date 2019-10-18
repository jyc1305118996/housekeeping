package com.haige.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    PermissionType[] value() default PermissionType.ADMIM;
}
