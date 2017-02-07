package main.testcase.ManagementClerk;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.TestJsonResult_new;
import main.unit.readexcel;
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
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/4/19.
 */
public class queryAssistantInformation {
    String testUrl = "/api/ucenter/employee/helper";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("dianyuanguanli.xls");
        Object[][] result = gettestdata(in, "qai");
        return result;
    }

    @Test(dataProvider = "ex")
    public void QAI_code(String employeeMobile,String excode,String exres,String exdata){
        if(employeeMobile.equals("空")){
            employeeMobile="";
        }
        String url=url_to_start()+"?employeeMobile="+employeeMobile+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult_new final_res = gs.fromJson(result, TestJsonResult_new.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        if(final_res.success.equals(true)){
            if(exdata.equals("空")){
                Assert.assertNull(final_res.getData());
            }
            else {
                Assert.assertNotNull(final_res.getData());
            }
        }
    }
}
