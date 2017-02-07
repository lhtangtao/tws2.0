package main.unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by tangtao on 2016/4/14.
 * 此类中的内容为实时获取baserequest
 */
public class getMySql {
    private static String sql_add= PropertiesHandle.readValue("sql_add");
    private static String sql_user= PropertiesHandle.readValue("sql_user");
    private static String sql_pwd= PropertiesHandle.readValue("sql_pwd");

    public static String getShopID_oldest() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT Id from employees WHERE Id='b43c796e-1b43-45e8-a2bb-d84dd01ad034'";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String ID = null;
        while (selectRes.next()) { //循环输出结果集
            ID = selectRes.getString("Id");
        }
        selectSql = "SELECT * FROM employeeshoprelations where EmployeeId='"+ID+"'";
        selectRes = stmt.executeQuery(selectSql);
        String shopId = null;
        while (selectRes.next()) { //循环输出结果集
            shopId = selectRes.getString("ShopId");
        }
        return shopId;
    }
    public static String getSessionGuid() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT Id from employees WHERE Id='b43c796e-1b43-45e8-a2bb-d84dd01ad034'";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String ID = null;
        while (selectRes.next()) { //循环输出结果集
            ID = selectRes.getString("Id");
        }
        return ID;
    }

    public static String getToken() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT * from employees WHERE Id='b43c796e-1b43-45e8-a2bb-d84dd01ad034'";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String token = null;
        while (selectRes.next()) { //循环输出结果集
            token = selectRes.getString("Token");
            if (token.equals(null)){
                System.out.println("请检查select * from employees where UserName='15658890633'");
            }
        }
        return token;
    }

    public static String getShopID() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String employeeid=getSessionGuid();
        String selectSql = "SELECT * FROM `employeeshoprelations`WHERE EmployeeId='"+employeeid+"' ORDER BY CreateTime DESC";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String shopid = null;
        while (selectRes.next()) { //循环输出结果集
            shopid = selectRes.getString("ShopId");
        }
        return shopid;

    }
    public static String getdeviceID() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT * from employeedevicerelations where EmployeeId='b43c796e-1b43-45e8-a2bb-d84dd01ad034'";
        selectSql=selectSql+"ORDER BY loginTime";//获取最新的deviceid
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String DeviceId = null;
        while (selectRes.next()) { //循环输出结果集
            DeviceId= selectRes.getString("DeviceId");
        }
        return DeviceId;
    }
/*
    public static String getSessionGuid_real() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT * FROM employees WHERE Id='49f48a10-55f3-418a-993a-fe9ad7d99d95'";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String ID = null;
        while (selectRes.next()) { //循环输出结果集
            ID = selectRes.getString("Id");
        }
        return ID;
    }
*/
   /* public static String getToken_real() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT * FROM employees WHERE Id='49f48a10-55f3-418a-993a-fe9ad7d99d95'";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String token = null;
        while (selectRes.next()) { //循环输出结果集
            token = selectRes.getString("Token");
            if (token.equals(null)){
                System.out.println("请检查select * from employees where UserName='15658890633'");
            }
        }
        return token;
    }
*/
public static String[] getCode() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
    Vector content = new Vector();
    Connection con = null; //定义一个MYSQL链接对象
    Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
    con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
    Statement stmt; //创建声明
    stmt = con.createStatement();
    String selectSql = "SELECT PASSWORD FROM expansionpassword ";
    ResultSet selectRes = stmt.executeQuery(selectSql);
    String code = null;
    List<String> list = new ArrayList<String>( );
    while (selectRes.next()) { //循环输出结果集
        code = selectRes.getString("PASSWORD");
        list.add(code);
    }
    return list.toArray(new String[]{});
}
    public static String[] getValue_timeRange() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Vector content = new Vector();
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT VALUE FROM activitytimerange ";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String code = null;
        List<String> list = new ArrayList<String>( );
        while (selectRes.next()) { //循环输出结果集
            code = selectRes.getString("value");
            list.add(code);
        }
        return list.toArray(new String[]{});
    }
    public static String[] getValue_accout() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Vector content = new Vector();
        Connection con = null; //定义一个MYSQL链接对象
        Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
        con = DriverManager.getConnection(sql_add, sql_user, sql_pwd); //链接本地MYSQL
        Statement stmt; //创建声明
        stmt = con.createStatement();
        String selectSql = "SELECT VALUE FROM activitycount";
        ResultSet selectRes = stmt.executeQuery(selectSql);
        String code = null;
        List<String> list = new ArrayList<String>( );
        while (selectRes.next()) { //循环输出结果集
            code = selectRes.getString("value");
            list.add(code);
        }
        return list.toArray(new String[]{});
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String []  array = getCode();
        System.out.println(array[0]);
        System.out.println(array.length);
    }
}