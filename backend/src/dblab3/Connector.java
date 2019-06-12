package dblab3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.*;
class Connector {
    private Connection conn = null;
    private Logger logger = Logger.getLogger("Connector");
    private final String connectString = "jdbc:mysql://localhost:3306/dblab3?"
            + "user=root&password=jiang19961009&serverTimezone=GMT%2B8";
    public Connector(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception ex){
            logger.log(Level.SEVERE,"Cannot Find Driver",ex);
        }
        try{
            conn = DriverManager.getConnection(connectString);
        }
        catch (SQLException ex){
            logger.log(Level.SEVERE,"Connection to Database Failed",ex);
        }
    }
    public Connection getConn(){
        return conn;
    }
}
