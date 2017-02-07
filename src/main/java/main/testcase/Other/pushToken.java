package main.testcase.Other;

import main.unit.PropertiesHandle;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;

/**
 * Created by tangtao on 2016/4/20.
 */
public class pushToken {
    private String testUrl ="/api/ucenter/device";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    private Object[][] exresult() {
        return new Object[][]{
                {true,200},//输入正确的格式
        };
    }

    @Test(dataProvider = "ex")
    public void pt_result(Boolean exsuccess,int excode) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        String result=httpput(url,jsonParam);
        System.out.println(jsonParam);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        Boolean res1 = (Boolean) map.get("success");
        Assert.assertEquals(res1, exsuccess);//res1是判断success
        int res2 = (Integer) map.get("code");
        Assert.assertEquals(res2, excode);//res1是判断success
    }
}
