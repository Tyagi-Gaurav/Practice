package gt.practice.interfaceDefaults;

import java.util.Collections;
import java.util.Set;

public class Artist {
    private String name;
    private Set<Artist> members;
    private String origin;

    public Artist(String name, Set<Artist> members, String origin) {
        this.name = name;
        this.members = members;
        this.origin = origin;
    }

    public Artist(String name, String origin) {
        this(name, Collections.emptySet(), origin);
    }

    public String getName() {
        return name;
    }

    public Set<Artist> getMembers() {
        return members;
    }

    public String getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", origin='" + origin + '\'' +
                '}';
    }
}
