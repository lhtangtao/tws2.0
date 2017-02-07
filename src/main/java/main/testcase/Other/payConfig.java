package main.testcase.Other;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;

/**
 * Created by tangtao on 2016/4/20.
 */
public class payConfig {
    private String testUrl ="/api/config/transferring";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("qita.xls");
        Object[][] result = gettestdata(in, "payconfig");
        return result;
    }
    @Test(dataProvider = "ex")
    public void payconfig(String allowTransffering, String excode, String exres) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        Boolean AllowTransffering;
        if(allowTransffering.equals("Y")){
            AllowTransffering=true;
        }
        else AllowTransffering=false;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        jsonParam.put("allowTransffering",AllowTransffering);
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
        /*Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);*/
    }
}
