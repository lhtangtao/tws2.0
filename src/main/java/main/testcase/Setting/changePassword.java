package main.testcase.Setting;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.default_pw.def_pw;
import static main.unit.getMySql.getSessionGuid;
import static main.unit.getMySql.getToken;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.json_bulit.json_built_noguid;
import static main.unit.readexcel.gettestdata;

/**
 * Created by tangtao on 2016/4/19.
 */
public class changePassword {
    private String testUrl ="/api/ucenter/employee";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @BeforeMethod//每次运行testcase前都会运行重置密码
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        def_pw();
    }
    @AfterClass//运行后把密码改回来
    public static void teardown() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        def_pw();
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("shezhi.xls");
        Object[][] result = gettestdata(in, "changePassword");
        return result;
    }
    @Test(dataProvider = "ex")
    public void cp_code(String oldPassword,String newPasswordw,String SessionGuid, String exres, String excode, String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        if(oldPassword.equals("空")){
            oldPassword="";
        }
        if(newPasswordw.equals("空")){
            newPasswordw="";
        }
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built_noguid();
        jsonParam.put("sessionGuid",SessionGuid);
        jsonParam.put("oldPassword", oldPassword);
        jsonParam.put("newPasswordassword",newPasswordw);//添加json的对象
        String result=httpput(url,jsonParam);
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
        /*
        Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        if (exmessage.equals("空")) {
            exmessage = "";
        }
        String res_message = (String) map.get("message");
        Assert.assertEquals(res_message, exmessage);*/
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        Assert.assertEquals(final_res.message,exmessage);
    }
}
