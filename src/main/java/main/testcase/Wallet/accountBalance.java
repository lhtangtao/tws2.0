package main.testcase.Wallet;

import main.unit.PropertiesHandle;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.JsonUtil.subJson2Map;
import static main.unit.JsonUtil.subsJson2Map;
import static main.unit.balance_money.setBalance;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;
import static main.unit.url_to_add.url_base_noshop;

/**
 * Created by tangtao on 2016/4/18.
 */
public class accountBalance {
    String testUrl = "/api/ucenter/balance/shop";
    public String url_to_start() {
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start = baseUrl + testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("qianbao.xls");
        Object[][] result = gettestdata(in, "accountBalance");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        setBalance();
    }

    @Test(dataProvider = "ex")
    public void ab(String shopId,String excode, String exres, String exmessage){
        String url=url_to_start()+"?shopId="+shopId+url_base_noshop();
        String result=httpGet(url);
        Map<String, Object> map = Json2Map(result);
        System.out.println(url);
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);
        int res_code = (Integer) map.get("code");
        int code = Integer.parseInt(excode);//强制转换成int类型
        Assert.assertEquals(res_code,code);
        if (exmessage.equals("空")) {
            exmessage = "";
        }
        else {
            Map<String, Object> map2 = subJson2Map(result);
            int res1 =(Integer) map2.get("balance");
            int exdata=Integer.parseInt(exmessage);
            Assert.assertEquals(res1, exdata);
        }

    }
}
