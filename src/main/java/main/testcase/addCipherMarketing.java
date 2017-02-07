package main.testcase;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import main.unit.setMySql;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.json_bulit.json_built_noguid;
import static main.unit.json_bulit.json_built_noshopidGuid;
import static main.unit.readexcel.gettestdata;
import static main.unit.withdraw.witndraw_al;

/**
 * Created by tangtao on 2016/6/6.
 */
public class addCipherMarketing {
    private String testUrl = "/api/expansion/activity";
    private String baseUrl = PropertiesHandle.readValue("baseUrl");
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        setMySql init=new setMySql();
        init.SetGetToken();
        witndraw_al();
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "addCipherMarketing");
        return result;
    }
    @Test(dataProvider = "ex")
    public void addCipherMarketing (String sessionguid, String shopid,String discount,String rangeType,String rangeValue,String password,String useCondition,
                                    String obtainCondition ,String passwordCount,String exres, String excode, String exmessage,String exdata)
            throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        if(sessionguid.equals("空")){
            sessionguid="";
        }
        if(shopid.equals("空")){
            shopid="";
        }
        if(discount.equals("空")){
            discount="";
        }
        if(password.equals("空")){
            password="";
        }
        int rangtype=Integer.parseInt(rangeType);
        int rangevalue=Integer.parseInt(rangeValue);
        int usecondition=Integer.parseInt(useCondition);
        int obtaincondition=Integer.parseInt(obtainCondition);
        int passwordcount=Integer.parseInt(passwordCount);
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam =json_built_noshopidGuid();
        jsonParam.put("sessionGuid", sessionguid);
        jsonParam.put("shopId", shopid);
        jsonParam.put("discount", discount);
        jsonParam.put("rangeType", rangtype);
        jsonParam.put("rangeValue", rangevalue);
        jsonParam.put("password", password);
        jsonParam.put("useCondition", usecondition);
        jsonParam.put("obtainCondition", obtaincondition);
        jsonParam.put("passwordCount", passwordcount);
        String result = httpPost(url, jsonParam);
        System.out.println(jsonParam);
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        if (exmessage.equals("空")) {
            exmessage = "";
        }
        int code = Integer.parseInt(excode);//强制转换成int类型
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        Assert.assertEquals(final_res.message,exmessage);
        if(exdata.equals("空")){
            Assert.assertNull(final_res.getData());
        }
        else  Assert.assertNotNull(final_res.getData());
    }
}
