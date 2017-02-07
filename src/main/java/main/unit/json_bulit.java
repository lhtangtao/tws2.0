package main.unit;

import net.sf.json.JSONObject;

import java.sql.SQLException;

import static main.unit.getMySql.*;
import static main.unit.setMySql.SetGetToken;

/**
 * Created by tangtao on 2016/4/15.
 * 此函数的作用是构建一个包含baserequest的json格式文件
 */
public class json_bulit {
    private static String DeviceType=PropertiesHandle.readValue("deviceType");
    private static String SystemVersion=PropertiesHandle.readValue("systemVersion");
    private static String AppVersion=PropertiesHandle.readValue("appVersion");
    private static String ClientIP=PropertiesHandle.readValue("clientIP");
    private static String DeviceNumber=PropertiesHandle.readValue("deviceNumber");
    private static String sessionGuid=PropertiesHandle.readValue("sessionGuid");
    private static String token=PropertiesHandle.readValue("token");
    private static String shopID=PropertiesHandle.readValue("shopId");

    private static String openId=PropertiesHandle.readValue("openId");
    private static String systemId=PropertiesHandle.readValue("systemId");
    private static String platformType=PropertiesHandle.readValue("platformType");
    private static String signature=PropertiesHandle.readValue("signature");

    public static JSONObject json_built() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SetGetToken();
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
      //  jsonParam.put("deviceType", DeviceType);
        jsonParam.put("systemVersion",SystemVersion);
        jsonParam.put("appVersion",AppVersion);
        jsonParam.put("clientIP",ClientIP);
        jsonParam.put("deviceNumber",DeviceNumber);
        jsonParam.put("sessionGuid",sessionGuid);
        jsonParam.put("token",getToken());
        jsonParam.put("shopId",shopID);
        return jsonParam;
    }
    public static JSONObject json_built_noguid() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {//这个函数的json信息都是直接修改数据库而来的
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        jsonParam.put("deviceType", 1);
        jsonParam.put("systemVersion",1);
        jsonParam.put("appVersion",1);
        jsonParam.put("clientIP","192.168.9.55");
        jsonParam.put("deviceNumber",DeviceNumber);
        jsonParam.put("token",getToken());

        return jsonParam;
    }
    public static JSONObject json_built_shopid() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {//这个函数的json信息都是直接修改数据库而来的
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        jsonParam.put("deviceType", 1);
        jsonParam.put("systemVersion",1);
        jsonParam.put("appVersion",1);
        jsonParam.put("clientIP","192.168.9.55");
        jsonParam.put("deviceNumber",DeviceNumber);
        jsonParam.put("token",getToken());
        jsonParam.put("sessionGuid",sessionGuid);
        return jsonParam;
    }
    public static JSONObject json_built_noshopidGuid() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {//这个函数的json信息都是直接修改数据库而来的
        JSONObject jsonParam = new JSONObject();//新建一个json的格式对象
        jsonParam.put("deviceType", 1);
        jsonParam.put("systemVersion",1);
        jsonParam.put("appVersion",1);
        jsonParam.put("clientIP","192.168.9.55");
        jsonParam.put("deviceNumber",DeviceNumber);
        jsonParam.put("token",getToken());
        return jsonParam;
    }
    public static  JSONObject json_built_pla() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        JSONObject jsonParam = json_built();
        jsonParam.put("openId",openId);
        jsonParam.put("systemId",systemId);
        jsonParam.put("platformType",platformType);
        jsonParam.put("signature",signature);
        return jsonParam;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        System.out.println(json_built());
    }
}
