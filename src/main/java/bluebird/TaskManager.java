package bluebird;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bluebird.tasks.Task;

public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage();

    public TaskManager() {
        loadTasks();
    }

    public void addTask(Task task) {
        tasks.add(task);
        storage.saveTasks(getWritableTaskString());
    }

    public void markTask(int index, boolean isDone) {
        validateIndex(index);
        tasks.get(index).markDone(isDone);
        storage.saveTasks(getWritableTaskString());
    }

    public void deleteTask(int index) {
        validateIndex(index);
        tasks.remove(index);
        storage.saveTasks(getWritableTaskString());
    }

    private void loadTasks() {
        List<String> taskStrings = storage.loadTasks();
        for (String taskString : taskStrings) {
            Task task = TaskFactory.createTaskFromFileString(taskString);
            if (task != null) {
                tasks.add(task);
            }
        }
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public String getWritableTaskString() {
        String writableTask = "";
        for (Task t : tasks) {
            writableTask = writableTask + t.toWritable();
        }
        return writableTask;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= tasks.size()) {
            //throw error
        }
    }
}
