package main.testcase.ManagementClerk;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.TestJsonResult_Array;
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
import static main.unit.getMySql.getShopID;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;
import static main.unit.url_to_add.url_base_noshop;

/**
 * Created by tangtao on 2016/4/19.
 */
public class shopAssistantList {
    String testUrl = "/api/ucenter/shop/employee";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("dianyuanguanli.xls");
        Object[][] result = gettestdata(in, "sal");
        return result;
    }
    @Test(dataProvider = "ex")
    public void SAL_code(String shopId,String pagesize,String pagenumber,String excode,String exres){
        if(shopId.equals("空")){
            shopId="";
        }
        if(pagesize.equals("空")){
            pagesize="";
        }
        if(pagenumber.equals("空")){
            pagenumber="";
        }
        String url=url_to_start()+"?shopId="+shopId+"&pageSize="+pagesize+"&pageNumber="+pagenumber+url_base_noshop();
        String result=httpGet(url);
        int code = Integer.parseInt(excode);//强制转换成int类型
        System.out.println(url);
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult_Array final_res = gs.fromJson(result, TestJsonResult_Array.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        /*
        Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        */
    }
}

//http://192.168.1.16/api/ucenter/shop/employee?
// shopID=11c8f677-9fe0-4fc8-b895-76e4b86e5dd9&pageSize=21&pageNumber=12&systemVersion=1&clientIP=192.168.9.55&deviceNumber=d75ddec0-9194-48e3-ae23-10e091d69cbe&
//        11c8f677-9fe0-4fc8-b895-76e4b86e5dd9
// sessionGuid=b43c796e-1b43-45e8-a2bb-d84dd01ad034&token=aa1f4d07-bb89-448d-95e6-fd62f66a0187&appVersion=1
//             b43c796e-1b43-45e8-a2bb-d84dd01ad034