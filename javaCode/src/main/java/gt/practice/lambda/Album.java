package gt.practice.lambda;

import java.util.List;

public class Album {
    private final String albumName;
    private List<Track> trackList;

    public Album(String albumName, List<Track> trackList) {
        this.albumName = albumName;
        this.trackList = trackList;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public String getAlbumName() {
        return albumName;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumName='" + albumName + '\'' +
                ", trackList=" + trackList +
                '}';
    }
}
