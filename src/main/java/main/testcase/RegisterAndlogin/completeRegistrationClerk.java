package main.testcase.RegisterAndlogin;

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
import static main.unit.getMySql.getToken;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.setMySql.SetGetToken;

/**
 * Created by tangtao on 2016/4/15.
 * 店员 完成注册接口 基本不会出错 无需在意
 */
public class completeRegistrationClerk {
    private String testUrl ="/api/ucenter/personal/detail";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("zhucedenglu.xls");
        Object[][] result = gettestdata(in, "completeRegistrationClerk");
        return result;
    }

    @BeforeClass
    public static  void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SetGetToken();
    }

    @Test(dataProvider = "ex")
    public void cp_result(String name,String Sex,String birthday,String excode,String exres) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        if (name.equals("空")) {
            name = "";
        }
        if (birthday.equals("空")) {
            birthday = "";
        }
        int sex;
        if (Sex.equals("空")) {
            Sex = "";
            sex=99;
        }
        else sex= Integer.parseInt( Sex);
        JSONObject jsonParam=json_built();
        jsonParam.put("token",getToken());
        jsonParam.put("name",name);
        jsonParam.put("birthday",birthday);
        jsonParam.put("sex",sex);//添加json的对象
        String result=httpput(url,jsonParam);
        System.out.println(jsonParam);
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
      /*  Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result,exresult );
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);*/

    }
}
