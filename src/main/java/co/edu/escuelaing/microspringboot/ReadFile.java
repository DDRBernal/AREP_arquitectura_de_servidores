package co.edu.escuelaing.microspringboot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFile {

    public static String readFiles(String path){
        // Convert the string to a byte array.
        // src/main/resources/files/index.html
        String result = "";
        System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnn: "+ path);
        Path file = Paths.get("src/main/resources/files/"+path);
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return result;
    }

    /**
     * Obtiene una imagen que este ubicada en la carpeta imagenes
     * @param requestURI URI
     * @param outputStream OUT
     */
    public static void getImage(String requestURI, OutputStream outputStream) {
        if (requestURI.equals("/pages/"))requestURI="not_found.png";
        System.out.println("src/main/resources/files/images" + requestURI);
        File file = new File("src/main/resources/files/images/" + requestURI);

        System.out.println(file);
        try {
            BufferedImage pic = ImageIO.read(file);
            ByteArrayOutputStream picShow = new ByteArrayOutputStream();
            DataOutputStream picDraw = new DataOutputStream(outputStream);
            ImageIO.write (pic, "PNG", picShow);
            picDraw.writeBytes("HTTP/1.1 200 OK\r\n" + "Content-Type: image/png \r\n\r\n");
            picDraw.write(picShow.toByteArray());
        } catch (IOException e) {

        }
    }
}
