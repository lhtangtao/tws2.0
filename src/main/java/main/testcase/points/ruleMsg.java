package main.testcase.points;

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
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.setMySql.SetGetToken;

/**
 * Created by tangtao on 2016/5/19.
 */
public class ruleMsg {
    private String testUrl = "/api/integral/rule/msg";
    private String baseUrl = PropertiesHandle.readValue("baseUrl");
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SetGetToken();
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("jifen.xls");
        Object[][] result = gettestdata(in, "ruleMsg");
        return result;
    }
    @Test(dataProvider = "ex")
    public void ruleMsg(String openId, String signature, String exres, String excode, String exmessage)
            throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam = json_built();
        jsonParam.put("openId", openId);
        jsonParam.put("signature", signature);//添加json内容
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
        /*
        Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        String res_message = (String) map.get("message");
        Assert.assertEquals(res_message, exmessage);*/
    }
}
