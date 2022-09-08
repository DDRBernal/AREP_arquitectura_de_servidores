package co.edu.escuelaing.microspringboot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFile {

    public static String readFiles(String path){
        // Convert the string to a byte array.
        // src/main/resources/files/index.html
        String result = "";
        System.out.println("n: "+ path);
        Path file = Paths.get("src/main/resources/files/"+path);
        System.out.println(file.toAbsolutePath());
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line;
                System.out.println(line);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return result;
    }
}
