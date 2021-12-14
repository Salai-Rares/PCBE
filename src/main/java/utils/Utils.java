package utils;

public class Utils {

    public final static String onlineClientsResponse = "The following people are online:\n";
    public static void sleep(long milis){
        try {
            Thread.sleep(milis);
        }
        catch (Exception e){
            System.out.println( e.getStackTrace());
        }
    }
}
