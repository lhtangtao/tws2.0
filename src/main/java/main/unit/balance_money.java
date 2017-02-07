package main.unit;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tangtao on 2016/4/18.
 */
public class balance_money {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");
    public static  void setBalance()throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String shopid=PropertiesHandle.readValue("shopId");
        String selectSql = "update shopbalance SET CurrentBalance=100 WHERE ShopId='"+shopid+"'";
        System.out.print(selectSql);
        int selectRes = stmt.executeUpdate(selectSql);
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
       setBalance();
    }
}
