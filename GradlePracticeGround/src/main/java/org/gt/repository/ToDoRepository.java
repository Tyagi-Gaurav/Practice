package org.gt.repository;

import org.gt.model.ToDoItem;

import java.util.List;

public interface ToDoRepository {
    List<ToDoItem> findAll();
    ToDoItem findById(long id);
    long insert(ToDoItem todoItem);
    void update(ToDoItem todoItem);
    void delete(ToDoItem todoItem);
}
