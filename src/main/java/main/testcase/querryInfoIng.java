package main.testcase;

import com.google.gson.Gson;
import main.unit.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static main.unit.createSign.createSignature;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_PLATFORMBASEREQUEST;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/6/7.
 */
public class querryInfoIng {
    String testUrl = "/api/expansion/customer/discount";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "querryInfoIng");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }
    @Test(dataProvider = "ex")
    public void querryInfoIng(String openid,String paytype,String pagenum,String pagesize,String excode,String exres){
        String url=url_to_start()+"?openId="+openid+"payType="+paytype+"pageNum="+pagenum+"pageSize="+pagesize+url_base()+url_PLATFORMBASEREQUEST();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult_Array final_res = gs.fromJson(result, TestJsonResult_Array.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
    }
}
