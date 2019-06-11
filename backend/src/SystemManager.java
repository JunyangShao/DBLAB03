import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class SystemManager {
    Logger logger = Logger.getLogger("SystemManager");
    Connection conn = null;
    Connection logIn(String username,String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception ex){
            logger.log(Level.SEVERE,"Cannot Find Driver",ex);
        }
        try {
            String connectionURL =
                    "jdbc:mysql://localhost:3306/dblab3?"
                            + "user=root&password=jiang19961009&serverTimezone=GMT%2B8";
            conn = DriverManager.getConnection(connectionURL);
            Statement stmt = conn.createStatement();
            String queryString = "select password from SystemManager where user=\'"+username + "\'";
            ResultSet rs = stmt.executeQuery(queryString);
            String hash = sha1(password);
            if(!rs.next()){
                return null; // 没有找到用户名
            }
            else{
               if(hash.equals(rs.getString("password"))) {
                   return conn;
               }
               else{
                   return null;
               }
            }
        }
        catch (SQLException ex){
            logger.log(Level.SEVERE,"Connection to Database Failed",ex);
        }
        return null;
    }
    private  String sha1(String portectData) {
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
