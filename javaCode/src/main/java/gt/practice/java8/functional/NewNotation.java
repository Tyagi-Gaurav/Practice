package gt.practice.java8.functional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gauravt on 18/09/15.
 */
public class NewNotation {
    public static void main(String[] args) {
        Factory<List<String>> f = ArrayList<String>::new;
        System.out.println(f.make());

        /*
          ::new is a constructor reference.
          It needs to refer to a constructor that can
          take parameters specified as the arguments of
          the method declared in the Functional Interface.

          If the functional interface declares a method
          that takes a parameter for which there is no
          given constructor, then code does not compile.
         */
        Factory1 f1 = String::new;
        System.out.println(f1.make(new char[] {'a','b'}));
    }
}

