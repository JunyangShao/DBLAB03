package dblab3;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class EmployeeManage {
    private Connector connector;
    private Connection conn;
    public EmployeeManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    public ResultSet query(String employId, String branchName, String name, String phoneNumber, String address, String moveInDate){
       if(employId==null){
           return queryAll();
       }
       else{
           StringBuffer queryString = new StringBuffer("select * from employee where ");
           boolean flag = false;
           if(employId.length()!=0){
               queryString.append("employeeid = \'"+employId +"\' and ");
               flag = true;
           }
           if(branchName.length()!=0){
               queryString.append("branchname = \'" + branchName + "\' and ");
               flag = true;
           }
           if(name.length()!=0){
               queryString.append("name = \'" + name + "\' and ");
               flag = true;
           }
           if(phoneNumber.length()!=0){
               queryString.append("phonenum = \'" + phoneNumber + "\' and ");
               flag = true;
           }
           if(address.length()!=0){
               queryString.append("address = \'" + address +"\' and ");
               flag = true;
           }
           if(moveInDate.length()!=0){
               queryString.append("moveindate = \'" + moveInDate + "\' and ");
           }
           if(flag){
               queryString.delete(queryString.length()-5,queryString.length());
               Statement stmt = null;
               ResultSet rs = null;
               try{
                   stmt = conn.createStatement();
                   rs = stmt.executeQuery(queryString.toString());
               }
               catch (SQLException ex){
                   System.out.println(ex.getMessage());
               }
               return rs;
           }
           else{
               return queryAll();
           }
       }
    }
    private ResultSet queryAll(){
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            rs =stmt.executeQuery("select * from employee");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    public boolean addEmployee(String employId, String branchName, String name, String phoneNumber, String address, String moveInDate){
        boolean result = false;
        String addString = "insert into employee values(?,?,?,?,?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(addString);
            stmt.setString(1,employId);
            stmt.setString(2,branchName);
            stmt.setString(3,name);
            stmt.setString(4,phoneNumber);
            stmt.setString(5,address);
            stmt.setDate(6,Date.valueOf(moveInDate));
            return stmt.executeUpdate()>0;

        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public boolean removeEmployee(String employId) throws SQLException {
        PreparedStatement stmt = null;
        String removeString = "delete from employee where employeeid = ?";
        try {
            stmt = conn.prepareStatement(removeString);
            stmt.setString(1,employId);
            return stmt.executeUpdate() > 0;
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
    public boolean update(String employeeId,String newemployId, String branchName, String name, String phoneNumber, String address, String moveInDate) throws SQLException {
        StringBuffer queryString = new StringBuffer("update employee set ");
        boolean flag = false;
        if(newemployId.length()!=0){
            queryString.append("employeeid = \'");
            queryString.append(newemployId);
            queryString.append("\',");
            flag = true;
        }
        if(branchName.length()!=0){
            queryString.append("branchname = \'");
            queryString.append(branchName);
            queryString.append("\',");
            flag = true;
        }
        if(name.length()!=0){
            queryString.append("name = \' ");
            queryString.append(name);
            queryString.append("\',");
            flag = true;
        }
        if(phoneNumber.length()!=0){
            queryString.append("phonenum = \'");
            queryString.append(phoneNumber);
            queryString.append("\',");
            flag = true;
        }
        if(address.length()!=0){
            queryString.append("address = \'");
            queryString.append(address);
            queryString.append("\',");
            flag = true;
        }
        if(moveInDate.length()!=0){
            queryString.append("moveindate = \'");
            queryString.append(moveInDate);
            queryString.append("\',");
            flag = true;
        }
        if(flag){
            queryString.deleteCharAt(queryString.length()-1);
            queryString.append(" where employeeid = \'" + employeeId+"\'");
            //System.out.println(queryString.toString());
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                return stmt.executeUpdate(queryString.toString()) > 0;
                //return true;
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
