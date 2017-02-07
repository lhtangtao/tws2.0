package main.testcase;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import main.unit.*;
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

/**
 * Created by tangtao on 2016/6/7.
 */
public class querryMarketingStatistics {
    String testUrl = "/api/expansion/shop/statistics";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "querryMarketingStatistics");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }

    @Test(dataProvider = "ex")
    public void querryMarketingStatistics(String activityId,String activityType,String exdata,String excode,String exres){
        if(activityId.equals("空")){
            activityId="";
        }
        if(activityType.equals("空")){
            activityType="";
        }
        String url=url_to_start()+"?activityId="+activityId+"&activityType="+activityType+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        if(!activityId.equals("")){//如果此处不做判断，当输入错误的信息时data不存在会报错
            if(!exdata.equals("0")){
                Object num =final_res.getData().getJSONObject("activity").get("active");
                String active = new DecimalFormat("0").format(num);
                Assert.assertEquals(active,exdata);
        }
            else {
                JSONObject activity = final_res.getData().getJSONObject("activity");
                Assert.assertTrue(activity.size() == 0);//size==0说明activity里为空
                }
        }
  }
}
