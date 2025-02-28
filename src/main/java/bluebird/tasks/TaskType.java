package bluebird.tasks;

import java.util.HashMap;
import java.util.Map;

public enum TaskType {
    DEADLINE,
    EVENT,
    TODO;

    private static final Map<String, TaskType> TASK_TYPES = new HashMap<>();

    static {
        TASK_TYPES.put("deadline", DEADLINE);
        TASK_TYPES.put("d", DEADLINE);
        TASK_TYPES.put("event", EVENT);
        TASK_TYPES.put("e", EVENT);
        TASK_TYPES.put("todo", TODO);
        TASK_TYPES.put("t", TODO);
    }

    /**
     * Compares a String variable with the pre-assigned task type keywords
     * and returns the matching TaskType element. Returns null otherwise.
     * 
     * @param taskType a user-specified String to map a task type to
     * @return a TaskType element
     */
    public static TaskType fromString(String taskType) {
        return TASK_TYPES.get(taskType.toLowerCase());
    }
}
