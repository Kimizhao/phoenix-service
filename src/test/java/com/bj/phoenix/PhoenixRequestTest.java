package com.bj.phoenix;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
public class PhoenixRequestTest {
    private static final int SUCCESS_CODE = 200;

    public static String sendPost(String url, String map) throws Exception{
        //我们可以使用一个Builder来设置UA字段，然后再创建HttpClient对象
        HttpClientBuilder builder = HttpClients.custom();
        //对照UA字串的标准格式理解一下每部分的意思
        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
        CloseableHttpClient httpClient = builder.build();
        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(30000).setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000).setRedirectsEnabled(true).build();

        HttpPost httpPost = new HttpPost(url);//前面字符串为数据服务的地址
        httpPost.setHeader("Content-Type","application/json");
        //httpPost.setHeader("Authorization","Bearer\n"+ JWT);
        //设置超时时间
        httpPost.setConfig(requestConfig);
        try {
            StringEntity stringEntity = new StringEntity(JSON.toJSONString(map));
            //设置post求情参数
            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String strResult = "";
            if(httpResponse != null){
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == SUCCESS_CODE) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                } else{
                    log.warn("sendPost请求异常,Error Response: {}",httpResponse.getStatusLine().toString());
                    throw new RuntimeException("sendPost请求异常,Error Response: " + httpResponse.getStatusLine().toString());
                }
            }
            return strResult;
        } finally {
            try {
                if(httpClient != null){
                    httpClient.close(); //释放资源
                }
            } catch (IOException e) {
                log.error("sendPost连接关闭异常",e);
                throw new IOException("sendPost连接关闭异常",e);
            }
        }
    }




    public static void main(String[] args) throws Exception {
        String result = sendPost("http://localhost:8080/user/1", "1");
        log.info(result);
        UserReceive user = JSON.parseObject(result, UserReceive.class);
        log.info(user.getName());
        log.info(user.toString());
    }
}
