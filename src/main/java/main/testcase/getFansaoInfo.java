package main.testcase;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/6/8.
 */
public class getFansaoInfo {
    String testUrl = "/api/customerScan/success/orderInfo";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "getFansaoInfo");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }

    @Test(dataProvider = "ex")
    public void getFansaoInfo(String orderId,String exres,String excode){
        if (orderId.equals("空")) {
            orderId = "";
        }
        String url=url_to_start()+"?orderId="+orderId+url_base();
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
    }
}
