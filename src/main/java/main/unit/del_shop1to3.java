package main.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tangtao on 2016/4/15.
 */
public class del_shop1to3 {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");
    public static void delshop1to3() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
       /*   int i=1;
      while (i<20){
            String selectSql = "DELETE FROM shops WHERE name ='测试店"+i+"'";

            int num=stmt.executeUpdate(selectSql);
            i=i+1;
        }*/
        String selectSql ="DELETE FROM shops WHERE name like '测试店%'";
        int num=stmt.executeUpdate(selectSql);
        selectSql ="DELETE FROM shops WHERE name like '名字超长名字超长名字超长%'";
        num=stmt.executeUpdate(selectSql);
        System.out.println("删除测试店成功");
       /* i=0;
        while (i<5){
            selectSql = "DELETE FROM shops WHERE name ='名字超长名字超长名字超长名字超长名字超长名字超长名字超长名字超长" + i + "'";
            num=stmt.executeUpdate(selectSql);
            i=i+1;
        }*/

    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        delshop1to3();
    }
}

