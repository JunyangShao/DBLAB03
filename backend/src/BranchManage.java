import java.sql.*;

public class BranchManage {
    private Connector connector;
    private Connection conn=null;
    public BranchManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    public boolean addBranch(String name,String city,long assets){
        PreparedStatement stmt;
        try {
            String insertString = "insert into BranchBank values (?,?,?)";
            stmt = conn.prepareStatement(insertString);
            stmt.setString(1,name);
            stmt.setString(2,city);
            stmt.setLong(3,assets);
            stmt.executeUpdate(insertString);
            return true;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public boolean updateBranchCity(String name,String city) throws SQLException {
        String queryString = "select name from BranchBank where name = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if(rs.next()){ //find name
               String updateString = "update BranchBank set city=? where name = ?";
               try {
                   stmt = conn.prepareStatement(updateString);
                   stmt.setString(1,city);
                   stmt.setString(2,name);
                   stmt.executeUpdate();
                   return true;
               }
               catch (SQLException ex){
                   System.out.println(ex.getMessage());
                   return false;
               }
            }
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        finally {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(rs!=null) {
                rs.close();
            }
        }
        return false;
    }
    public boolean updateBranchAssets(String name,long assets) throws SQLException {
        String queryString = "select name from BranchBank where name = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if(rs.next()){ //find name
                String updateString = "update BranchBank set assets=? where name = ?";
                try {
                    stmt = conn.prepareStatement(updateString);
                    stmt.setLong(1,assets);
                    stmt.setString(2,name);
                    stmt.executeUpdate();
                    return true;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        finally {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(rs!=null) {
                rs.close();
            }
        }
        return false;
    }
    public boolean updateBranchName(String newName,String oldName) throws SQLException {
        String queryString = "select name from BranchBank where name = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,oldName);
            rs = stmt.executeQuery();
            if(rs.next()){
                String updateString = "update BranchBank set name = ? where name = ?";
                try {
                    stmt = conn.prepareStatement(updateString);
                    stmt.setString(1,newName);
                    stmt.setString(2,oldName);
                    stmt.executeUpdate();
                    return true;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        finally {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(rs!=null) {
                rs.close();
            }
        }
        return false;
    }
    public boolean removeBranch(String name) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String queryString = "select name from BranchBank where name = ?";
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if(rs.next()){
                String deleteString = "delete from BranchBank where name = ?";
                try {
                    stmt = conn.prepareStatement(deleteString);
                    stmt.setString(1,name);
                    stmt.executeUpdate();
                    return true;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        finally {
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
        }
        return false;
    }

    /**
     *
     * @param name
     * @return city, null if not found
     * @throws SQLException
     */
    public String queryCity(String name) throws SQLException {
        String queryString = "select city from BranchBank where name = +\'" + name + "\'";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(queryString);
            if(rs.next()){
                return rs.getString("city");
            }
            else{
                System.out.println("No Data Found");
                return null;
            }

        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
        }
        return null;
    }

    /**
     *
     * @param name
     * @return return Long.MIN_VALUE if not found data
     * @throws SQLException
     */
    public long queryAssets(String name) throws SQLException {
        String queryString = "select assets from BranchBank where name = +\'" + name + "\'";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(queryString);
            if(rs.next()){
                return rs.getLong("assets");
            }
            else{
                System.out.println("No Data Found");
                return Long.MIN_VALUE;
            }

        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(stmt!=null){
                stmt.close();
            }
            if(rs!=null){
                rs.close();
            }
        }
        return Long.MIN_VALUE;
    }
}

