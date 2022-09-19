package co.edu.escuelaing.microspringboot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class SprintBoot {
    /**
     *
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        args = new String[1];
        args[0]="co.edu.escuelaing.microspringboot.WebServices";
        MicroJunit microJunit = new MicroJunit();
        microJunit.start(args);
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }

}
