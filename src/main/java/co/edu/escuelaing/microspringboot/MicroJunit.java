package co.edu.escuelaing.microspringboot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class MicroJunit {
    
    private static HashMap<String, Method> map = new HashMap<>();
    private static String className;

    public static void setArgs(String args){
        className = args;
    }

    public static void main(String... args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        //String className = "co.edu.escuelaing.microspringboot.HttpServer";
        String className = args[0];
        Class c = Class.forName(className);
        Method[] declareMethods = c.getDeclaredMethods();

        int passed=0,failed = 0;
        for (Method m : declareMethods){
            System.out.println(m.isAnnotationPresent(RequestMapping.class));
            if (m.isAnnotationPresent(RequestMapping.class)){

                    map.put(m.getAnnotation(RequestMapping.class).value(),m);
                    m.invoke(null);
                    System.out.println("Invoking: "+ m.getName() +" in class: " + c.getName());
                    System.out.println(m.getAnnotation(RequestMapping.class).value());
                    passed+=1;

            }
        }
    }//C:\Users\david.otalora-b\IdeaProjects\AREP_META_PROTOCOLOS\src\main\java\co\edu\escuelaing\microspringboot\MicroJunit.java
    //java -cp target/classes main.java.co.edu.escuelaing.microspringboot.MicroJunit main.java.co.edu.escuelaing.microspringboot.JunitTest

    public static boolean searchInMap(String path){
         System.out.println(" searchid in map    :" + map.containsKey(path));
         return map.containsKey(path);
    }

    public static Method getService(String path){
        return map.get(path);
    }
}
