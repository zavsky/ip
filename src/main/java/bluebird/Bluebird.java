package bluebird;

import java.security.SecureRandom;

public class Bluebird {
    private static final SecureRandom random = new SecureRandom();
    
    public Bluebird() {}

    public String greetHello() {
        return "Hello, I am Bluebird\n" + 
            "What can I do for you?\n\n";
    }

    public String scream() {
        return "CAWWW\n";
    }

    public String curse() {
        return "Oh my, either the density levels are off the charts or my reading comprehension is getting worse\n";
    }

    public String enthusiastic() {
        return "Until next time...\n";
    }

    public String confused() {
        return "I don't quite get you... gotta speak clearer\n";
    }

    public String helpless() {
        return "I cannot do that...";
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
