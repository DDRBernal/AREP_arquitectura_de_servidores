package co.edu.escuelaing.microspringboot;

/**
 * Unit test for simple App.
 */
public class JunitTest{

    @RequestMapping(value = "/")
    public static String index(){
        return "Greetings from Spring boot";
    }

    @RequestMapping(value = "")
    public static void m2(){}

    @Test
    public static void m3(){}

    @Test
    public static void m4(){}

}
