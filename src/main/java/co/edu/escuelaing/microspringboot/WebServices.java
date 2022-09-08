package co.edu.escuelaing.microspringboot;

public class WebServices {

    @RequestMapping(value = "/hello")
    public static String helloWorld(){
        return "hello world";
    }

}
