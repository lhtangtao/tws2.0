package main.unit;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by tangtao on 2016/4/14
 * 此函数的作用是输入一个url和head会返回一个json格式
 */
public class httppost {
    public static String httpPost(String url,JSONObject jsonParam)  throws ClientProtocolException, IOException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httppost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
        HttpResponse httpResponse;
        httpResponse = closeableHttpClient.execute(httppost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = null;
        result = EntityUtils.toString(httpEntity, "UTF-8");
        closeableHttpClient.close();
        //System.out.print(result);
        return result;
    }
}
