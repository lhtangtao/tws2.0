package main.unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import net.sf.json.JSONObject;


/**
 * Created by tangtao on 2016/4/15.
 */
public class httpput {
    public static String httpput(String url, JSONObject keyArg) {
        int responseCode = -1;
        StringBuilder output = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPut request = new HttpPut(url);
            StringEntity params =new StringEntity(String.valueOf(keyArg),"UTF-8");
            params.setContentType("application/json");
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "*/*");
            request.addHeader("Accept-Encoding", "gzip,deflate,sdch");
            request.addHeader("Accept-Language", "en-US,en;q=0.8");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            responseCode = response.getStatusLine().getStatusCode();
            if (response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204) {
                BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
                //System.out.println("Output from Server ...." + response.getStatusLine().getStatusCode() + "\n");
                String tmp;
                while ((tmp = br.readLine()) != null) {
                   output.append(tmp).append("\n");
                }
            }
            else{
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

        }catch (Exception ex) {
        } finally {
           // System.out.println(responseCode);
            httpClient.getConnectionManager().shutdown();
        }
        return output.toString();
    }
//public static String httpput(String url,JSONObject jsonParam) throws IOException {
//    HttpClient client = HttpClientBuilder.create().build();
//    HttpPut put = new HttpPut(url);
//    put.setHeader("Content-Type","application/json");
//    put.addHeader("Accept", "application/json");
//    StringEntity params =new StringEntity(jsonParam.toString());
//    put.setEntity(params);
//    HttpResponse response = client.execute(put);
//    HttpEntity httpEntity = response.getEntity();
//    String result = EntityUtils.toString(httpEntity, "UTF-8");
//    return result;
//}
}
