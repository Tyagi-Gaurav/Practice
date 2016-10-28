package gt.practice.lambdaLib;

import java.util.function.Supplier;

public class SupplierTest {
    public static void main(String[] args) {
        SupplierTest supplierTest = new SupplierTest();
        supplierTest.doSomething(() -> "Hello World");
    }

    public void doSomething(Supplier<String> message) {
        System.out.println(message.get());
    }
}
