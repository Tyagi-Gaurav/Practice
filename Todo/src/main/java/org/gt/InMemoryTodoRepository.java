package org.gt;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryTodoRepository implements ToDoRepository {
    private ConcurrentHashMap<UUID, TodoItem> todos = new ConcurrentHashMap<>();

    @Override
    public List<TodoItem> findAll() {
        return todos.values().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public TodoItem findById(UUID uuid) {
        return todos.get(uuid);
    }

    @Override
    public UUID insert(TodoItem todoItem) {
        UUID uuid = UUID.randomUUID();
        todos.putIfAbsent(uuid, todoItem);
        return uuid;
    }

    @Override
    public void update(TodoItem todoItem) {
        todos.replace(todoItem.id(), todoItem);
    }

    @Override
    public void delete(TodoItem todoItem) {
        todos.remove(todoItem.id());
    }
}
