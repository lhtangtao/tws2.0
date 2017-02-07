package main.testcase.GatheringList;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.getMySql.*;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.setMySql.SetGetToken;
import static main.unit.url_to_add.url_base;
import static main.unit.url_to_add.url_base_noshop;

/**
 * Created by tangtao on 2016/4/18.
 */
public class collectionSchedule{
    String testUrl = "/api/shop/order/detail";
    public String url_to_start() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        SetGetToken();
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("shoukuandingdan.xls");
        Object[][] result = gettestdata(in, "collectionSchedule");
        return result;
    }
    @Test(dataProvider = "ex")
    public void collectionSchedule(String orderNo,String orderType,String exres,String excode) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
       if(orderNo.equals("空")){
           orderNo="";
       }
        if(orderType.equals("空")){
            orderType="";
        }
        String url=url_to_start()+"?orderNo="+orderNo+"&orderType="+orderType+"&sessionGuid="+getSessionGuid()+"&token="+getToken()+"&shopId=11c8f677-9fe0-4fc8-b895-76e4b86e5dd9";
        System.out.println(url);
        String result=httpGet(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        /*
        Map<String, Object> map = Json2Map(result);
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//*/
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
    }
}
