package main.testcase;

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

import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.withdraw.witndraw_un;

/**
 * Created by tangtao on 2016/6/3.
 */
public class openMarketing {
    private String testUrl ="/api/expansion/activity/starting";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    String url = baseUrl + testUrl;

    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "openMarketing");
        return result;
    }
    @BeforeClass
    public void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        witndraw_un();
    }
    @Test(dataProvider = "ex")
    public void openMarketing(String activityId,String exres, String excode, String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        if(activityId.equals("空")){
            activityId="";
        }
        JSONObject jsonParam=json_built();
        jsonParam.put("activityId", activityId);
        String result=httpPost(url,jsonParam);
        System.out.println(jsonParam);
        System.out.println(result);
        Boolean exresult;
        if (exmessage.equals("空")) {
            exmessage = "";
        }
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        int code = Integer.parseInt(excode);//强制转换成int类型
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        Assert.assertEquals(final_res.getMessage(),exmessage);
    }
}
