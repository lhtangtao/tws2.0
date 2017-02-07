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
 * Created by tangtao on 2016/4/19.
 */
public class upgradeToRemind {
    String testUrl = "/api/system/appVersion";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    private Object[][] exresult() {
        return new Object[][] {{200,true}};//格式为 t预期结果
    }

    @Test(dataProvider = "ex")
    public void utr_code(int excode,Boolean exresult){
        String url=url_to_start()+"?"+url_base();
        String result=httpGet(url);
        Map<String, Object> map = Json2Map(result);
        System.out.println(result);
        int res2 = (Integer) map.get("code");
        Assert.assertEquals(res2, excode);//res1是判断success
        Boolean res1 = (Boolean) map.get("success");
        Assert.assertEquals(res1, exresult);//res1是判断success
    }
}
