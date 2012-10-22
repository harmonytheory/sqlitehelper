package net.harmonytheory.apt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SqliteBean {
    String table() default "";
    String helper();
    String nullColumnHack() default "";
}
