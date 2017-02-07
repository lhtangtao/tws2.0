package main.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static main.unit.getMySql.getShopID_oldest;

/**
 * Created by tangtao on 2016/4/19.
 */
public class default_pw {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");

    public static void def_pw() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql ="UPDATE employees SET Password='5fe254ba43c6000b6d20465df350d6ea' WHERE UserName='15658890633'";
        int num=stmt.executeUpdate(selectSql);
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        def_pw();
    }
}
