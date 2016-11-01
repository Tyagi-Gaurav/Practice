package gt.practice.interfaceDefaults;

import java.util.stream.Stream;

public interface Performance {
    String getName();

    Stream<Artist> getMusicians();

    default Stream<Artist> getAllMusicians() {
        Stream<Artist> musicians = getMusicians();
        return musicians
              .flatMap(x -> Stream.concat(Stream.of(x), x.getMembers().stream()));

    }
}
