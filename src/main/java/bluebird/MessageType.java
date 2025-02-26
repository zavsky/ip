package bluebird;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
    ERROR,
    SUCCESS,
    INFO,
    SHOWTASK,
    MARK,
    UNMARK,
    DELETE;

    private static final Map<MessageType, String> MESSAGE_TYPE = new HashMap<>();
    private static final Bluebird bb = new Bluebird();

    static {
        MESSAGE_TYPE.put(ERROR, "\t" + bb.curse() + "\tERROR:\t");
        MESSAGE_TYPE.put(SUCCESS, "\t" + bb.scream() + "\n\t");
        MESSAGE_TYPE.put(INFO, "\t");
        MESSAGE_TYPE.put(SHOWTASK, "\tHere's your todo:\n\n");
        MESSAGE_TYPE.put(MARK, "\tSelect task to mark:\n\n");
        MESSAGE_TYPE.put(UNMARK, "\tSelect task to unmark:\n\n");
        MESSAGE_TYPE.put(DELETE, "\tSelect task to delete:\n\n");
    }

    public static String getMessageFormat(MessageType messageType) {
        return MESSAGE_TYPE.getOrDefault(messageType, MESSAGE_TYPE.get(INFO));
    }
}
