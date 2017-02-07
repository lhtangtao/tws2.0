package main.testcase.Lottery;

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
import static main.unit.url_to_add.url_base_noshop;

/**
 * Created by tangtao on 2016/5/18.
 */
public class lotteryTimes {
    String testUrl = "/api/shop/lottery/times";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("choujiang.xls");
        final String getAuthCode = "lotterytimes";
        Object[][] result = gettestdata(in, getAuthCode);
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }
    @Test(dataProvider = "ex")
    public void getAuthCode_code(String shopId,String exres,String excode){
        if (shopId.equals("空")) {
            shopId = "";
        }
        String url=url_to_start()+"?shopId="+shopId+url_base_noshop();
        String result=httpGet(url);
        int code = Integer.parseInt(excode);//强制转换成int类型
        System.out.println(url);
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        /*
        Map<String, Object> map = Json2Map(result);
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);*/
    }
}
