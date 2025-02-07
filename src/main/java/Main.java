import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        String userPrompt;
        bluebird bb = new bluebird();
        bb.greetHello();

        Scanner userPromptScanner = new Scanner(System.in);
        userPrompt = userPromptScanner.nextLine().trim().toLowerCase();

        while (!userPrompt.equals("bye")) {
            System.out.println(userPrompt);
            userPrompt = userPromptScanner.nextLine().trim().toLowerCase();
        }

        bb.greetGoodbye();
        userPromptScanner.close();
    }
}
