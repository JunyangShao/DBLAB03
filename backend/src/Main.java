import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import dblab3.SystemManager;
public class Main {
    public static void main(String[]args){
        SystemManager systemManager = new SystemManager();
        System.out.println(sha1("123456"));
        boolean loged = systemManager.logIn("dblab3","123456");
        if(loged){
            System.out.println("yes");
        }
        else{
            System.out.println("no");
        }
    }
    private  static String sha1(String portectData) {
        if (portectData.isEmpty()) {
            return "";
        }
        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA1");
            byte[] bytes = hash.digest(portectData.getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
