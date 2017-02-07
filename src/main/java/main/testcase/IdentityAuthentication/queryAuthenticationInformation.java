package main.testcase.IdentityAuthentication;

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
import static main.unit.url_to_add.url_base_noshop_noguid;

/**
 * Created by tangtao on 2016/4/20.
 */
public class queryAuthenticationInformation {
    String testUrl = "/api/ucenter/identification";

    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("shenfen.xls");
        Object[][] result = gettestdata(in, "qai");
        return result;
    }
    @BeforeClass
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @Test(dataProvider = "ex")
    public void qai(String shopid,String employeeid,String excode,String exres){
        String url=url_to_start()+"?&sessionGuid="+employeeid+"&shopId="+shopid+url_base_noshop_noguid();
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
    }
}
