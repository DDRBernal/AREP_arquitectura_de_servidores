package co.edu.escuelaing.microspringboot;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;

public class HttpServer {
    //mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.WebServer.HttpServer"
    //java -cp target/AREP_META_PROTOCOLOS-1.0-SNAPSHOT.jar co.edu.escuelaing.microspringboot.SprintBoot

    /**
     * This method runs the server on port 35000 and showing an answer in the site
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public void start() throws IOException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        boolean flag = true;
        while (flag) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            String path = "";
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("GET") && !inputLine.contains("/favicon.ico")) {
                    getServiceOutput(out,inputLine,clientSocket.getOutputStream());
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            out.close();
            in.close();
            clientSocket.close();

        }
        serverSocket.close();
    }

    /**
     * This method shows the method's message from webService, depends on what he received like parameter
     *
     * @param out
     * @param inputLine
     * @param outputStream
     * @return String HTML body
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void getServiceOutput(PrintWriter out, String inputLine, OutputStream outputStream) throws InvocationTargetException, IllegalAccessException {
        String path = inputLine.split(" ")[1];
        String outputLine = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        if (path.contains("pages")) {
            System.out.println(path);
            if (path.equals("/pages/")){
                ReadFile.getImage(path,outputStream);
            }
            else if (path.contains("png")){
                String newPath = path.split("/")[2];
                System.out.println("wtffff "+newPath);
                ReadFile.getImage(newPath,outputStream);
            } else{
                String newPath = path.split("/")[2];
                outputLine += ReadFile.readFiles(newPath);
                out.println(outputLine);
            }
        }else{
            outputLine += MicroJunit.invoke(path);
            out.println(outputLine);
        }

    }

    /**
     * This method gets the value of the specified environment variable, if is null returns 35000 by default
     * @return int PORT
     */
    public int getPort(){
        int port = 35000;
        if (System.getenv("PORT")!=null){
            port = Integer.parseInt(System.getenv("PORT"));
        }
        return port;
    }
}