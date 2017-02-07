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
import static main.unit.del37to39.del37to39;
import static main.unit.del_shop1to3.delshop1to3;
import static main.unit.httppost.httpPost;
import static main.unit.json_bulit.json_built;
import static main.unit.json_bulit.json_built_shopid;
import static main.unit.readexcel.gettestdata;

/**
 * Created by tangtao on 2016/4/15.
 * 首选要确保shopname 测试店123都是未被占用的
 DELETE FROM shops WHERE name ='测试店1'
 哦哦这个点是被占用的
 店长完成注册接口
 主要测试地址和店名的长度限制
 */
public class managerCompleteRegistration {
    private String testUrl ="/api/ucenter/shop/detail";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    String url = baseUrl + testUrl;
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("zhucedenglu.xls");
        Object[][] result = gettestdata(in, "managerCompleteRegistration");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        delshop1to3();
    }
    @AfterClass//最后的扫尾工作 清理名字为测试店1-3的数据
    public static void teardown() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        delshop1to3();
    }
    @Test(dataProvider = "ex")
    public void mcr_exresult(String shopname,String cityId,String address,String excode, String exres, String exmessage) throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        url = url.replaceAll("\\\\", "");//解决url转义符的问题
        if(shopname.equals("空")){
            shopname="";
        }
        if(address.equals("空")){
            address="";
        }
        if(cityId.equals("空")){
            cityId="";
        }
        JSONObject jsonParam=json_built_shopid();
        jsonParam.put("shopName", shopname);
        jsonParam.put("cityId",cityId);//添加json的对象
        jsonParam.put("address", address);
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
    }
}
