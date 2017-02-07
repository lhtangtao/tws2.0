package main.testcase;

import com.google.gson.Gson;
import main.unit.PropertiesHandle;
import main.unit.TestJsonResult_Array;
import main.unit.TestJsonResult_new;
import main.unit.readexcel;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static main.unit.getMySql.getCode;
import static main.unit.httpget.httpGet;
import static main.unit.readexcel.gettestdata;
import static main.unit.url_to_add.url_base;

/**
 * Created by tangtao on 2016/6/3.
 */
public class DeafaltCommand {
    String testUrl = "/api/expansion/password/default";
    public String url_to_start(){
        String baseUrl = PropertiesHandle.readValue("baseUrl");
        String url_to_start=baseUrl+testUrl;
        return url_to_start;
    }
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("yingxiao.xls");
        Object[][] result = gettestdata(in, "DeafaltCommand");
        return result;
    }
    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        //SetGetToken();
    }


    @Test(dataProvider = "ex")
    public void getMarketingInfo(String excode,String exres) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url=url_to_start()+"?"+url_base();
        String result=httpGet(url);
        System.out.println(url);
        System.out.println(result);
        int code = Integer.parseInt(excode);//强制转换成int类型
        Boolean exresult;
        if (exres.equals("Y")) {
            exresult = true;
        } else exresult = false;
        Gson gs = new Gson();
        TestJsonResult_new final_res = gs.fromJson(result, TestJsonResult_new.class);
        Assert.assertEquals(final_res.code,code);
        Assert.assertEquals(final_res.success,exresult);
        boolean data_res=false;
        String exdata=final_res.getData();
        getCode();//返回一个list 里面全是随机的码
        String []  array = getCode();//读取数据库中的所有随机数
        int i=0;
        while (i<array.length){
            if(exdata.equals(array[i])){
                data_res=true;
                break;
            }
            i++;
        }
        Assert.assertTrue(data_res);//查看随机数是不是匹配
    }
}
