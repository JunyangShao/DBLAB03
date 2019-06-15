package dblab3;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.*;

public class StatisticsManage {
    private Connector connector;
    private Connection conn;
    private static String pattern="\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d";
    private static String[] start_time=new String[5];
    private static String[] end_time=new String[5];
    private static int start_year;
    private static  int start_month;
    private static int end_year;
    private static int end_month;
    private static Pie nmslpie;

    public StatisticsManage(){
        connector = new Connector();
        conn = connector.getConn();
    }

    public String getStat(String saving_or_loan,String msy,String startdate,String enddate,String branchname) {
        if(saving_or_loan==null){
            return null;
        }

        int saving_or_loan_int= saving_or_loan.contains("1")?1:0;
        if(saving_or_loan_int==1){
            nmslpie=new Pie("Saving Stats - Amount");
        }
        else{
            nmslpie=new Pie("Loan Stats - Amount");
        }
        if(msy!=null && startdate!=null && enddate !=null && branchname!=null){
            if(Pattern.matches(pattern,startdate) && Pattern.matches(pattern,enddate)){
                start_time=startdate.split("\\-");
                start_year=Integer.parseInt(start_time[0]);
                start_month=Integer.parseInt(start_time[1]);
                end_time=enddate.split("\\-");
                end_year=Integer.parseInt(end_time[0]);
                end_month=Integer.parseInt(end_time[1]);
                int tmp_month_increment = 1;
                if(msy.contains("monthly")){
                    tmp_month_increment=1;
                }
                else if(msy.contains("seasonaly")){
                    tmp_month_increment=3;
                }
                else if(msy.contains("yearly")){
                    tmp_month_increment=12;
                }
                for(int i=start_year;i<=end_year;i=i+1){
                    int tmp_end_month;
                    int tmp_start_month;
                    if(i==start_year){
                        if(i==end_year){
                            tmp_start_month=start_month;
                            tmp_end_month=end_month;
                        }
                        else{
                            tmp_start_month=start_month;
                            tmp_end_month=12;
                        }
                    }
                    else if(i!=end_year){
                        tmp_start_month=1;
                        tmp_end_month=12;
                    }
                    else{
                        tmp_start_month=1;
                        tmp_end_month=end_month;
                    }
                    int j;
                    if(tmp_month_increment==1){
                        j=tmp_start_month;
                    }
                    else{
                        j=tmp_start_month/tmp_month_increment+1 ;
                    }
                    for(;j<=tmp_end_month;j=j+tmp_month_increment){
                        int is_saving_or_loan=saving_or_loan_int;
                        String begindate;
                        if(j>=10) {
                            begindate = Integer.toString(i) + "-" + Integer.toString(j) + "-01";
                        }
                        else{
                            begindate = Integer.toString(i) + "-0" + Integer.toString(j) + "-01";
                        }
                        String _enddate;
                        if((j+tmp_month_increment)%12>=10){
                            _enddate=Integer.toString(i+j/12)+"-"+Integer.toString((j+tmp_month_increment)%12)+"-01";
                        }
                        else{
                            _enddate=Integer.toString(i+j/12)+"-0"+Integer.toString((j+tmp_month_increment)%12)+"-01";
                        }
                        String ibranchname=branchname;
                        String thismonth = innergetStat(is_saving_or_loan,begindate,_enddate,ibranchname);
                        String[] thismonth_stat=thismonth.split(",");
                        int thismonth_totalamount=Integer.parseInt(thismonth_stat[0]);
                        int thismonth_totalclientnum=Integer.parseInt(thismonth_stat[1]);
                        String _Month=Integer.toString(i)+"."+Integer.toString(j);
                        nmslpie.Fill(_Month,thismonth_totalamount);
                    }
                }
            }
        }
        nmslpie.PrintJPG("Amount.jpg");

        if(saving_or_loan_int==1){
            nmslpie=new Pie("Saving Stats - Client Number");
        }
        else{
            nmslpie=new Pie("Loan Stats - Client Number");
        }
        if(msy!=null && startdate!=null && enddate !=null && branchname!=null){
            if(Pattern.matches(pattern,startdate) && Pattern.matches(pattern,enddate)){
                start_time=startdate.split("\\-");
                start_year=Integer.parseInt(start_time[0]);
                start_month=Integer.parseInt(start_time[1]);
                end_time=enddate.split("\\-");
                end_year=Integer.parseInt(end_time[0]);
                end_month=Integer.parseInt(end_time[1]);
                int tmp_month_increment = 1;
                if(msy.contains("monthly")){
                    tmp_month_increment=1;
                }
                else if(msy.contains("seasonaly")){
                    tmp_month_increment=3;
                }
                else if(msy.contains("yearly")){
                    tmp_month_increment=12;
                }
                for(int i=start_year;i<=end_year;i=i+1){
                    int tmp_end_month;
                    int tmp_start_month;
                    if(i==start_year){
                        if(i==end_year){
                            tmp_start_month=start_month;
                            tmp_end_month=end_month;
                        }
                        else{
                            tmp_start_month=start_month;
                            tmp_end_month=12;
                        }
                    }
                    else if(i!=end_year){
                        tmp_start_month=1;
                        tmp_end_month=12;
                    }
                    else{
                        tmp_start_month=1;
                        tmp_end_month=end_month;
                    }
                    int j;
                    if(tmp_month_increment==1){
                        j=tmp_start_month;
                    }
                    else{
                        j=tmp_start_month/tmp_month_increment+1 ;
                    }
                    for(;j<=tmp_end_month;j=j+tmp_month_increment){
                        int is_saving_or_loan=saving_or_loan_int;
                        String begindate;
                        if(j>=10) {
                            begindate = Integer.toString(i) + "-" + Integer.toString(j) + "-01";
                        }
                        else{
                            begindate = Integer.toString(i) + "-0" + Integer.toString(j) + "-01";
                        }
                        String _enddate;
                        if((j+tmp_month_increment)%12>=10){
                            _enddate=Integer.toString(i+j/12)+"-"+Integer.toString((j+tmp_month_increment)%12)+"-01";
                        }
                        else{
                            _enddate=Integer.toString(i+j/12)+"-0"+Integer.toString((j+tmp_month_increment)%12)+"-01";
                        }
                        String ibranchname=branchname;
                        String thismonth = innergetStat(is_saving_or_loan,begindate,_enddate,ibranchname);
                        String[] thismonth_stat=thismonth.split(",");
                        int thismonth_totalamount=Integer.parseInt(thismonth_stat[0]);
                        int thismonth_totalclientnum=Integer.parseInt(thismonth_stat[1]);
                        String _Month=Integer.toString(i)+"."+Integer.toString(j);
                        nmslpie.Fill(_Month,thismonth_totalclientnum);
                    }
                }
            }
        }
        nmslpie.PrintJPG("ClientNumber.jpg");

        return System.getProperty("user.dir");
    }
    private String innergetStat(int is_saving_or_loan, String begindate, String enddate, String ibranchname){
        CallableStatement callableStatement = null;
        String sql = "{call get_stat(?,?,?,?,?,?)}";
        try{
            java.util.Date beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(begindate);
            java.util.Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
            callableStatement = conn.prepareCall(sql);
            callableStatement.setInt(1,is_saving_or_loan);
            callableStatement.setDate(2,new java.sql.Date(beginDate.getTime()));
            callableStatement.setDate(3,new java.sql.Date(endDate.getTime()));
            callableStatement.setString(4,ibranchname);
            callableStatement.registerOutParameter(5, Types.INTEGER);
            callableStatement.registerOutParameter(6,Types.INTEGER);
            callableStatement.execute();
            int totalAmout = callableStatement.getInt(5);
            int totalClientNum = callableStatement.getInt(6);
            return totalAmout + "," + totalClientNum;
        }
        catch (SQLException | ParseException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
