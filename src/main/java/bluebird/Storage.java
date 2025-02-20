package bluebird;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    private static final String FILE_PATH = "./task.txt";

    private static void printFileContents(String fileSavePath) {
        File f = new File(fileSavePath);
        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            //
        }
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
        } catch (IOException e1) {
            System.err.println("Error creating file: " + e1.getMessage());
        }
    }

    private static void appendToFile(String fileSavePath, String textToAppend) {
        try (FileWriter fw = new FileWriter(fileSavePath, true)) {
            fw.write(textToAppend);
        } // create a FileWriter in append mode
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
