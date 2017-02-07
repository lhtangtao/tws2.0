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
import static main.unit.del_xiaotangge.del_xtg;
import static main.unit.getMySql.getShopID_oldest;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.withdraw.witndraw_al;

/**
 * Created by tangtao on 2016/4/19.
 * 通过select * from employeeshoprelations where EmployeeId='b43c796e-1b43-45e8-a2bb-d84dd01ad034' and CreateTime='2016-03-24 10:09:01'查找一个店铺 确保c0b84457-9957-4d39-944d-b20f8e4ab85f存在
 *确保小汤哥456都不存在 DELETE FROM employeeshoprelations where ShopId='c0b84457-9957-4d39-944d-b20f8e4ab85f' and Remark='小汤哥6'
 *
 */
public class addShopAssistant {
    private String testUrl ="/api/ucenter/shop/employee";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("dianyuanguanli.xls");
        Object[][] result = gettestdata(in, "addShopAssistant");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        del_xtg();
        witndraw_al();
        System.out.println("11111111111111111111");
    }
    @AfterMethod
    public static void teardown() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        del_xtg();//把添加了的名字删除
    }
    @Test(dataProvider = "ex")
    public void asa(String shopId,String employeeName,String employeeUserName, String exres, String excode, String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = baseUrl + testUrl;
        if(shopId.equals("空")){
            shopId="";
        }
        if(employeeName.equals("空")){
            employeeName="";
        }
        if(employeeUserName.equals("空")){
            employeeUserName="";
        }
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        JSONObject jsonParam=json_built();
        jsonParam.put("shopId", shopId);
        jsonParam.put("employeeName", employeeName);//添加json的对象
        jsonParam.put("employeeUsername", employeeUserName);//添加json的对象
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
        if(exmessage.equals("employeeUsername can not be empty!")){
            Assert.assertTrue(final_res.getMessage().equals("employeeUsername can not be empty!")||final_res.getMessage().equals("please input correct phone number!"));
        }
    }
}
