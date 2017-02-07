package main.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tangtao on 2016/4/15.
 * 此类是为注册第一部接口（register）所做的扫尾工作的函数
 */
public class del37to39 {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");
    public static  void del37to39() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "DELETE FROM employees WHERE UserName LIKE '1565889065%';";
        int num=stmt.executeUpdate(selectSql);

        selectSql = "DELETE FROM employees WHERE UserName='15658'";
        num=stmt.executeUpdate(selectSql);
        selectSql = "DELETE FROM employees WHERE UserName='156588906333333'";
        num=stmt.executeUpdate(selectSql);
        selectSql = "DELETE FROM employees WHERE UserName='156qqwwzzxx'";
        num= stmt.executeUpdate(selectSql);
        selectSql = "DELETE FROM employees WHERE UserName='25658890633'";
        num=  stmt.executeUpdate(selectSql);
        /*
        String selectSql = "DELETE FROM employees WHERE UserName='15658890638'";
        int num=stmt.executeUpdate(selectSql);
        selectSql = "DELETE FROM employees WHERE UserName='15658890637'";
        num=stmt.executeUpdate(selectSql);
        selectSql = "DELETE FROM employees WHERE UserName='15658890639'";
        num=stmt.executeUpdate(selectSql);
        selectSql = "DELETE FROM employees WHERE UserName='15658890636'";
        num=stmt.executeUpdate(selectSql);
        */
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        del37to39();
    }
}
