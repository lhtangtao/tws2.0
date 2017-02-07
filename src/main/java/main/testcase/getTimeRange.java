package main.testcase;

import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import main.unit.PropertiesHandle;
import main.unit.TestJsonResult_Array;
import main.unit.readexcel;
import net.sf.json.JSONArray;

/**
 * Created by tangtao on 2016/6/6.
 */
public class getTimeRange {
    String testUrl = "/api/activity/timerange";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "getTimeRange");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }


    @Test(dataProvider = "ex")
    public void getTimeRange(String activityType,String  exdata,String excode,String exres) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url=url_to_start()+"?activityType="+activityType+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult_Array final_res = gs.fromJson(result, TestJsonResult_Array.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        if(!exdata.equals("0")){
            JsonArray array = final_res.getData();
            JsonObject obj = (JsonObject)array.get(0);
            Assert.assertEquals(obj.get("activityType").toString(),exdata);
        }
    }
}