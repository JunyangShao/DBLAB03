//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.ResultSet;
//import java.util.logging.*;
//public class Main {
//    private static Logger logger = Logger.getLogger("Main");
//    public static void main(String[] args){
//        Connection conn = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//        }
//        catch (Exception ex){
//            logger.log(Level.SEVERE,"Cannot Find Driver",ex);
//        }
//        try {
//            String connectionURL =
//                    "jdbc:mysql://localhost:3306/dblab3?"
//                            + "user=root&password=jiang19961009&serverTimezone=GMT%2B8";
//            conn = DriverManager.getConnection(connectionURL);
//        }
//        catch (SQLException ex){
//            logger.log(Level.SEVERE,"Connection to Database Failed",ex);
//        }
//    }
//
//}
