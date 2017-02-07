package main.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tangtao on 2016/4/22.
 */
public class undoModify {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");
    public static void undoMod() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql ="UPDATE employees SET UserName='15658890633' WHERE Id='b43c796e-1b43-45e8-a2bb-d84dd01ad034';";
        int num=stmt.executeUpdate(selectSql);
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        undoMod();
    }
}
