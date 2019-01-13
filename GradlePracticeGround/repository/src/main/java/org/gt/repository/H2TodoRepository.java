package org.gt.repository;

import org.gt.model.ToDoItem;

import java.util.List;

public class H2TodoRepository implements ToDoRepository {
    @Override
    public List<ToDoItem> findAll() {
        return null;
    }

    @Override
    public ToDoItem findById(long id) {
        return null;
    }

    @Override
    public long insert(ToDoItem todoItem) {
        return 0;
    }

    @Override
    public void update(ToDoItem todoItem) {

    }

    @Override
    public void delete(ToDoItem todoItem) {

    }
}
