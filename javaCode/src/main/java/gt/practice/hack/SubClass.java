package gt.practice.hack;

public class SubClass extends ParentClass {
    public SubClass() {
        super();
    }

    @Override
    protected int createx() {
        super.createx();
        System.out.println("Inside child");
        return 0;
    }
}
