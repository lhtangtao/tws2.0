package main.testcase.Other;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;
import static main.unit.url_to_add.url_base_noshop;

/**
 * Created by tangtao on 2016/4/19.
 */
public class getStartDiagram {
    String testUrl = "/api/app/cover";
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("qita.xls");
        Object[][] result = gettestdata(in, "getStartDiagram");
        return result;
    }
    @BeforeClass
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @Test(dataProvider = "ex")
    public void gsd_code(String screenWidth,String screenHeight,String excode,String exres){
        if(screenHeight.equals("空")){
            screenHeight="";
        }
        if(screenWidth.equals("空")){
            screenWidth="";
        }
        int ScreenWidth=Integer.parseInt(screenWidth);
        int ScreenHeight=Integer.parseInt(screenHeight);
        String url=url_to_start()+"?screenHeight="+ScreenHeight+"&screenWidth="+ScreenWidth+url_base();
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
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);*/
    }
}
