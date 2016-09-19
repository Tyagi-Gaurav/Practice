package gt.practice.java8.typeAnnotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by gauravt on 23/09/15.
 */
@Retention(RUNTIME)
@Target({CONSTRUCTOR, FIELD, TYPE})
public @interface StaticConstructorFieldAnnotation {
    String name();
    String value();
}
