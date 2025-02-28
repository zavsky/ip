package bluebird.commands;

/**
 * Represents a command to quit the application.
 *
 * @see Command
 */
public class ExitCommand extends Command {
    /**
     * @return true to signal exit of the program.
     */
    @Override
    public boolean execute() {
        return true;
    }
}
