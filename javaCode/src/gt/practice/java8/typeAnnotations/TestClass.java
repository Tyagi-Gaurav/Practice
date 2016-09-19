package gt.practice.java8.typeAnnotations;

/**
 * Created by gauravt on 23/09/15.
 */

@StaticClassAnnotation
public class TestClass {

    @StaticConstructorFieldAnnotation(name = "test", value = "testVal")
    public TestClass() {
    }
}
