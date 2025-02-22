package baymax;

import baymax.storage.Storage;
import baymax.tasklist.TaskList;
import baymax.parser.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BaymaxTest {

    // Check if initialization works properly
    @Test
    public void testBaymaxInitialization() {
        Baymax baymax = new Baymax("./data/test_tasks.txt");
        assertNotNull(baymax, "Baymax is initialized properly.");
    }
}
