package bluebird.commands;

import bluebird.exceptions.IllegalTaskParameterException;

public abstract class Command {
    public String commandFeedback = "";
    /**
     * Executes the user command.
     *
     * @return true if the program should exit, false otherwise.
     * @throws exception if an error occurs during execution.
     */
    public boolean execute() throws IllegalTaskParameterException {
        return false;
    };
}
