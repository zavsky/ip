package bluebird;

import java.security.SecureRandom;

public class Bluebird {
    private static final SecureRandom random = new SecureRandom();
    
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

    public void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    /**
     * Taken from stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     * from users Eldelshell and zuddduz.
     * @return
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
