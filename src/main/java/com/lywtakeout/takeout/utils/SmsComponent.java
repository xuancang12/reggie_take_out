package com.lywtakeout.takeout.utils;

/**
 * 黄洋林: Huang
 * 2023/4/7 13:29
 */

import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.aliyunsms")
@Data
@Component
public class SmsComponent {
    //private String host;
    //private String path;
    //private String appcode;
    //// private String smsSignId;
    //private String templateId;

    public  boolean sendMsm(String phone, String code, int minute){
        //System.out.println(host);
        //System.out.println(path);
        //System.out.println(appcode);
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + "3000ac3ba157442c820757068dbf0a5a");
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code+",expire_at:"+minute);
        bodys.put("phone_number", phone);
        bodys.put("template_id", "CST_ptdie100");
        try {
            HttpResponse response = HttpUtils.doPost("https://dfsns.market.alicloudapi.com", "/data/send_sms", method, headers, querys, bodys);
            // System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
