import java.sql.SQLException;
public class Main {
    public static void main(String[]args) {
        BranchManage branchManage = new BranchManage();
        try {
            long res = branchManage.queryAssets("zeze");
            System.out.println(res);
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

    }
}
