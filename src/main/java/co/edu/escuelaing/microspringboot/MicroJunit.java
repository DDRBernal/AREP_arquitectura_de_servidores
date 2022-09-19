package co.edu.escuelaing.microspringboot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class MicroJunit {
    
    private static HashMap<String, Method> requests = new HashMap<>();

    public static void start(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, IOException {
        String className = args[0];
        Class c = Class.forName(className);
        Method[] declareMethods = c.getMethods();
        int passed=0,failed = 0;
        for (Method m : declareMethods){
            if (m.isAnnotationPresent(RequestMapping.class)){
                requests.put(m.getAnnotation(RequestMapping.class).value(),m);
                passed+=1;
            }
        }
    }

    //java -cp target/classes main.java.co.edu.escuelaing.microspringboot.MicroJunit main.java.co.edu.escuelaing.microspringboot.JunitTest
    public static String invoke(String path) throws InvocationTargetException, IllegalAccessException {
        return requests.get(path).invoke(null).toString();
    }
}
