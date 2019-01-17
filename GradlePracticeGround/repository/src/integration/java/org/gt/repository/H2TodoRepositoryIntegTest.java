package org.gt.repository;

import org.gt.model.ToDoItem;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class H2TodoRepositoryIntegTest {
    private ToDoRepository toDoRepository;

    @Before
    public void setUp() throws Exception {
        toDoRepository = new InMemoryToDoRepository();
    }

    @Test
    public void testInsertTodoItem() throws Exception {
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setName("Write Integration Tests");
        long newId = toDoRepository.insert(toDoItem);
        toDoItem.setId(newId);

        ToDoItem persistedItem = toDoRepository.findById(newId);
        assertNotNull(persistedItem);
        assertEquals(toDoItem, persistedItem);
    }
}
