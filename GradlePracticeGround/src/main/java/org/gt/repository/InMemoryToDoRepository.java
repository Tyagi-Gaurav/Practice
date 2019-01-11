package org.gt.repository;

import org.gt.model.ToDoItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryToDoRepository implements ToDoRepository {

    private ConcurrentMap<Long, ToDoItem> toDos = new ConcurrentHashMap<>();
    private AtomicLong currentId = new AtomicLong();

    @Override
    public List<ToDoItem> findAll() {
        List<ToDoItem> todoItems = new ArrayList<>(toDos.values());
        Collections.sort(todoItems);
        return todoItems;
    }

    @Override
    public ToDoItem findById(long id) {
        return toDos.get(id);
    }

    @Override
    public long insert(ToDoItem todoItem) {
        long newId = currentId.incrementAndGet();
        todoItem.setId(newId);
        toDos.putIfAbsent(newId, todoItem);
        return newId;
    }

    @Override
    public void update(ToDoItem todoItem) {
        toDos.replace(todoItem.getId(), todoItem);
    }

    @Override
    public void delete(ToDoItem todoItem) {
        toDos.remove(todoItem.getId());
    }
}
