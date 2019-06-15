package dblab3;
import java.sql.*;

public class AccountManage {
    private Connector connector;
    private Connection conn;
    public AccountManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    public ResultSet querySavingAccount(String accountNum,String branchName,String employeeid,String balance,String createDate,String recentVisitDate,String interestrate,String currencyType){
        if(accountNum==null){
            Statement statement = null;
            try {
                statement = conn.createStatement();
                return statement.executeQuery("select * from savingaccount");
            }
            catch (SQLException ex){
                System.out.println(ex.getMessage());
                return null;
            }
        }
        else{
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
    }
    public ResultSet queryCheckAccount(String accountNum,String branchName,String employeeid,String balance,String createDate,String recentVisitDate,String accountLimit){
        if(accountNum==null){
            Statement statement = null;
            try {
                statement = conn.createStatement();
                return statement.executeQuery("select * from checkaccount");
            }
            catch (SQLException ex){
                System.out.println(ex.getMessage());
                return null;
            }
        }
        else{
            StringBuffer queryString = new StringBuffer("select * from checkaccount where ");
            boolean flag = false;
            if (accountNum.length() != 0) {
                queryString.append("accountnum = \'" + accountNum + "\' and ");
                flag = true;
            }
            if (branchName.length() != 0) {
                queryString.append("branchname = \'" + branchName + "\' and ");
                flag = true;
            }
            if (employeeid.length() != 0) {
                queryString.append("employeeid = \'" + employeeid + "\' and ");
                flag = true;
            }
            if (balance.length() != 0) {
                queryString.append("balance = " + balance + " and ");
                flag = true;
            }
            if (createDate.length() != 0) {
                queryString.append("createdate = \'" + createDate + "\' and ");
                flag = true;
            }
            if (recentVisitDate.length() != 0) {
                queryString.append("recentvisitdate = \'" + recentVisitDate + "\' and ");
                flag = true;
            }
            if (accountLimit.length() != 0) {
                queryString.append("accountlimit = " + accountLimit + " and ");
                flag = true;
            }
            queryString.delete(queryString.length()-5,queryString.length());
            if (!flag) {
                queryString.delete(0, queryString.length());
                queryString.append("select * from checkaccount");
            }
            Statement statement;
            ResultSet resultSet = null;
            try {
                statement = conn.createStatement();
                resultSet = statement.executeQuery(queryString.toString());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return resultSet;
        }
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
        StringBuffer updateSavingAccountString = new StringBuffer("update savingaccount set ");
        StringBuffer updateAccountString = new StringBuffer("update account set ");
        boolean flag1 = false;
        boolean flag2 = false;
        if(balance.length()!=0){
            updateAccountString.append("balance = " + balance +" ,");
            updateSavingAccountString.append("balance = " + balance +" ,");
            flag1 = true;
            flag2 = true;
        }
        if(employeeId.length()!=0){
            updateAccountString.append("employeeid = \'"+employeeId +"\' ,");
            updateSavingAccountString.append("employeeid = \'"+employeeId +"\' ,");
            flag1 = true;
            flag2 = true;
        }
        if(interestrate.length()!=0){
            updateSavingAccountString.append("interestrate = "+Float.parseFloat(interestrate)+" ,");
            flag1 = true;
        }
        if(currencyType.length()!=0){
            updateSavingAccountString.append("currencytype = \'"+currencyType+"\' ,");
            flag1 = true;
        }
        if(flag1) {
            updateSavingAccountString.deleteCharAt(updateSavingAccountString.length() - 1);
            updateSavingAccountString.append("where accountnum = \'" + accountNum + "\'");
            Statement statement;
            if(flag2){
                updateAccountString.deleteCharAt(updateAccountString.length()-1);
                updateAccountString.append("where accountnum = \'"+accountNum +"\'");
                try {
                    statement = conn.createStatement();
                    statement.addBatch(updateAccountString.toString());
                    statement.addBatch(updateSavingAccountString.toString());
                    int[] count = statement.executeBatch();
                    return count[0]>0 && count[1] >0;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
            else{
                try {
                    statement = conn.createStatement();
                    return statement.executeUpdate(updateSavingAccountString.toString())>0;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                    return false;
                }
            }

        }
        else{
            return true;
        }
    }
    public boolean updateCheckAccount(String accountNum,String balance,String employeeId,String accountLimit){
        StringBuffer updateCheckAccountString = new StringBuffer("update checkaccount set ");
        StringBuffer updateAccountString = new StringBuffer("update account set ");
        boolean flag1 = false;
        boolean flag2 = false;
        if(balance.length()!=0){
            updateAccountString.append("balance = " + balance +" ,");
            updateCheckAccountString.append("balance = " + balance +" ,");
            flag1 = true;
            flag2 = true;
        }
        if(employeeId.length()!=0){
            updateAccountString.append("employeeid = \'"+employeeId +"\' ,");
            updateCheckAccountString.append("employeeid = \'"+employeeId +"\' ,");
            flag1 = true;
            flag2 = true;
        }
        if(accountLimit.length()!=0){
            updateCheckAccountString.append("accountlimit = "+Integer.parseInt(accountLimit)+" ,");
            flag1 = true;
        }
        if(flag1){
            Statement statement;
            updateCheckAccountString.deleteCharAt(updateCheckAccountString.length()-1);
            updateCheckAccountString.append("where accountnum = \'"+accountNum +"\'");
            if(flag2){
                updateAccountString.deleteCharAt(updateAccountString.length()-1);
                updateAccountString.append("where accountnum = \'"+accountNum +"\'");
                try {
                    statement = conn.createStatement();
                    statement.addBatch(updateAccountString.toString());
                    statement.addBatch(updateCheckAccountString.toString());
                    int[] count = statement.executeBatch();
                    return count[0] > 0 && count[1] > 0;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
            else{
                try {
                    statement = conn.createStatement();
                    return statement.executeUpdate(updateCheckAccountString.toString())>0;
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                    return false;
                }
            }
        }
        else{
            return true;
        }
    }
    public ResultSet queryClients(String accountNum){
        String queryString = "select clientid from clientaccount where accountnum = \'" + accountNum + "\'";
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = conn.createStatement();
            resultSet = statement.executeQuery(queryString);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }
}
