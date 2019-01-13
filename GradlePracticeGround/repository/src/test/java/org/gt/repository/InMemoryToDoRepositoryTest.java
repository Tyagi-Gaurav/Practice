package org.gt.repository;

import org.gt.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class InMemoryToDoRepositoryTest {
    private ToDoRepository toDoRepository;

    @Before
    public void setUp() throws Exception {
        toDoRepository = new InMemoryToDoRepository();
    }

    @Test
    public void insertTodoItem() throws Exception {
        ToDoItem newToDoItem = new ToDoItem();
        newToDoItem.setName("Write unit tests");
        Long newId = toDoRepository.insert(newToDoItem);
        assertNotNull(newId);
        ToDoItem persistedToDoItem = toDoRepository.findById(newId);
        assertNotNull(persistedToDoItem);
        assertEquals(newToDoItem, persistedToDoItem);
    }
}