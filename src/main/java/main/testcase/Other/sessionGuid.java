package main.testcase.Other;

import main.unit.PropertiesHandle;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Map;
import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/4/15.
 * 这个接口 只需输入base requests 返回200就好了
 */
public class sessionGuid {
    String testUrl = "/api/ucenter/shoprelations";
    @DataProvider(name = "ex")
    private Object[][] exresult() {
        return new Object[][] {{true,200}};//格式为 t预期结果
    }
    @BeforeClass
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @Test(dataProvider = "ex")
    public void sessionguid_code(Boolean exresult,int excode){
        String url=url_to_start()+"?"+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        int res2 = (Integer) map.get("code");
        Assert.assertEquals(res2, excode);//res1是判断success
        Boolean res1 = (Boolean) map.get("success");
        Assert.assertEquals(res1, exresult);//res1是判断success
    }
}