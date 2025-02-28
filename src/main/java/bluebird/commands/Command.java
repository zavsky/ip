package bluebird.commands;

public abstract class Command {
    public String commandFeedback = "";
    /**
     * Executes the user-specified command.
     *
     * @return true if the program should exit, false otherwise.
     */
    public boolean execute() {
        return false;
    };
}
