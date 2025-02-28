package bluebird;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import bluebird.tasks.Task;

/**
 * Handles all modifications to the task list during runtime. Handles all requests for tasks data 
 * made by other classes.
 */
public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage();
    private Task[] lastModifiedTasks = null;
    private CommandType undoCommand = null;

    /**
     * A map that associates each command type with its corresponding task modification function.
     * <p>
     * The task modification function takes an array of Tasks and returns a String to indicate the 
     * activity back to the user.
     * </p>
     */
    private final Map<CommandType, Function<Task[], String>> undoActions = Map.of(
        CommandType.ADD, this::addTask,
        CommandType.MARK, tasks -> markTask(true, tasks),
        CommandType.UNMARK, tasks -> markTask(false, tasks),
        CommandType.DELETE, this::deleteTask
    );

    public TaskManager() {
        loadTasks();
    }

    public String addTask(Task... tasksToAdd) {
        StringBuilder feedback = new StringBuilder("Added task" + (tasksToAdd.length == 1 ? "" : "s") + ":\n");
        lastModifiedTasks = new Task[tasksToAdd.length];
        undoCommand = CommandType.DELETE;
        for (int i = 0; i < tasksToAdd.length; i++) {
            Task task = tasksToAdd[i];
            tasks.add(task);
            lastModifiedTasks[i] = task;
            feedback.append("\t\t").append(task.getDescription()).append("\n");
        }
        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    }

    public String markTask(int[] indices, boolean isDone) {
        int taskCount = indices.length;
        Task[] taskArray = new Task[taskCount];
        for (int i = 0; i < taskCount; i++) {
            taskArray[i] = tasks.get(indices[i]);
        }
        return markTask(isDone, taskArray);
    }

    private String markTask(boolean isDone, Task... taskArray) {
        StringBuilder feedback = new StringBuilder();
        StringBuilder taskString = new StringBuilder();
        feedback.append("Task" + (taskArray.length == 1 ? " " : "s "));
        lastModifiedTasks = new Task[taskArray.length];
        undoCommand = isDone ? CommandType.UNMARK : CommandType.MARK;
        int i = 0;
        for (Task task : taskArray) {
            task.markDone(isDone);
            lastModifiedTasks[i] = task;

            taskString.append(tasks.indexOf(task)+1);
            if (i != taskArray.length - 1) {
                taskString.append(", ");
            }

            i++;
        }
        feedback.append(taskString + (isDone ? " has been marked as done" : " has been marked as not done"));

        storage.saveTasks(getWritableTaskString());
        return feedback.toString().trim();
    }

    public String deleteTask(int[] indices) {
        int taskCount = indices.length;
        Task[] taskArray = new Task[taskCount];
        for (int i = 0; i < taskCount; i++) {
            taskArray[i] = tasks.get(indices[i]);
        }
        return deleteTask(taskArray);
    }

    public String deleteTask(Task... taskArray) {
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

        return undoActions.getOrDefault(undoCommand, tasks -> "What am I doing here??? Check my code...")
              .apply(lastModifiedTasks);
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
        StringBuilder taskString = new StringBuilder();
        int taskCount = tasks.size();
        int maxDigits = String.valueOf(taskCount).length(); // Number of digits in the largest index
    
        for (int i = 0; i < taskCount; i++) {
            String formattedIndex = String.format("%" + maxDigits + "d", i + 1);
            taskString.append("\t\t").append(formattedIndex).append(". ").append(tasks.get(i));
            if (i != taskCount - 1) {
                taskString.append(System.lineSeparator());
            }
        }
        return taskString.toString();
    }

    public String getPrintableTasks(String key) {
        StringBuilder taskString = new StringBuilder();
        int taskCount = tasks.size();
        int maxDigits = String.valueOf(taskCount).length(); // Number of digits in the largest index
    
        for (int i = 0; i < taskCount; i++) {
            String currTaskDescription = tasks.get(i).toString();
            if (currTaskDescription.toLowerCase().contains(key.toLowerCase())) {
                String formattedIndex = String.format("%" + maxDigits + "d", i + 1);
                taskString.append("\t\t").append(formattedIndex).append(". ").append(tasks.get(i));
                if (i != taskCount - 1) {
                    taskString.append(System.lineSeparator());
                }
            }
        }
        if (taskString.isEmpty()) {
            return "\t\tNo matches found";
        }
        return taskString.toString();
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
        StringBuilder writableTask = new StringBuilder();
        for (Task t : tasks) {
            writableTask.append(t.toWritable());
        }
        return writableTask.toString();
    }

    public boolean isEmpty() {
        return tasks.size() == 0;
    }
}
