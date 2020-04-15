package gt.practice.hack;

public class ParentClass {
    private int x;
    public ParentClass() {
        x = createx();
    }

    protected int createx() {
        System.out.println("Inside parent");
        return 1;
    }
}
