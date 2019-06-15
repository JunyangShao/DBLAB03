package dblab3;

import java.sql.*;

public class ClientManage {
    private Connector connector;
    private Connection conn;
    public ClientManage(){
        connector = new Connector();
        conn = connector.getConn();
    }
    public boolean addClient(String clientId,String clientName,String phoneNum,String address,String contactName,String contactEmail,String contactClientRelation){
        StringBuffer addString = new StringBuffer("insert into client values(?,?,?,?,?,?,?)");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(addString.toString());
            preparedStatement.setString(1,clientId);
            preparedStatement.setString(2,clientName);
            preparedStatement.setString(3,phoneNum);
            preparedStatement.setString(4,address);
            preparedStatement.setString(5,contactName);
            preparedStatement.setString(6,contactEmail);
            preparedStatement.setString(7,contactClientRelation);
            return preparedStatement.executeUpdate() > 0;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    public boolean removeClient(String clientId){
        String removeString = "delete from client where clientid = \'" + clientId + "\'";
        Statement statement = null;
        try{
            statement = conn.createStatement();
            return statement.executeUpdate(removeString) > 0;
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    private ResultSet queryAll(){
        String queryString = "select * from client";
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryString);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return rs;
    }
    public ResultSet query(String clientId,String clientName,String phoneNum,String address,String contactName,String contactEmail,String contactClientRelation){
        if(clientId==null){
            return queryAll();
        }
        else{
            StringBuffer queryString = new StringBuffer("select * from client where ");
            Statement statement;
            ResultSet resultSet = null;
            boolean flag = false;
            if(clientId.length()!=0){
                queryString.append("clientid = \'"+clientId +"\' and ");
                flag = true;
            }
            if(clientName.length()!=0){
                queryString.append("clientName = \'"+ clientName +"\' and ");
                flag = true;
            }
            if(phoneNum.length()!=0){
                queryString.append("phonenum = \'" + phoneNum + "\' and ");
                flag = true;
            }
            if(address.length()!=0){
                queryString.append("address = \'" + address + "\' and ");
                flag = true;
            }
            if(contactName.length()!=0){
                queryString.append("contactname = \'"+contactName + "\' and ");
                flag = true;
            }
            if(contactEmail.length()!=0){
                queryString.append("contactemail = \'" + contactEmail + "\' and ");
                flag = true;
            }
            if(contactClientRelation.length()!=0){
                queryString.append("contactclientrelation = \'"+contactClientRelation + "\' and ");
                flag = true;
            }
            if(flag){
                queryString.delete(queryString.length()-5,queryString.length());
                try{
                    statement = conn.createStatement();
                    resultSet = statement.executeQuery(queryString.toString());
                }
                catch (SQLException ex){
                    System.out.println(ex.getMessage());
                }
                return resultSet;
            }
            else{
                return queryAll();
            }
        }
    }
    public boolean update(String clientId,String newClientId,String clientName,String phoneNum,String address,String contactName,String contactEmail,String contactClientRelation){
        StringBuffer updateString = new StringBuffer("update client set ");
        boolean flag = false;
        if(newClientId.length()!=0){
            updateString.append("clientid = \'" + newClientId + "\' ,");
            flag = true;
        }
        if(clientName.length()!=0){
            updateString.append("clientname = \'"+clientName + "\' ,");
            flag = true;
        }
        if(phoneNum.length()!=0){
            updateString.append("phonenum = \'"+phoneNum + "\' ,");
            flag = true;
        }
        if(address.length()!=0){
            updateString.append("address = \'"+address + "\' ,");
            flag = true;
        }
        if(contactName.length()!=0){
            updateString.append("contactname = \'" + contactName + "\' ,");
            flag = true;
        }
        if(contactEmail.length()!=0){
            updateString.append("contactemail = \'"+contactEmail +"\' ,");
            flag = true;
        }
        if(contactClientRelation.length()!=0){
            updateString.append("contactclientrelation = \'" + contactClientRelation + "\' ,");
            flag = true;
        }
        if(flag){
            updateString.deleteCharAt(updateString.length()-1);
            updateString.append(" where clientid = \'"+ clientId +"\'");
            Statement statement;
            try {
                statement = conn.createStatement();
                return statement.executeUpdate(updateString.toString()) > 0;
            }
            catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
            return false;
        }
        else{
            return true;
        }
    }
    public ResultSet queryAccount(String clientId){
        String queryString = "select accountnum from clientaccount where clientid = \'" + clientId + "\'";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(queryString);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }
}
