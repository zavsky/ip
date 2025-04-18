package bluebird;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Responsible for checking the existance of the save file, loading the save file 
 * into the application upon startup and saving the user-generated tasks when requested.
 */
public class Storage {
    private static final String FILE_PATH = "./task.txt";

    public Storage() {
        ensureFileExists();
    }

    public List<String> loadTasks() {
        try {
            return Files.readAllLines(Paths.get(FILE_PATH));
        } catch (IOException e) {
            System.err.println("Error loading tasks from file: " + e.getMessage());
            return List.of();
        }
    }

    public void saveTasks(String text) {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            fw.write(text);
        } catch (IOException e) {
            System.err.println("Error saving tasks to disk: " + e.getMessage());
        }
    }

    public void ensureFileExists() {
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // private static void appendToFile(String textToAppend) {
    //     try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
    //         fw.write(textToAppend);
    //     } // create a FileWriter in append mode
    //     catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
}
