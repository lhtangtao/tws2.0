package main.testcase.IdentityAuthentication;

import main.unit.PropertiesHandle;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/4/20.
 */
public class certificationStatusQuery {
    String testUrl = "/api/ucenter/identification/status";
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("shenfen.xls");
        Object[][] result = gettestdata(in, "csq");
        return result;
    }
    @BeforeSuite
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @Test(dataProvider = "ex")
    public void csq_code(String exdata, String excode, String exres){
        String url=url_to_start()+"?"+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        int code = Integer.parseInt(excode);//强制转换成int类型
        Assert.assertEquals(res_code,code);
        Integer res_message = (Integer) map.get("data");
        Integer Exdata=Integer.parseInt(exdata);
        Assert.assertEquals(res_message,Exdata);
    }
}
