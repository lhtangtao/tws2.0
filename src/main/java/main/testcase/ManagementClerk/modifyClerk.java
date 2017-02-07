package main.testcase.ManagementClerk;

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
import static main.unit.getMySql.getSessionGuid;
import static main.unit.getMySql.getShopID;
import static main.unit.getMySql.getShopID_oldest;
import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.undoModify.undoMod;

/**
 * Created by tangtao on 2016/4/19.
 * 确保employid b43c796e-1b43-45e8-a2bb-d84dd01ad034 存在 select * from employeeshoprelations where ShopId='c0b84457-9957-4d39-944d-b20f8e4ab85f'
 */
public class modifyClerk {
    private String testUrl ="/api/ucenter/shop/employee";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("dianyuanguanli.xls");
        Object[][] result = gettestdata(in, "modifyClerk");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        undoMod();
    }
    @AfterClass
    public static void teardown() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        undoMod();
    }

    @Test(dataProvider = "ex")
    public void modify(String shopid,String employeeId,String employeeName,String employeeUsername,String exres, String excode, String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        if(shopid.equals("空")){
            shopid="";
        }
        if(employeeId.equals("空")){
            employeeId="";
        }
        if(employeeName.equals("空")){
            employeeName="";
        }
        if(employeeUsername.equals("空")){
            employeeUsername="";
        }
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        jsonParam.put("shopId",shopid);
        jsonParam.put("employeeId",employeeId);//添加json的对象
        jsonParam.put("employeeName",employeeName);//添加json的对象
        jsonParam.put("employeeUsername",employeeUsername);//添加json的对象
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
        Gson gs = new Gson();
        TestJsonResult final_res = gs.fromJson(result, TestJsonResult.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        Assert.assertEquals(final_res.message,exmessage);
     /*   Map<String, Object> map = Json2Map(result);
        Boolean res_result = (Boolean) map.get("success");
        Assert.assertEquals(res_result, exresult);//
        int res_code = (Integer) map.get("code");
        Assert.assertEquals(res_code,code);
        String res_message = (String) map.get("message");
        Assert.assertEquals(res_message, exmessage);*/
    }
}
