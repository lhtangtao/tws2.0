package main.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tangtao on 2016/5/27.
 */
public class withdraw {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");
    public static  void witndraw_un() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {//设置认证信息为未认证
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "UPDATE employeeverifyinfo set verifyState=2 where employeeId='b43c796e-1b43-45e8-a2bb-d84dd01ad034';";
        int num = stmt.executeUpdate(selectSql);
    }
    public static  void witndraw_al() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {//设置认证信息为已通过
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "UPDATE employeeverifyinfo set verifyState=1 where employeeId='b43c796e-1b43-45e8-a2bb-d84dd01ad034';";
        int num = stmt.executeUpdate(selectSql);
    }
    public static  void witndraw_fail() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {//设置认证信息为失败
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "UPDATE verifyinfo set VerifyState =2 WHERE ShopId='11c8f677-9fe0-4fc8-b895-76e4b86e5dd9'";
        int num = stmt.executeUpdate(selectSql);
    }
}
