package main.testcase.Other;

import main.unit.PropertiesHandle;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/4/20.
 */
public class fuzzyQueryShopList {
    String testUrl = "/api/shop";
    @DataProvider(name = "exresult")
    private Object[][] exresult() {
        return new Object[][] {
                {"我们","00745c58-80a4-4f2f-98d3-48e930389e71",true},//
        };
    }
    @DataProvider(name = "excode")
    private Object[][] excode() {
        return new Object[][] {
                {"杭州","7961267e-cac3-4378-bfb4-df09b1590642",200},//
        };
    }
    @BeforeSuite
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @Test(dataProvider = "excode")
    public void fqcl_code(String cityid,String shopname,int excode){
        String url=url_to_start()+"?shopName="+shopname+"&cityId="+cityid+url_base();
        String result=httpGet(url);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        int res2 = (Integer) map.get("code");
        Assert.assertEquals(res2, excode);//res1是判断success
    }
    @Test(dataProvider = "exresult")
    public void fqcl_result(String cityid,String shopname,Boolean exresult){
        String url=url_to_start()+"?shopName="+shopname+"&cityId="+cityid+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        Boolean res1 = (Boolean) map.get("success");
        Assert.assertEquals(res1, exresult);//res1是判断success
    }
}

