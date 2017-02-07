package main.testcase.points;

import main.unit.PropertiesHandle;
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
public class checkin {
    private String testUrl = "/api/activity/checkin";
    private String baseUrl = PropertiesHandle.readValue("baseUrl");
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SetGetToken();
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("jifen.xls");
        Object[][] result = gettestdata(in, "checkin");
        return result;
    }
    @Test(dataProvider = "ex")
    public void checkin(String systemId, String openId, String platformType,String signature,String exres, String excode, String exmessage)
            throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        if(systemId.equals("空")){
            systemId="";
        }
        if(openId.equals("空")){
            openId="";
        }
        if(signature.equals("空")){
            signature="";
        }
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam = json_built();
        int PlatformType=Integer.parseInt(platformType);
        jsonParam.put("systemId", systemId);
        jsonParam.put("openId", openId);//添加json内容
        jsonParam.put("platformType", PlatformType);
        jsonParam.put("signature", signature);//添加json内容
        String result = httpPost(url, jsonParam);
        System.out.println(jsonParam);
        System.out.println(result);
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        int code = Integer.parseInt(excode);//强制转换成int类型
        Assert.assertEquals(res_code,code);
        if (exmessage.equals("空")) {
            exmessage = "";
        }
        String res_message = (String) map.get("message");
        Assert.assertEquals(res_message, exmessage);
    }
}
