package co.edu.escuelaing.microspringboot;

import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;
import java.lang.reflect.Method;

public class HttpServer {
    //mvn exec:java -Dexec.mainClass="edu.escuelaing.arem.WebServer.HttpServer"
    //java -cp target/AREP_META_PROTOCOLOS-1.0-SNAPSHOT.jar co.edu.escuelaing.microspringboot.MicroJunit
    //java -cp target/patronesReflexion-1.0-SNAPSHOT.jar edu.escuelaing.arem.app.nanoSpark.MicroSpringBoot
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
                    System.out.println("--------------------------------------------");
                    path = inputLine.split(" ")[1];
                    System.out.println("xxxxxxxxxxxxx " + path);
                    outputLine = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n"
                            + getServiceOutput(path);
                    out.println(outputLine);
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

    private static String getServiceOutput(String path) throws InvocationTargetException, IllegalAccessException {
        String m = MicroJunit.invoke(path);
        return "<!DOCTYPE html>\n"  +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>AREP</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<main class=\"main-container\">\n" +
                "\n" +
                "    <div >\n" +
                "        <div >\n" +
                "            <h3>\n" +
                "                 \n" + m +
                "            </h3>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "\n" +
                "    </div>\n" +
                "</main>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

    }

    public static String getHtml(String path){
        System.out.println(ReadFile.readFiles(path));
        return ReadFile.readFiles(path);
    }

    public static String getForm(){

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>AREP</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<script language=\"JavaScript\" type=\"text/javascript\" src=\"./Scripts/connection.js\"></script>\n" +
                "<main class=\"main-container\">\n" +
                "    <h1 class=\"title\">Consult the stock market of the shares traded on the Stock Exchange</h1>\n" +
                "\n" +
                "    <div >\n" +
                "        <div >\n" +
                "            <h3>\n" +
                "                Type the word of a share traded\n" +
                "            </h3>\n" +
                "            <input type=\"text\" id=word_share_traded placeholder=\"For example MSFT\">\n" +
                "\n" +
                "            <button type=\"button\" onclick=\"getData()\">Search</button>\n" +
                "\n" +
                "            <input type=\"radio\" name=\"time\" id=\"Time_Series_Daily\" checked>\n" +
                "            <label for=\"Time_Series_Daily\"> Daily </label>\n" +
                "\n" +
                "            <input type=\"radio\" name=\"time\" id=\"Time_Series_Weekly\">\n" +
                "            <label for=\"Time_Series_Weekly\"> Weekly </label>\n" +
                "\n" +
                "            <input type=\"radio\" name=\"time\" id=\"Time_Series_Monthly\">\n" +
                "            <label for=\"Time_Series_Monthly\"> Monthly </label>\n" +
                "        </div>\n" +
                "        <table cellspacing=\"4\" width=\"90%\">\n" +
                "            <pre id=\"table_elements\">\n" +
                "            </pre>\n" +
                "        </table>\n" +
                "\n" +
                "\n" +
                "    </div>\n" +
                "</main>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

    public int getPort(){
        int port = 35000;
        if (System.getenv("PORT")!=null){
            port = Integer.parseInt(System.getenv("PORT"));
        }
        return port;
    }
}