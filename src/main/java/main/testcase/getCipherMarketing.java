package main.testcase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.TestJsonResult_Array;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static main.unit.expansionactivities.marketing;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/6/6.
 */
public class getCipherMarketing {
    String testUrl = "/api/expansion/activity";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "getCipherMarketing");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }


    @Test(dataProvider = "ex")
    public void getCipherMarketing(String activityId,String  exres,String excode,String state) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if (!state.equals("3")) {
            marketing(activityId, state);
        }
        String url = url_to_start() + "?activityId=" + activityId + url_base();
        String result = httpGet(url);
        System.out.println(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code, code);
        Assert.assertEquals(final_res.success, exresult);
        if (!state.equals("3")) {//如果不等于3 说明他这个活动id是错在的 那么data里的数据必定不为空
            Assert.assertNotNull(final_res.getData());
            if (state.equals("0")) {
                Assert.assertEquals(final_res.getData().get("active"), false);
            } else Assert.assertEquals(final_res.getData().get("active"), true);
        }
        else Assert.assertNull(final_res.getData());
    }
}
