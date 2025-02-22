package bluebird;
import java.util.ArrayList;
import java.util.List;

import bluebird.tasks.Task;

public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage();
    private Task[] lastModifiedTasks = null;
    private CommandType undoCommand = null;

    public TaskManager() {
        loadTasks();
    }

    public String addTask(Task task) {
        tasks.add(task);
        lastModifiedTasks = new Task[] {task};
        undoCommand = CommandType.DELETE;

        storage.saveTasks(getWritableTaskString());
        return "Added task: " + task.getDescription();
    }

    private String addTask(Task[] taskArray) {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Added task" + (taskArray.length == 1 ? "" : "s") + ":\n");
        lastModifiedTasks = new Task[taskArray.length];
        undoCommand = CommandType.DELETE;
        int i = 0;
        for (Task task : taskArray) {
            tasks.add(task);
            lastModifiedTasks[i] = task;
            feedback.append("\t\t" + task.getDescription()).append("\n");
            i++;
        }

        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    } 

    public String markTask(int[] indices, boolean isDone) {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Task" + (indices.length == 1 ? " " : "s "));
        lastModifiedTasks = new Task[indices.length];
        undoCommand = isDone ? CommandType.UNMARK : CommandType.MARK;
        int i = 0;
        for (int index : indices) {
            tasks.get(index).markDone(isDone);
            lastModifiedTasks[i] = tasks.get(index);
            feedback.append(index + 1);
            if (i != indices.length - 1) {
                feedback.append(", ");
            } else {
                feedback.append(" ");
            }
            i++;
        }
        feedback.append((isDone ? "has been marked as done" : "has been marked as not done"));

        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    }

    public String markTask(Task[] taskArray, boolean isDone) {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Task" + (taskArray.length == 1 ? " " : "s "));
        lastModifiedTasks = new Task[taskArray.length];
        undoCommand = isDone ? CommandType.UNMARK : CommandType.MARK;
        int i = 0;
        for (Task task : taskArray) {
            task.markDone(isDone);
            lastModifiedTasks[i] = task;
            feedback.append(tasks.indexOf(task) + 1);
            if (i != taskArray.length - 1) {
                feedback.append(", ");
            } else {
                feedback.append(" ");
            }
            i++;
        }
        feedback.append((isDone ? "has been marked as done" : "has been marked as not done"));

        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    }

    public String deleteTask(int[] indices) {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Deleted task" + (indices.length == 1 ? "" : "s") + ":\n");
        lastModifiedTasks = new Task[indices.length];
        undoCommand = CommandType.ADD;
        int i = 0;
        for (int index : indices) {
            lastModifiedTasks[i] = tasks.remove(index);
            feedback.append("\t\t" + lastModifiedTasks[i].getDescription()).append("\n");
            i++;
        }

        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    }

    public String deleteTask(Task[] taskArray) {
        StringBuilder feedback = new StringBuilder();
        feedback.append("Deleted task" + (taskArray.length == 1 ? "" : "s") + ":\n");
        lastModifiedTasks = new Task[taskArray.length];
        undoCommand = CommandType.ADD;
        int i = 0;
        for (Task task : taskArray) {
            lastModifiedTasks[i] = task;
            tasks.remove(task);
            feedback.append("\t\t" + task.getDescription()).append("\n");
            i++;
        }

        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    }

    public String undoCommand() {
        if (lastModifiedTasks == null || undoCommand == null) {
            return "Nothing to undo, sad";
        }

        switch (undoCommand) {
        case ADD:
            return addTask(lastModifiedTasks);
        case MARK:
            return markTask(lastModifiedTasks, true);
        case UNMARK:
            return markTask(lastModifiedTasks, false);
        case DELETE:
            return deleteTask(lastModifiedTasks);
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

    /**
     * Calculates the base-10 exponent value, ignores factor
     * @param value signedness is ignored
     * @return the exponent value of an integer input
     */
    private int getExponent(int value) {
        int exp = 0, val = Math.abs(value);
        for (int i = 9; val > 0; i *= 10) {
            val -= i;
            exp++;
        }
        return exp;
    }

    public String getPrintableTasks() {
        String taskString = "";
        int taskSize = tasks.size() - 1;
        for (int i = 0; i <= taskSize; i++) {
            taskString = taskString + "\t\t" + (i+1) + ". " + tasks.get(i);
            if (i != taskSize) {
                taskString = taskString + System.lineSeparator();
            }
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
