package main.testcase.points;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.setMySql.SetGetToken;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/5/19.
 */
public class notice {
    String testUrl = "/api/activity/callback/notice";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("jifen.xls");
        Object[][] result = gettestdata(in, "notice");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SetGetToken();
    }
    @Test(dataProvider = "ex")
    public void records(String appKey, String timestamp, String success,String orderNum,String sign,String exres, String excode){
        if(appKey.equals("空")){
            appKey="";
        }
        boolean Success;
        if(success.equals("Y")){
            Success=true;
        }else Success=false;
        if(orderNum.equals("空")){
            orderNum="";
        }
        if(sign.equals("空")){
            sign="";
        }
        float Timestamp=Float.parseFloat(timestamp);
        String url=url_to_start()+"?appKey="+appKey+"timestamp="+timestamp+"success="+Success+"orderNum="+orderNum+"sign="+sign+url_base();
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
       /* Map<String, Object> map = Json2Map(result);
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);*/
    }
}
