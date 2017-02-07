package main.testcase.Wallet;

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

import static main.unit.httpput.httpput;
import static main.unit.json_bulit.json_built;
import static main.unit.readexcel.gettestdata;
import static main.unit.withdraw.witndraw_al;
import static main.unit.withdraw.witndraw_un;

/**
 * Created by tangtao on 2016/5/27.
 * 提现已认证
 */
public class withdraw_already {
    private String testUrl ="/api/ucenter/balance/shop/withdrawing";
    private String baseUrl= PropertiesHandle.readValue("baseUrl");
    String url = baseUrl + testUrl;
    @DataProvider(name = "ex")
    public static Object[][] words() throws IOException {
        InputStream in = readexcel.class.getClassLoader().getResourceAsStream("qianbao.xls");
        Object[][] result = gettestdata(in, "Withdraw-al");
        return result;
    }
    @BeforeClass
    public void setup() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, InterruptedException {
          witndraw_al();
    }
    @Test(dataProvider = "ex")
    public void w_al(String shopid,String account,String bankNo,String bankName,String branchName,String amount,String password,String exres, String excode, String exmessage) throws InterruptedException, IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
    url = url.replaceAll("\\\\", "");//解决url转义符的问题
        float Amount=Float.parseFloat(amount);
        if(shopid.equals("空")){
            shopid="";
        }
        if(account.equals("空")){
            account="";
        }
        if(bankNo.equals("空")){
            bankNo="";
        }
        if(bankName.equals("空")){
            bankName="";
        }
        if(branchName.equals("空")){
            branchName="";
        }
        if(password.equals("空")){
            password="";
        }
        JSONObject jsonParam=json_built();
        jsonParam.put("account", account);
        jsonParam.put("shopId", shopid);
        jsonParam.put("bankNo", bankNo);
        jsonParam.put("bankName", bankName);
        jsonParam.put("branchName", branchName);
        jsonParam.put("amount", Amount);
        jsonParam.put("password", password);
//        jsonParam.put("shopId", "11c8f677-9fe0-4fc8-b895-76e4b86e5dd9");
//        jsonParam.put("account", "汤涛");
//        jsonParam.put("bankNo", "123123456632123000");
//        jsonParam.put("bankName", "中国工商银行");
//        jsonParam.put("branchName", "杭州滨江支行");
//        jsonParam.put("amount", "2.2");
//        jsonParam.put("password", "32005beb6cc4314fe07aa6b0c9208f99");
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
    }
}