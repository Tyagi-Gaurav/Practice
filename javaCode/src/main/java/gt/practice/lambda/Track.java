package gt.practice.lambda;

public class Track {
    private int length;
    private String name;

    public Track(int length, String name) {
        this.length = length;
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Track{" +
                "length=" + length +
                ", name='" + name + '\'' +
                '}';
    }
}
