import java.util.Scanner;

public class Main {
    protected static State runState;
    private static Task[] userTasks = new Task[100];
    private static int userTaskCount = 0;

    enum State {
        LISTEN,
        CREATETASK,
        LISTTASK,
        EXIT
    }
    public static void updateState(String userInput) {
        switch (userInput.trim().toLowerCase()) {
            case "bye": runState = State.EXIT; break;
            case "list": runState = State.LISTEN; listTask(); break;
            default: runState = State.LISTEN; createTask(userInput); break;
        }
    }
    public static void createTask(String userInput) {
        userTasks[userTaskCount] = new Task(userInput);
        userTaskCount++;
        System.out.println("Created task: " + userInput);
    }
    public static void listTask() {
        System.out.println("Current stored tasks:");
        for (int i = 0; i < userTaskCount; i++) {
            System.out.println(userTasks[i].getDescription());
        }
    }
    public static void main (String[] args) {
        String userInput;

        bluebird bb = new bluebird();
        bb.greetHello();

        Scanner userInputScanner = new Scanner(System.in);

        while (runState != State.EXIT) {
            userInput = userInputScanner.nextLine();
            updateState(userInput);
            
        }

        bb.greetGoodbye();
        userInputScanner.close();
    }
}
