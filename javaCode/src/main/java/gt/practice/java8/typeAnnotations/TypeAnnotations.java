package gt.practice.java8.typeAnnotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.toList;

/**
 * Created by gauravt on 23/09/15.
 */
public class TypeAnnotations {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        Annotation[] annotations = TestClass.class.getAnnotations();
        List<Annotation> annotationsList = asList(annotations);
        annotationsList.forEach(System.out::println);
        Constructor<?>[] constructors = TestClass.class.getConstructors();

        List<Annotation> annotationList = asList(constructors)
                .stream()
                .map(e -> e.getAnnotations())
                .flatMap(l -> Arrays.asList(l).stream())
                .collect(toList());

        annotationList.forEach(System.out::println);
    }
}
