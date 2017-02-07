package main.testcase.ReverseScan;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;

/**
 * Created by tangtao on 2016/4/20.
 */
public class fansaopay {
    private String testUrl ="/api/customerScan/weichat/order";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("fansao.xls");
        Object[][] result = gettestdata(in, "fp");
        return result;
    }
    @Test(dataProvider = "ex")
    public void fsp(String shopCode,String amount,String openId,String shopName,String exres,String excode,String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        if(shopCode.equals("空")){
            shopCode="";
        }
        if(openId.equals("空")){
            openId="";
        }
        if(shopName.equals("空")){
            shopName="";
        }
        float Amount=Float.parseFloat(amount);
        JSONObject jsonParam=json_built();
        jsonParam.put("shopCode", shopCode);
        jsonParam.put("amount",Amount);//添加json内容
        jsonParam.put("openId", openId);
        jsonParam.put("shopName", shopName);
        String result=httpPost(url,jsonParam);
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
    }
}