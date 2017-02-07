package main.testcase.points;

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
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/5/19.
 */
public class creditexChange {
    String testUrl = "/api/integral/callback/creditexchange";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("jifen.xls");
        Object[][] result = gettestdata(in, "creditexchange");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }

    @Test(dataProvider = "ex")
    public void records(String uid, String credits1, String appKey,String timestamp,String orderNum,String type,
            String actualPrice,String sign,String status,String bizId,String credits2,String exres, String excode){
        if(uid.equals("空")){
            uid="";
        }
        if(appKey.equals("空")){
            appKey="";
        }
        if(timestamp.equals("空")){
            timestamp="";
        }
        if(orderNum.equals("空")){
            orderNum="";
        }
        if(type.equals("空")){
            type="";
        }
        if(sign.equals("空")){
            sign="";
        }
        if(status.equals("空")){
            status="";
        }
        if(bizId.equals("空")){
            bizId="";
        }
        float Credit1s1=Float.parseFloat(credits1);
        float Credit1s2=Float.parseFloat(credits2);
        int ActualPricee=Integer.parseInt(actualPrice);
        String url=url_to_start()+"?uid="+uid+"credits="+Credit1s1+"appKey="+appKey+"timestamp="+timestamp+"orderNum="+ orderNum+"type="+type+"actualPrice="+
                ActualPricee+"sign="+sign+"status="+status+"bizId="+bizId+"credits="+credits2+url_base();
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
      /* Map<String, Object> map = Json2Map(result);
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);*/
    }
}
