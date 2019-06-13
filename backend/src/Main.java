import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import dblab3.SystemManager;
import dblab3.BranchManage;
public class Main {
    public static void main(String[]args){
        BranchManage branchManage = new BranchManage();
        ResultSet rs = null;
        boolean flag;
        try {
            flag = branchManage.update("song","song","klm","1000");
            rs = branchManage.queryAll();
            while (rs.next()){
                System.out.println(rs.getString("branchname")+ " " + rs.getString("city"));
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
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
