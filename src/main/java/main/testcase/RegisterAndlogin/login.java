package main.testcase.RegisterAndlogin;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import main.unit.setMySql;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
 * Created by tangtao on 2016/4/14.
 */
public class login {
    private String testUrl = "/api/user/session";
    private String baseUrl = PropertiesHandle.readValue("baseUrl");
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        setMySql init=new setMySql();
        init.SetGetToken();
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("zhucedenglu.xls");
        Object[][] result = gettestdata(in, "login");
        return result;
    }
    @Test(dataProvider = "ex")
    public void login(String userName, String passWord, String exres, String excode, String exmessage)
            throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam = json_built();
        jsonParam.put("userName", userName);
        jsonParam.put("password", passWord);//添加json内容
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
    }
}