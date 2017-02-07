package main.testcase.Wallet;

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
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.del_xiaotangge.del_withdrawrecords;
import static main.unit.getMySql.getShopID;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.setMySql.SetGetToken;
import static main.unit.url_to_add.url_base;
import static main.unit.url_to_add.url_base_noshop;
import static main.unit.withdraw.witndraw_un;

/**
 * Created by tangtao on 2016/4/18.
 */
public class BeforeTheWithdrawalInformationQuery {
    String testUrl = "/api/ucenter/shop/bank";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("qianbao.xls");
        Object[][] result = gettestdata(in, "bwaq");
        return result;
    }
    @BeforeClass
    public void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        del_withdrawrecords();//删除之前的提现记录
    }
    @Test(dataProvider = "ex")
    public void btwiq(String shopid,String excode,String exres) throws InterruptedException {
        String url=url_to_start()+"?shopId="+shopid+url_base_noshop();
        String result=httpGet(url);
        Thread.sleep(1000);
        System.out.println(result);
        System.out.println(url);
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