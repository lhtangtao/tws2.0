package main.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tangtao on 2016/5/26.+
 * 在商户激活二维码接口删除二维码和店铺之间的关系
 */
public class del_qrcode {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");
    public static void delQrcode() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql ="DELETE FROM shopqrcoderelations where Qrcode = '9461018447634cf4ae05c5703cea47df';";
        int num=stmt.executeUpdate(selectSql);
        System.out.println("删除Qrcode成供");
    }
}
