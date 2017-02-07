package main.testcase.Other;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/4/19.
 * 我们后台有设置版本号，假设是2.6，若你传过来的<=2.6，那返回的data为null，反之给出相应的配置列表
 */
public class accessToSystemConfigurationTables {
    private String testUrl ="/api/system/systemConfig";
    @BeforeSuite
    public String url_to_start(){
        String baseUrl =PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("qita.xls");
        Object[][] result = gettestdata(in, "atsct");
        return result;
    }
    @Test(dataProvider = "ex")
    public void atsct(String systemConfigVersion,String exres,String excode,String exdata){
        String url=url_to_start()+"?systemConfigVersion="+systemConfigVersion+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        int code = Integer.parseInt(excode);//强制转换成int类型
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        if(exdata.equals("空")){
            Assert.assertNull(final_res.getData());
        }
        else {
            Assert.assertNotNull(final_res.getData());
        }

    }
}
