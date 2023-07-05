package org.esgi.cookmaster.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    private String filePath;

    public void defineFile(String filePath) {
        this.filePath = filePath;
    }

    public String getFileContent() throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            content.append(line).append("\n");
        }

        scanner.close();
        return content.toString();
    }
}
