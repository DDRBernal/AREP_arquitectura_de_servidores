package co.edu.escuelaing.microspringboot;

public class WebServices {

    @RequestMapping(value = "/hello")
    public static String helloWorld(){
        return "hello world";
    }

    @RequestMapping(value = "/status")
    public static String serverStatus(){
        return "Running";
    }

    @RequestMapping("/")
    public static String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("index")
    public static String html() {return "";}


}
