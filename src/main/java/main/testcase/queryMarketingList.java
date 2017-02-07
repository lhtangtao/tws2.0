package main.testcase;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;

import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/6/7.
 */
public class queryMarketingList {
    String testUrl = "/api/expansion/shop/records";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "queryMarketingList");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }

    @Test(dataProvider = "ex")
    public void queryMarketingList(String activityId,String activityType,String data,String year,String pagenum,String pagesize,
                                          String exdata,String excode,String exres){
        if(activityId.equals("空")){
            activityId="";
        }
        if(activityType.equals("空")){
            activityType="";
        }
        if(data.equals("空")){
            data="";
        }
        if(year.equals("空")){
            year="";
        }
        if(pagenum.equals("空")){
            pagenum="";
        }
        if(pagesize.equals("空")){
            pagesize="";
        }
        String url=url_to_start()+"?activityId="+activityId+"&activityType="+activityType+"&date="+data+"&year="+year+"&pageNum="+pagenum+"&pageSize="+pagesize+url_base();
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
