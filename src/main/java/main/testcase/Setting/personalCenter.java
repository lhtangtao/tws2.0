package main.testcase.Setting;

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
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;
import static main.unit.url_to_add.url_base_noshop;

/**
 * Created by tangtao on 2016/4/18.
 */
public class personalCenter {
    String testUrl = "/api/ucenter/module/personal";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("shezhi.xls");
        Object[][] result = gettestdata(in, "personalCenter");
        return result;
    }
    @Test(dataProvider = "ex")
    public void personCentre_result(String shopId,String excode, String exres,String exdata){
        if(shopId.equals("空")){
            shopId="";
        }
        String url=url_to_start()+"?shopId="+shopId+url_base_noshop();
        String result=httpGet(url);
        System.out.println(url);
        int code = Integer.parseInt(excode);//强制转换成int类型
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        if(exdata.equals("空")){
            Assert.assertNull(final_res.getData());
        }
        else {
            Assert.assertNotEquals(final_res.getData(),null);
        }
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        /*Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);*/
    }
}
