package bluebird.commands;

public class ExitCommand extends Command {
    /**
     * @return true to signal exit of the program.
     */
    @Override
    public boolean execute() {
        return true;
    }
}
