package bluebird;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the categories of commands available to the user 
 * for classifying parsed user-specified inputs.
 */
public enum CommandType {
    LIST, 
    ADD,
    MARK, 
    UNMARK, 
    DELETE, 
    UNDO, 
    HELP, 
    EXIT, 
    UNKNOWN;

    private static final Map<String, CommandType> COMMAND_TYPE = new HashMap<>();

    static {
        COMMAND_TYPE.put("list", LIST);
        COMMAND_TYPE.put("l", LIST);
        COMMAND_TYPE.put("add", ADD);
        COMMAND_TYPE.put("create", ADD);
        COMMAND_TYPE.put("c", ADD);
        COMMAND_TYPE.put("mark", MARK);
        COMMAND_TYPE.put("m", MARK);
        COMMAND_TYPE.put("unmark", UNMARK);
        COMMAND_TYPE.put("u", UNMARK);
        COMMAND_TYPE.put("delete", DELETE);
        COMMAND_TYPE.put("d", DELETE);
        COMMAND_TYPE.put("undo", UNDO);
        COMMAND_TYPE.put("z", UNDO);
        COMMAND_TYPE.put("help", HELP);
        COMMAND_TYPE.put("h", HELP);
        COMMAND_TYPE.put("exit", EXIT);
        COMMAND_TYPE.put("e", EXIT);
    }

    /**
     * Compares a String variable against the pre-assigned list of known commands 
     * and returns the matching CommandType element.
     * 
     * @param commandString a user-specified String to match against
     * @return a CommandType element
     */
    public static CommandType fromString(String commandString) {
        return COMMAND_TYPE.getOrDefault(commandString.toLowerCase(), UNKNOWN);
    }
}
