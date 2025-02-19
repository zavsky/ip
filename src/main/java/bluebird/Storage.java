package bluebird;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    public Storage() {}

    private static void printFileContents(String filePath) {
        File f = new File(filePath);
        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                System.out.println(s.nextLine());
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void writeToFile(String filePath, String text) {
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToFile(String filePath, String textToAppend) {
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write(textToAppend);
        } // create a FileWriter in append mode
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
