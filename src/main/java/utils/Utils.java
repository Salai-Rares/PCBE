package utils;

public class Utils {

    public final static String onlineClientsResponse = "The following people are online:\n";
    public static void sleep(long milis){
        try {
            Thread.sleep(1000);
        }
        catch (Exception e){
            System.out.println( e.getStackTrace());
        }
    }
}
