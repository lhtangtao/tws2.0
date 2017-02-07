package main.testcase.Wallet;

import main.unit.PropertiesHandle;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.setMySql.SetGetToken;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/4/18.
 */
public class withdrawalRecordList {
    String testUrl = "/api/ucenter/balance/withdrawRecords";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    private Object[][] excode() {
        return new Object[][] {
                {200,true},
        };
    }
    @BeforeClass
    public void setUp() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        SetGetToken();
    }

    @Test(dataProvider = "ex")
    public void wrl_code(int excode,Boolean exres) {
        String url = url_to_start() + "?" + url_base();
        String result = httpGet(url);
        System.out.println(url);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        Boolean res1 = (Boolean) map.get("success");
        Assert.assertEquals(res1, exres);
        int res2 = (Integer) map.get("code");
        Assert.assertEquals(res2, excode);//res1是判断success
    }
}
