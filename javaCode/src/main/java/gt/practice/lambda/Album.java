package gt.practice.lambda;

import java.util.List;

public class Album {
    private final String albumName;
    private List<Track> trackList;
    private final int memberCount;

    public Album(String albumName, List<Track> trackList, int memberCount) {
        this.albumName = albumName;
        this.trackList = trackList;
        this.memberCount = memberCount;
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

    public int getMemberCount() {
        return memberCount;
    }
}
