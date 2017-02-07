package main.unit;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import static main.unit.getMySql.*;

/**
 * Created by tangtao on 2016/4/14.
 * 此类的作用是时刻根据数据库中的信息 把baserequest写入配置文件中
 */
public class setMySql {
    private static Properties props = null;
/*    public setMySql() throws IOException {
        InputStream in = new FileInputStream("config.properties");
        // 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中
        // in = propertiesTools.class.getResourceAsStream(fileNamePath);
        props = new Properties();
        props.load(in);
        in.close();
    }
    */
    public static void setTokenValue(String key, String value) {//修改指定的key
        Properties prop = new Properties();
        OutputStream w = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            w = new FileOutputStream("config.properties");
            prop.setProperty(key, value);
            prop.store(w, key);
            w.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(null != w) {
                    w.close();
                }
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void SetGetToken() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException{
        String token_value=getToken();
        String shopId_value=getShopID();
        String deviceId_value=getdeviceID();
        String SessionGuid_value=getSessionGuid();
        //System.out.println("test:::::::::::::::"+token_value);
        setTokenValue("token",token_value);
       // System.out.println("test:::::::::::::::写入成功"+shopId_value);
        setTokenValue("deviceType","1");
        setTokenValue("appVersion","1");
        setTokenValue("baseUrl","http://192.168.1.16:80");
        setTokenValue("clientIP","192.168.9.55");
        setTokenValue("systemVersion","1");
        setTokenValue("shopId",shopId_value);
        setTokenValue("deviceNumber",deviceId_value);
        setTokenValue("sessionGuid",getSessionGuid());
        setTokenValue("shopid_old",getShopID());

    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SetGetToken();
    }
}
