package bluebird;

public enum CommandList {
    LIST("l", "list"),
    ADD("a", "add");

    private final String[] cmd;

    private CommandList(String... cmd) {
        this.cmd = cmd;
    }
}
