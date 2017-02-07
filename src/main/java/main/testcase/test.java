package main.testcase;

import main.unit.PropertiesHandle;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.setMySql.SetGetToken;

/**
 * Created by tangtao on 2016/4/14.
 * * 此接口，为测试登录的接口。
 * 预置条件为在数据表中有username=15658890633的帐号
 */
public class test {
    public static void main(String[] args) throws ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut("http://192.168.1.16:80/api/ucenter/balance/shop/withdrawing");
        put.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        StringEntity jsonEntity = new StringEntity("{'systemVersion':'1','appVersion':'1','clientIP':'192.168.9.55','deviceNumber':'848c6996-8348-4044-b45e-d72952ce8edb','sessionGuid':'b43c796e-1b43-45e8-a2bb-d84dd01ad034','token':'3c2eba2d-687c-49c0-8482-d1232dc8adc9','shopId':'11c8f677-9fe0-4fc8-b895-76e4b86e5dd9','account':'汤涛','bankNo':'123123456632123000','bankName':'中国工商银行','branchName':'杭州滨江支行','amount':'2.2','password':'32005beb6cc4314fe07aa6b0c9208f99'}");
        put.setEntity(jsonEntity );

        CloseableHttpResponse response = client.execute(put);
        HttpEntity entity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        System.out.println(code);
        if (entity != null) {
            System.out.println("--------------------------------------");
            InputStream in = entity.getContent();
            StringBuilder out   =   new StringBuilder();
            byte[]   b   =   new   byte[4096];
            for   (int   n;   (n   =   in.read(b))   !=   -1;)   {
                out.append(new   String(b,   0,   n));
            }
            System.out.println(out);
            System.out.println("--------------------------------------");
        }
        response.close();
        client.close();
    }
}
