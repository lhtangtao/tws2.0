package main.testcase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult_Array;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/6/7.
 */
public class querryShopMarketing {
    String testUrl = "/api/expansion/history";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }
    @Test
    public void querryShopMarketing () {
        String url=url_to_start()+"?"+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Gson gs = new Gson();
        TestJsonResult_Array final_res = gs.fromJson(result, TestJsonResult_Array.class);
        Assert.assertEquals(final_res.code,200);
        Assert.assertEquals(final_res.success,Boolean.TRUE);
        JsonArray array = final_res.getData();
        JsonObject obj = (JsonObject)array.get(0);
        Assert.assertNotNull(obj.get("activityId").toString());//"activityId"正常情况下必须存在
    }
}
