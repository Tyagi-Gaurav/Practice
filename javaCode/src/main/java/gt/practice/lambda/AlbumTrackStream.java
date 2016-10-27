package gt.practice.lambda;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class AlbumTrackStream {
    public static void main(String[] args) {
        Album album1 = new Album("About a Day", asList(new Track(3, "Bad afternoon"),
                new Track(5, "Good Morning"),
                new Track(7, "Great Night"),
                new Track(2, "Best Evenning")));

        Album album2 = new Album("About SDLC", asList(new Track(10, "Good Dev"),
                new Track(1, "Good QA"),
                new Track(2, "Great Manager"),
                new Track(5, "Shit Process")));

        List<Track> collect = Stream.of(album1, album2)
                .flatMap(album -> album.getTrackList().stream())
                .filter(t -> t.getLength() > 5)
                .collect(Collectors.toList());

        System.out.println(collect);
    }
}
