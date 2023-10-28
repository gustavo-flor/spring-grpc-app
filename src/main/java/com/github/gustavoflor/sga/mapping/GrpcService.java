package com.github.gustavoflor.sga.mapping;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface GrpcService {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
