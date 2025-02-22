package baymax.tasklist;

import baymax.exception.BaymaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(); // Use empty constructor to avoid file I/O
    }

    @Test
    void addTodo_validInput_taskAdded() throws BaymaxException {
        String response = taskList.addTodo("todo Read book");
        assertEquals(1, taskList.getTasks().size());
        assertTrue(response.contains("Read book"));
    }

    @Test
    void addTodo_emptyDescription_exceptionThrown() {
        assertThrows(BaymaxException.class, () -> taskList.addTodo("todo "));
    }

    @Test
    void deleteTask_validIndex_taskDeleted() throws BaymaxException {
        taskList.addTodo("todo Read book");
        assertEquals(1, taskList.getTasks().size());

        String response = taskList.deleteTask(0);
        assertEquals(0, taskList.getTasks().size());
        assertTrue(response.contains("Removed task"));
    }

    @Test
    void deleteTask_invalidIndex_exceptionThrown() {
        assertThrows(BaymaxException.class, () -> taskList.deleteTask(0));
    }

    @Test
    void markTask_validIndex_taskMarkedAsDone() throws BaymaxException {
        taskList.addTodo("todo Read book");
        assertFalse(taskList.getTasks().get(0).isDone());

        String response = taskList.markTask(0, true);
        assertTrue(taskList.getTasks().get(0).isDone());
        assertTrue(response.contains("Marked task as done"));
    }

    @Test
    void markTask_invalidIndex_exceptionThrown() {
        assertThrows(BaymaxException.class, () -> taskList.markTask(0, true));
    }
}
