package main.testcase.ManagementClerk;

import main.unit.PropertiesHandle;
import main.unit.readexcel;
import net.sf.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

import static main.unit.JsonUtil.Json2Map;
import static main.unit.getMySql.getSessionGuid;
import static main.unit.getMySql.getShopID_oldest;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;

/**
 * Created by tangtao on 2016/4/19.
 */
public class deleteShopAssistant {
    private String testUrl ="/api/ucenter/employee/helper";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("dianyuanguanli.xls");
        Object[][] result = gettestdata(in, "qai");
        return result;
    }
    @Test(dataProvider = "exresult")
    public void cp_result(String employeeId,String shopId,Boolean exsuccess) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        jsonParam.put("employeeId", employeeId);
        jsonParam.put("shopId",shopId);//添加json的对象
        String result=httpput(url,jsonParam);
        // System.out.println(jsonParam);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        Boolean res1 = (Boolean) map.get("success");
        Assert.assertEquals(res1, exsuccess);//res1是判断success
    }
    @Test(dataProvider = "excode")
    public void cp_code(String employeeId,String shopId,int excode) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        jsonParam.put("employeeId", employeeId);
        jsonParam.put("shopId",shopId);//添加json的对象
        String result=httpput(url,jsonParam);
        System.out.println(result);
        Map<String, Object> map = Json2Map(result);
        int res1 = (Integer) map.get("code");
        Assert.assertEquals(res1, excode);//res1是判断success
    }
}
