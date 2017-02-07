package main.testcase.ReverseScan;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
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
 * Created by tangtao on 2016/4/20.
 */
public class isActiveCode {
    String testUrl = "/api/shop/qrcode/relation";
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("fansao.xls");
        Object[][] result = gettestdata(in,"iac");
        return result;
    }
    @BeforeClass
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }

    @Test(dataProvider = "ex")
    public void iac(String shopId,String exdata,String exres,String excode){
        String url=url_to_start()+"?shopId="+shopId+url_base_noshop();
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
        Assert.assertEquals(final_res.success,exresult);
        Assert.assertEquals(final_res.code,code);
        if(final_res.success.equals(true)){
        	 Assert.assertEquals(final_res.getData().getInt("state"),Integer.parseInt(exdata));
        }
       /* Map<String, Object> map = Json2Map(result);
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);*/
    }
}
