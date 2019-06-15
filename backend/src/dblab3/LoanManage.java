package dblab3;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;

public class LoanManage {
    private Connector connector;
    private Connection conn;
    public LoanManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    private ResultSet queryLoanAll(){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from loan");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }
    private ResultSet queryPayAll(){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select * from pay");
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }
    public ResultSet queryLoan(String loanNum,String employeeId,String branchName,String leftPayNum,String leftAmount,String totalPayNum){
        if(loanNum==null){
            return queryLoanAll();
        }
        else{
            StringBuffer queryString = new StringBuffer("select * from loan where ");
            boolean flag = false;
            if(loanNum.length()!=0){
                queryString.append("loannum = " + loanNum + " and ");
                flag = true;
            }
            if(employeeId.length()!=0){
                queryString.append("employeeid = \'"+employeeId + "\' and ");
                flag = true;
            }
            if(branchName.length()!=0){
                queryString.append("branchname = \'"+branchName + "\' and ");
                flag = true;
            }
            if(leftPayNum.length()!=0){
                queryString.append("leftpaynum = "+leftAmount+" and ");
                flag = true;
            }
            if(leftAmount.length()!=0){
                queryString.append("leftamount = " + leftAmount + " and ");
                flag = true;
            }
            if(totalPayNum.length()!=0){
                queryString.append("totalpaynum = "+totalPayNum +" and ");
                flag = true;
            }
            if(flag){
                queryString.delete(queryString.length()-5,queryString.length());
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
            else{
                return queryLoanAll();
            }
        }
    }
    public ResultSet queryPay(String clientId,String loannum,String payAmount,String payDate){
        if(clientId==null){
            return queryPayAll();
        }
        else{
            StringBuffer queryString = new StringBuffer("select * from pay where ");
            boolean flag = false;
            if(clientId.length()!=0){
                queryString.append("clientid = \'"+clientId + "\' and ");
                flag = true;
            }
            if(loannum.length()!=0){
                queryString.append("loannum = " + loannum +" and ");
                flag = true;
            }
            if(payAmount.length()!=0){
                queryString.append("payamount = " + payAmount + " and ");
                flag = true;
            }
            if(payDate.length()!=0){
                queryString.append("paydate = \'" + payDate + "\' and ");
                flag = true;
            }
            if(flag){
                queryString.delete(queryString.length()-5,queryString.length());
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
            else{
                return queryPayAll();
            }
        }
    }
    public boolean addLoan(String employeeId,String branchName,String leftAmount,String totalPayNum){
        String addString = "insert into loan (employeeid,branchname,leftpaynum,leftamount,totalpaynum) \n values(?,?,?,?,?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(addString);
            preparedStatement.setString(1,employeeId);
            preparedStatement.setString(2,branchName);
            preparedStatement.setInt(3,Integer.parseInt(totalPayNum));
            preparedStatement.setInt(4,Integer.parseInt(leftAmount));
            preparedStatement.setInt(5,Integer.parseInt(totalPayNum));
            return preparedStatement.executeUpdate() > 0 ;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }
    public boolean addPay(String clientId,String loanNum,String payAmount){
        CallableStatement callableStatement;
        String sql = "{call create_pay(?,?,?,?)}";
        try{
            callableStatement = conn.prepareCall(sql);
            callableStatement.setString(1,clientId);
            callableStatement.setInt(2,Integer.parseInt(loanNum));
            callableStatement.setInt(3,Integer.parseInt(payAmount));
            callableStatement.setDate(4,new java.sql.Date(new java.util.Date().getTime()));
            callableStatement.executeUpdate();
            return true;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public boolean removeLoan(String loanNum){
        CallableStatement callableStatement;
        String sql = "{call delete_loan(?)}";
        try {
            callableStatement = conn.prepareCall(sql);
            callableStatement.setInt(1,Integer.parseInt(loanNum));
            callableStatement.execute();
            return true;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
