package org.gt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToDoItem implements Comparable<ToDoItem> {
    private long id;
    private String name;
    private boolean completed;

    @Override
    public int compareTo(ToDoItem o) {
        return 0;
    }
}
