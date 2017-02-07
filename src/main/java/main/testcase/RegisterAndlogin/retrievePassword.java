package main.testcase.RegisterAndlogin;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.default_pw.def_pw;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;

/**
 * Created by tangtao on 2016/4/18.
 */
public class retrievePassword {
    private String testUrl ="/api/service/user/password";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @AfterClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        def_pw();
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("zhucedenglu.xls");
        Object[][] result = gettestdata(in, "retrievePassword");
        return result;
    }
    @Test(dataProvider = "ex")
    public void rp_result(String username,String passWord,String captcha,String exres, String excode, String exmessage ) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        if(username.equals("空")){
            username="";
        }
        if(passWord.equals("空")){
            passWord="";
        }
        if(captcha.equals("空")){
            captcha="";
        }
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        jsonParam.put("username", username);
        jsonParam.put("captcha", captcha);//添加json的对象
        jsonParam.put("password", passWord);//添加json的对象
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
