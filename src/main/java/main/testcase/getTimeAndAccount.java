package main.testcase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.TestJsonResult_Array;
import main.unit.readexcel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;

import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;
import static main.unit.getMySql.getValue_accout;
import static main.unit.getMySql.getValue_timeRange;

/**
 * Created by tangtao on 2016/6/12.
 */
public class getTimeAndAccount {
    String testUrl = "/api/activity/property";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "getTimeAndAccount");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }
    @Test(dataProvider = "ex")
    public void querryShopMarketing (String activityType,String excode,String exres) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url=url_to_start()+"?activityType="+activityType+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);

        JSONObject data=final_res.getData();
        System.out.println(data);
        JSONArray count = data.getJSONArray("count");
        for (int i = 0; i < count.size(); i++) {
            JSONObject obj = count.getJSONObject(i);
           // System.out.println(new DecimalFormat("0").format(obj.get("value")));//此处0为小数点后不保留
            Assert.assertTrue(getValue_accout()[0].equals(new DecimalFormat("0").format(obj.get("value")))||getValue_accout()[1].equals(new DecimalFormat("0").format(obj.get("value")))
            ||getValue_accout()[2].equals(new DecimalFormat("0").format(obj.get("value"))));
            Assert.assertEquals(new DecimalFormat("0").format(obj.get("activityType")),"1");
        }
        JSONArray timeSpan = data.getJSONArray("timeSpan");
        for (int i = 0; i < timeSpan.size(); i++) {
            JSONObject obj = timeSpan.getJSONObject(i);
            //System.out.println(new DecimalFormat("0").format(obj.get("value")));//此处0为小数点后不保留
            Assert.assertTrue(getValue_timeRange()[0].equals(new DecimalFormat("0").format(obj.get("value")))||getValue_timeRange()[1].equals(new DecimalFormat("0").format(obj.get("value")))
                    ||getValue_timeRange()[2].equals(new DecimalFormat("0").format(obj.get("value"))));
            Assert.assertEquals(new DecimalFormat("0").format(obj.get("type")),"2");
            Assert.assertEquals(new DecimalFormat("0").format(obj.get("activityType")),"1");
        }
    }
}
