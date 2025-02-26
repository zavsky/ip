package bluebird.commands;

public abstract class Command {
    public String commandFeedback = "";
    /**
     * Executes the user-specified command.
     *
     * @return true if the program should exit, false otherwise.
     * @throws exception if an error occurs during execution.
     */
    public boolean execute() {
        return false;
    };
}
