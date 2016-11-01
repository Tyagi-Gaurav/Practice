package gt.practice.interfaceDefaults;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerformanceImpl {
    public static void main(String[] args) {
        SingleArtist artist = new SingleArtist();
        System.out.println("***** Single - Get Musicians******");
        toConsole(artist.getMusicians());
        System.out.println("***** Single - Get All Musicians******");
        toConsole(artist.getAllMusicians());

        System.out.println("***** Multiple - Get Musicians******");
        MultipleArtist artist1 = new MultipleArtist();
        toConsole(artist1.getMusicians());
        System.out.println("***** Multiple - Get All Musicians******");
        toConsole(artist1.getAllMusicians());
    }

    private static void toConsole(Stream<Artist> musicians) {
        if (musicians != null) {
            musicians.forEach(System.out::println);
        }
    }

    private static class SingleArtist implements Performance {

        @Override
        public String getName() {
            return "SingleArtist";
        }

        @Override
        public Stream<Artist> getMusicians() {
            return Stream.of(new Artist("SingleArtist", "Alone"));
        }
    }

    private static class MultipleArtist implements Performance {

        @Override
        public String getName() {
            return "MultipleGroup";
        }

        @Override
        public Stream<Artist> getMusicians() {
            return Stream.of(new Artist("Artist1", "Germany"),
                             new Artist("Artist2", "Poland"),
                             new Artist("Artist3", "Austria"),
                             new Artist("Beatles", Stream.of(new Artist("Lenon", "origin1"),
                                                             new Artist("Mcartney", "origin2"),
                                                            new Artist("so on", "origin3")).collect(Collectors.toSet()), "Everywhere"));
        }
    }
}
