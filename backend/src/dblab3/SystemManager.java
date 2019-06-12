package dblab3;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SystemManager {
    Logger logger = Logger.getLogger("SystemManager");
    private   Connection conn = null;
    /**
     *
     * @param username
     * @param password
     * @return Connection object to the database,null if failed
     */
    public boolean logIn(String username,String password){
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connector connector = new Connector();
            conn = connector.getConn();
            stmt = conn.createStatement();
            String queryString = "select password from SystemManager where username=\'"+username + "\'";
            rs = stmt.executeQuery(queryString);
            String hash = sha1(password);
            if(!rs.next()){
                return false; // 没有找到用户名
            }
            else{
               if(hash.equals(rs.getString("password"))) {
                   return true; //密码错误
               }
               else{
                   return false;
               }
            }
        }
        catch (SQLException ex){
            logger.log(Level.SEVERE,"Connection to Database Failed",ex);
        }
        finally {
            if(stmt!=null){
                try {
                    stmt.close();
                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
            if(rs!=null){
                try {
                    rs.close();
                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }

            }

        }
        return false;
    }

    /**
     *
     * @param portectData string to be hash
     * @return hashed string, 40 bytes length
     */
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
    public Connection getConnect(){
        return conn;
    }
}
