package net.harmonytheory.apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Column {
    String name() default "";
    String type() default "";
    boolean primary() default false;
    int size() default 0;
}
