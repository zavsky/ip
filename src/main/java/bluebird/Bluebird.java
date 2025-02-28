package bluebird;

import java.security.SecureRandom;

/**
 * Serves as a lookup for Bluebird's speech with formatting to 
 * align with the general UI
 */
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
        return "Either no command or I don't quite get you... gotta speak clearer\n";
    }

    public String helpless() {
        return "I cannot do that...";
    }

    /**
     * Randomly picks an element from the supplied enum class and 
     * returns a value in the enum-class type
     * <p>
     * Taken from stackoverflow.com/questions/1972392/pick-a-random-value-from-an-enum
     * from users Eldelshell and zuddduz.
     * </p>
     * @param clazz an enum class
     * @return a random member of the enum
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
