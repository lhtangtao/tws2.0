package main.testcase.RegisterAndlogin;
import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import static main.unit.JsonUtil.Json2Map;
import static main.unit.del37to39.del37to39;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;

/*
 * Created by tangtao on 2016/4/15.
 * 注册第一部接口
 * 预置条件 15658890633这个电话号码存在
 * 15658890637-39这三个号码在数据表中不存在
 * 且每次用完后需要自己手动删除 删除SQL语言如下所示
 * DELETE FROM employees WHERE UserName='15658890637'
 */
public class register {
    private String testUrl ="/api/user/registration/mobile";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    String url = baseUrl + testUrl;

    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("zhucedenglu.xls");
        Object[][] result = gettestdata(in, "register");
        return result;
    }
    @AfterClass//最后的扫尾工作 清理手机号码为15658890637-39的号码
    public static void teardown() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        del37to39();
    }
    @Test(dataProvider = "ex")
    public void register_result(String username,String password,String captcha,String excode,String exres,String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        if (username.equals("空")) {
            username = "";
        }
        if (password.equals("空")) {
            password = "";
        }
        if (captcha.equals("空")) {
            captcha = "";
        }
        if (excode.equals("空")) {
            excode = "";
        }
        if (exres.equals("空")) {
            exres= "";
        }
        if (exmessage.equals("空")) {
            exmessage= "";
        }
        JSONObject jsonParam=json_built();
        jsonParam.put("username", username);
        jsonParam.put("password", password);//添加json的对象
        jsonParam.put("captcha", captcha);//添加json的对象
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
        Assert.assertEquals(res_result, exresult);
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        String res_message = (String) map.get("message");
        Assert.assertEquals(res_message, exmessage);*/
    }
}
