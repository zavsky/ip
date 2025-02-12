package bluebird.commands;

public class ExitCommand extends Command {
    /**
     * @return true to break loop in Main and exit the program.
     */
    @Override
    public boolean execute() {
        return true;
    }
}
