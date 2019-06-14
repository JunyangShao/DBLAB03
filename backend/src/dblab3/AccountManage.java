package dblab3;

import java.security.PublicKey;
import java.sql.*;

public class AccountManage {
    private Connector connector;
    private Connection conn;
    public AccountManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    public ResultSet querySavingAccount(String accountNum,String branchName,String employeeid,String balance,String createDate,String recentVisitDate,String interestrate,String currencyType){
        StringBuffer queryString = new StringBuffer("select * from savingaccount where ");
        boolean flag = false;
        if(accountNum.length()!=0){
            queryString.append("accountnum = \'"+accountNum + "\' and ");
            flag = true;
        }
        if(branchName.length()!=0){
            queryString.append("branchname = \'" + branchName + "\' and ");
            flag = true;
        }
        if(employeeid.length()!=0){
            queryString.append("employeeid = \'"+employeeid + "\' and ");
            flag = true;
        }
        if(balance.length()!=0){
            queryString.append("balance = " + balance +" and ");
            flag = true;
        }
        if(createDate.length()!=0){
            queryString.append("createdate = \'" +createDate + "\' and ");
            flag = true;
        }
        if(recentVisitDate.length()!=0){
            queryString.append("recentvisitdate = \'" + recentVisitDate + "\' and ");
            flag = true;
        }
        if(interestrate.length()!=0){
            queryString.append("interestrate = "+interestrate + " and ");
            flag = true;
        }
        if(currencyType.length()!=0){
            queryString.append("currencytype = \'"+currencyType + "\' and ");
            flag = true;
        }
        queryString.delete(queryString.length()-5,queryString.length());
        if(!flag){
            queryString.delete(0,queryString.length());
            queryString.append("select * from savingaccount");
        }
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(queryString.toString());

        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return resultSet;


    }

    public ResultSet queryCheckAccount(String accountNum,String branchName,String employeeid,String balance,String createDate,String recentVisitDate,String accountLimit){
        StringBuffer queryString = new StringBuffer("select * from checkaccount where ");
        boolean flag = false;
        if(accountNum.length()!=0){
            queryString.append("accountnum = \'"+accountNum + "\' ,");
            flag = true;
        }
        if(branchName.length()!=0){
            queryString.append("branchname = \'" + branchName + "\' ,");
            flag = true;
        }
        if(employeeid.length()!=0){
            queryString.append("employeeid = \'"+employeeid + "\' ,");
            flag = true;
        }
        if(balance.length()!=0){
            queryString.append("balance = " + balance +",");
            flag = true;
        }
        if(createDate.length()!=0){
            queryString.append("createdate = \'" +createDate + "\' ,");
            flag = true;
        }
        if(recentVisitDate.length()!=0){
            queryString.append("recentvisitdate = \'" + recentVisitDate + "\' ,");
            flag = true;
        }
        if(accountLimit.length()!=0){
            queryString.append("accountlimit = " +accountLimit + ",");
            flag = true;
        }
        queryString.deleteCharAt(queryString.length()-1);
        if(!flag){
            queryString.delete(0,queryString.length());
            queryString.append("select * from checkaccount");
        }
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(queryString.toString());
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return resultSet;

    }

    public boolean addSavingAccount(String clientId,String accountNum,String branchname,String employeeId,String interestrate,String currencyType){
        CallableStatement callableStatement = null;
        String sql = "{call create_saving_account(?,?,?,?,?,?,?,?,?)}";
        try{
            callableStatement = conn.prepareCall(sql);
            callableStatement.setString(1,clientId);
            callableStatement.setString(2,accountNum);
            callableStatement.setString(3,branchname);
            callableStatement.setString(4,employeeId);
            callableStatement.setInt(5,0);
            callableStatement.setDate(6,new java.sql.Date(new java.util.Date().getTime()));
            callableStatement.setDate(7,new java.sql.Date(new java.util.Date().getTime()));
            callableStatement.setFloat(8,Float.parseFloat(interestrate));
            callableStatement.setString(9,currencyType);
            callableStatement.executeUpdate();
            return true;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public boolean addCheckAccount(String clientId,String accountNum,String branchName,String employeeId,String accountLimit){
        CallableStatement callableStatement = null;
        String sql = "{call create_check_account(?,?,?,?,?,?,?,?)}";
        try{
            callableStatement = conn.prepareCall(sql);
            callableStatement.setString(1,clientId);
            callableStatement.setString(2,accountNum);
            callableStatement.setString(3,branchName);
            callableStatement.setString(4,employeeId);
            callableStatement.setInt(5,0);
            callableStatement.setDate(6,new java.sql.Date(new java.util.Date().getTime()));
            callableStatement.setDate(7,new java.sql.Date(new java.util.Date().getTime()));
            callableStatement.setInt(8,Integer.parseInt(accountLimit));
            callableStatement.executeUpdate();
            return true;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public boolean removeAccount(String accountNum){
        CallableStatement callableStatement = null;
        String sql = "{call delete_account(?)}";
        try{
            callableStatement = conn.prepareCall(sql);
            callableStatement.setString(1,accountNum);
            callableStatement.execute();
            return true;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public boolean updateSavingAccount(String accountNum,String balance,String employeeId,String interestrate,String currencyType){
        String updateSavingAccountString = "update savingaccount set ";
        String updateAccountString = "update account set ";

        return false;
    }
    public boolean updateCheckAccount(){
        return false;
    }
}
