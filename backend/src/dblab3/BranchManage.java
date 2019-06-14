package dblab3;
import java.sql.*;
public class BranchManage {
    private Connector connector;
    private Connection conn=null;
    public BranchManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    public boolean addBranch(String name,String city,int assets){
        PreparedStatement stmt;
        try {
            String insertString = "insert into branch values (?,?,?)";
            stmt = conn.prepareStatement(insertString);
            stmt.setString(1,name);
            stmt.setString(2,city);
            stmt.setInt(3,assets);
            return stmt.executeUpdate() > 0;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public boolean updateBranchCity(String name,String city) throws SQLException {
        String queryString = "select branchname from branch where name = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if(rs.next()){ //find name
               String updateString = "update branch set city=? where branchname = ?";
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
    public boolean updateBranchAssets(String name,int assets) throws SQLException {
        String queryString = "select branchname from branch where branchname = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if(rs.next()){ //find name
                String updateString = "update branch set assets=? where branchname = ?";
                try {
                    stmt = conn.prepareStatement(updateString);
                    stmt.setInt(1,assets);
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
        String queryString = "select branchname from branch where branchname = ?";

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,oldName);
            rs = stmt.executeQuery();
            if(rs.next()){
                String updateString = "update branch set branchname = ? where branchname = ?";
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
        String queryString = "select branchname from branch where branchname = ?";
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,name);
            rs = stmt.executeQuery();
            if(rs.next()){
                String deleteString = "delete from branch where branchname = ?";
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
        String queryString = "select city from branch where branchname = +\'" + name + "\'";
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
    public int queryAssets(String name) throws SQLException {
        String queryString = "select assets from branch where branchname = +\'" + name + "\'";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(queryString);
            if(rs.next()){
                return rs.getInt("assets");
            }
            else{
                System.out.println("No Data Found");
                return Integer.MIN_VALUE;
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
        return Integer.MIN_VALUE;
    }
    public ResultSet queryAll() throws SQLException {
        String queryString = "select * from branch";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            rs = stmt.executeQuery();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    public ResultSet queryByCity(String city) throws SQLException {
        String queryString = "select * from branch where city = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(queryString);
            stmt.setString(1,city);
            rs = stmt.executeQuery();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    public ResultSet query(String name,String city,String assets) throws SQLException {
        StringBuffer queryString = new StringBuffer("select * from branch where ");
        boolean flag = false;
        if(name.length()!=0){
            queryString.append("branchname=\'"+name+"\' and ");
            flag = true;
        }
        if(city.length()!=0){
            queryString.append("city=\'"+city + "\' and ");
            flag = true;
        }
        if(assets.length()!=0){
            queryString.append("assets = "+assets +" and ");
            flag = true;
        }
        if(flag) {
            //queryString.deleteCharAt(queryString.length() - 1);
            queryString.delete(queryString.length()-5,queryString.length());
           // System.out.println(queryString.toString());
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(queryString.toString());
            } catch (SQLException ex) {
                System.out.append(ex.getMessage());
            }
            return rs;
        }
        else{
            return queryAll();
        }
    }
    public boolean update(String name,String newName,String city,String assets) throws SQLException {
        StringBuffer queryString = new StringBuffer("update branch set ");
        boolean flag = false;
        if(newName.length()!=0){
            queryString.append("branchname = \'");
            queryString.append(newName);
            queryString.append("\',");
            flag = true;
        }
        if(city.length()!=0){
            queryString.append("city = \'");
            queryString.append(city);
            queryString.append("\',");
            flag = true;
        }
        if(assets.length()!=0){
            queryString.append("assets = ");
            queryString.append(assets);
            queryString.append(",");
            flag = true;
        }
        if(flag){
            queryString.deleteCharAt(queryString.length()-1);
            queryString.append(" where branchname = \'" + name+"\'");
            //System.out.println(queryString.toString());
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                stmt.executeUpdate(queryString.toString());
                return true;
            }
            catch (SQLException ex){
                System.out.println(ex.getMessage());
                return false;
            }
            finally {
                if(stmt!=null){
                    stmt.close();
                }
            }
        }
        else{
            return true;
        }
    }
}

