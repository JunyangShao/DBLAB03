import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import dblab3.*;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

public class Main {
    public static void main(String[]args){
     ClientManage clientManage = new ClientManage();
     try {
         boolean res = clientManage.update("1","5","","","","","","");
         if(res){
             System.out.println("gg");
         }
     }
     catch (Exception ex){
         ex.printStackTrace();
     }
    }
}
