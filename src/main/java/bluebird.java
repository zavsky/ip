public class Bluebird {
    
    public Bluebird() {

    }

    public void greetHello() {
        clearScreen();
        System.out.println("Hello, I am Bluebird");
        System.out.println("What can I do for you?");
        System.out.println("");
    }

    public void greetGoodbye() {
        clearScreen();
        System.out.println("CAWWW");
        System.out.println("");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
