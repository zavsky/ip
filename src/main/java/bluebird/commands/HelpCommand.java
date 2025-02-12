package bluebird.commands;

import bluebird.UIHandler;

public class HelpCommand extends Command {
    private final UIHandler ui;

    public HelpCommand(UIHandler ui) {
        this.ui = ui;
    }

    /**
     * Prints the help message shown below.
     * @return false to signal that the program should not end.
     */
    @Override
    public boolean execute() {
        ui.showMessage(
            "Available commands:\n" +
            "\tlist, l      - List all tasks\n" +
            "\tadd, a       - Add a new task                \"a [d/e/t] [description]\"\n" +
            "\tmark, m      - Mark a task as done           \"m [task index]\"\n" +
            "\tunmark, u    - Mark a task as not done       \"u [task index]\"\n" +
            "\tdelete, d    - Delete a task                 \"d [task index]\"\n" +
            "\texit, e      - Exit the program\n" +
            "\thelp, h      - Show this help message\n\n" +
            "\tadd options\n" +
            "\t  deadline   task_description [/by deadline_string]\n" +
            "\t  event      task_description [/from start_string] [/to end_string]\n" +
            "\t  todo       task_description"
        );
        return false;
    }
}
