package org.gt;

import java.util.List;
import java.util.UUID;

public interface ToDoRepository {
    List<TodoItem> findAll();

    TodoItem findById(UUID uuid);

    UUID insert(TodoItem todoItem);

    void update(TodoItem todoItem);

    void delete(TodoItem todoItem);
}
