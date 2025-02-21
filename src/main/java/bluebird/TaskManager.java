package bluebird;
import java.util.ArrayList;
import java.util.List;

import bluebird.tasks.Task;

public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage();
    private Task lastModifiedTask = null;
    private CommandType undoCommand = null;

    public TaskManager() {
        loadTasks();
    }

    private enum CommandType {
        ADD,
        MARK,
        UNMARK,
        DELETE
    }

    public String addTask(Task task) {
        tasks.add(task);
        lastModifiedTask = task;
        undoCommand = CommandType.DELETE;
        storage.saveTasks(getWritableTaskString());
        return "Added task: " + lastModifiedTask.getDescription() + "\n";
    }

    public String markTask(int index, boolean isDone) {
        return markTask(tasks.get(index), isDone);
    }

    public String markTask(Task task, boolean isDone) {
        task.markDone(isDone);
        lastModifiedTask = task;
        undoCommand = isDone ? CommandType.UNMARK : CommandType.MARK;
        storage.saveTasks(getWritableTaskString());
        return "Task " + (tasks.indexOf(lastModifiedTask) + 1) + 
            (isDone ? " has been marked as done" : " has been marked as not done") + "\n";
    }

    public String deleteTask(int index) {
        lastModifiedTask = tasks.remove(index);
        undoCommand = CommandType.ADD;
        storage.saveTasks(getWritableTaskString());
        return "Deleted task: " + lastModifiedTask.getDescription() + "\n";
    }

    public String deleteTask(Task task) {
        return deleteTask(tasks.indexOf(task));
    }

    public String undoCommand() {
        if (lastModifiedTask == null || undoCommand == null) {
            return "Nothing to undo, sad\n";
        }

        switch (undoCommand) {
        case ADD:
            return addTask(lastModifiedTask);
        case MARK:
            return markTask(lastModifiedTask, true);
        case UNMARK:
            return markTask(lastModifiedTask, false);
        case DELETE:
            return deleteTask(lastModifiedTask);
        default:
            return "What am I doing here??? Check my code...";
        }
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

    public String getPrintableTasks() {
        String taskString = "";
        for (int i = 0; i < tasks.size(); i++) {
            taskString = taskString + "\t\t" + (i+1) + ". " + tasks.get(i) + System.lineSeparator();
        }
        return taskString;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public String getTaskDescription(int index) {
        return tasks.get(index).getDescription();
    }

    public String getTaskType(int index) {
        return tasks.get(index).getTaskType();
    }

    public String getWritableTaskString() {
        String writableTask = "";
        for (Task t : tasks) {
            writableTask = writableTask + t.toWritable();
        }
        return writableTask;
    }

    public boolean isEmpty() {
        return tasks.size() == 0;
    }
}
