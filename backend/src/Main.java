import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dblab3.*;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

public class Main {
    public static void main(String[]args){
        AccountManage accountManage = new AccountManage();
        boolean result = false;
        try {
           result = accountManage.addSavingAccount("2","johnA","SH","6","0.05","RMB");
           if(result){
               System.out.println("gg");
           }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
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
