package main.unit;

import java.sql.SQLException;

/**
 * Created by tangtao on 2016/4/15.
 * 此函数的作用是构造一个基本的get baserequest 放到最后的url来添加
 */
public class url_to_add {
   // static String baseUrl =PropertiesHandle.readValue("baseUrl");
    static String DeviceType=PropertiesHandle.readValue("deviceType");
    static String SystemVersion=PropertiesHandle.readValue("systemVersion");
    static String AppVersion=PropertiesHandle.readValue("appVersion");
    static String ClientIP= PropertiesHandle.readValue("clientIP");
    static String DeviceNumber=PropertiesHandle.readValue("deviceNumber");
    static String sessionGuid=PropertiesHandle.readValue("sessionGuid");
    static String token=PropertiesHandle.readValue("token");
    static String shopId=PropertiesHandle.readValue("shopId");

    static String systemId=PropertiesHandle.readValue("systemId");
    static String openId=PropertiesHandle.readValue("openId");
    static String platformType=PropertiesHandle.readValue("platformType");
    static String signature=PropertiesHandle.readValue("signature");

    public static String url_base(){//本段函数为后面添加的base信息
//        String url="&systemVersion="+SystemVersion+"&clientIP="+ClientIP+"&deviceNumber="+DeviceNumber+
//                "&sessionGuid="+sessionGuid+"&token="+token+"&appVersion="+AppVersion+"&shopId="+shopId;
        String url= "&sessionGuid="+sessionGuid+"&shopId="+shopId+"&systemVersion="+SystemVersion+"&clientIP="+ClientIP+"&deviceNumber="+DeviceNumber+
               "&token="+token+"&appVersion="+AppVersion;
        // System.out.println(shopId);
        return url;
    }
    public static String url_base_noshop(){//本段函数为后面添加的base信息
        String url="&systemVersion="+SystemVersion+"&clientIP="+ClientIP+"&deviceNumber="+DeviceNumber+
                "&sessionGuid="+sessionGuid+"&token="+token+"&appVersion="+AppVersion;
         //System.out.println(url);
        return url;
    }
    public static String url_base_noshop_noguid(){//本段函数为后面添加的base信息
        String url="&systemVersion="+SystemVersion+"&clientIP="+ClientIP+"&deviceNumber="+DeviceNumber+
                "&token="+token+"&appVersion="+AppVersion;
        //System.out.println(url);
        return url;
    }

    public static String url_PLATFORMBASEREQUEST(){//本段函数为后面添加的base信息
        String url="&systemId="+systemId+"&openId="+openId+"&platformType="+platformType+
                "&signature="+signature;
        //System.out.println(url);
        return url;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        System.out.print(url_base());
    }

}
