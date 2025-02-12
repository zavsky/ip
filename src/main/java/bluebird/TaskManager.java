package bluebird;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bluebird.tasks.Task;

public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void markTask(int index, boolean isDone) {
        validateIndex(index);
        tasks.get(index).markDone(isDone);
    }

    public void deleteTask(int index) {
        validateIndex(index);
        tasks.remove(index);
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            //throw error
        }
    }
}
