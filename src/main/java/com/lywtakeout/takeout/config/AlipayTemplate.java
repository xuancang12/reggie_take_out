package com.lywtakeout.takeout.config;

/**
 * 黄洋林: Huang
 * 2023/4/19 19:28
 */

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.lywtakeout.takeout.entity.PayVo;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//
//@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public String app_id = "2021000122664334";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDCblKPQK08O9UOl2vSN5ZDnN2pe717kKtXpEEPR+ycFw709saI9/N0n9OBmYJYQnut8BJ+eBvzj42cFVs1d2+S9sVxWSrW3wOnbacEoFJS13YK7DoMFeem+6dYPcBs5zR8KmcZPLQGRC+td3+9WrqPtXnVPxCPOBQJAt9u4utB2dPfW9ejdmz2w350c9l+obS5SeoJFKaKxYbRRpcpKn829i+wKUCzWfYdHfAXipP4HbORdbMNb684cNIaDPYr+35TzUhAY+CZt+iAIn28VeyNcbS5art4zUIjcbifgj0OJ8H2XrwOrEQmht6KAw1yHyWv7tx7iMRsQcsp4hPg3CfAgMBAAECggEAR2LpvYblTZugVluBpDdnYk7cGjwlpXcKfhZxnESMWxKZPMKar2/xwdg2IFSeC+gAmDskl2h4m5BZFiE2eJc+OShsDz09LZkmSXQkAnyWUE6trlGHC8ciRLcgVD558kReX4Tjcd8Q5Mty8uJzMS2T9E8NCvyifJdbgG1SkUm1uynws5wYVTy294b1epw9rA4d7sgJT8sjl87Ll8d7arIo+uDRymYJ5LMrqAvS6RZcA7zbGCN2baiD/57lPq8X86CZblR3ie/GPvlfzsMlhueRLEXn+292TUYh6AJoZ30cZLXtCQRcoL367hoKCxrFdxkA4jGvO6t68LU572tqisI/2QKBgQC9WjpzXYoYYpDR58nbhylJ1da85ms3C1/p5uJWB5OPC7lWgfhcqkJ9meVHLuyTI5pV6HeoDOA6VDvd9wfs56Zhsr2ROyHK2C/O4NdVaRJSl1tlZgB0ryfBdH4u3y6mwIgzld7mya7RbNTnMesaw97Mr3mimvNDLJIvfsT84P0O9QKBgQCxKQSh1eKbBPhfJoS97SUhZBCIFZcnVsMsydl2TAkKgA+nP7ACWC0uyIwzNRpkZkvx3jGZ+H7tj0vo70jPuxAQt9hykh+GYOxhq4V8CpcQ1isjL103IP54fS2n39Ic3Atw3cnAcp5Yt1wWS4t9RB7C9ed9TpbYUmTmB4vtmT1cwwKBgBe4f+4H387iOiYxlVYM/p0jTr1nWDMTF7bjl6MTW+blnxg5O/XP1niJmPFr7XMZWLI+Zw8EXeqwtrc9xR5mfq9qhtj0wWz53DNcc11dxk/Wajqvpor3jlFr2IripqN5JxbfRYc/6MiPi7RSypMVyol1yhUWuDbIFKAij2bmgI0pAoGAd7pT4heNjNygCpYakczqEhXxQjbtL23oe6VyrQsX9Ru0+IAHMJ5s8rggYB8cVgv4eV6DwR36kgwM7kCAwbsXUWUeB9gPLB7mwd9MWBdEHuHOx3mkErTepYlbyJ9v/YcMyFtrlP8hWq2Ys4ycmazm6TWNG9d4BHwyqm8hNxMgMEcCgYBmVDEYGxIOtWadnZwGoebKSUWGMRtHAwFl5O/ZLTGVkJZnoLDNkF/fmDNAP+lhrDul3wKmDTWSPsLT74kIyXGslJMn33WaQZw5t1WO901Uh+gAJAG7WNG98CrgzsS/+dTNg38nVyyorAsiF6DM9Cfs7l0MUj/PBwS623oXtuuKxw==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoomsSkao5Q9dz/l/9nNSfxCLS1/F3vEAHSm7yVuhH7IXiFk1zPgxKOtni0K5gy5/1eHnYpa7eCwRzy6x3QmLPd/HPURzCRrNm/yROnwvSZ6Q/+3yGxMAzvN9dx6AkLHfGNN0TD+uJfy6k+55gXRv2rfoAHWXu0T0+MF+72WkVKjLJjQHARnLIBeinlrDgiX06sPs8xt+Bh9tNaS+MCP5k2mJsBvt2oO3gYoW15RQmUqxqtopzUbtT5h3lHnsXqSEIuTrG+xGUl/pSbqmy+M9v+82I7Kp74LRiPGbBMdezLkvz8FWQ8f2NknHfZpMeai+Iv5R7+6dDAObL0HF85zdbwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    public String notify_url = "http://7a4548.natappfree.cc/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    public String return_url = "http://localhost:8080/front/page/add-order.html";

    // 签名方式
    public String sign_type = "RSA2";

    // 字符编码格式
    public String charset = "utf-8";


    public  String FORMAT ="JSON";

    //订单超时时间
    private String timeout = "2h";

    private String LOG_PATH = "/log";

    //// 支付宝网关； https://openapi.alipaydev.com/gateway.do
    //public String gatewayUrl;
    // 支付宝网关（沙箱环境）
    public  String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradeWapPayRequest alipayTradeWapPayRequest = new AlipayTradeWapPayRequest();
        //AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //alipayRequest.setReturnUrl(return_url);
        //alipayRequest.setNotifyUrl(notify_url);

        alipayTradeWapPayRequest.setReturnUrl(return_url);
        alipayTradeWapPayRequest.setNotifyUrl(notify_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayTradeWapPayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayTradeWapPayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }

    public String pay1(PayVo vo, HttpServletResponse response) throws AlipayApiException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, app_id,merchant_private_key, FORMAT , charset, alipay_public_key, sign_type); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);//在公共参数中设置回跳和通知地址
        //alipayRequest.setBizContent("{" +
        //        " \"out_trade_no\":\"20150320010101002\"," +
        //        " \"total_amount\":\"88.88\"," +
        //        " \"subject\":\"Iphone6 16G\"," +
        //        " \"product_code\":\"QUICK_WAP_PAY\"" +
        //        " }");//填充业务参数
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        String timeout_express="2m";

        String product_code="QUICK_WAP_PAY";

        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipayRequest.setBizModel(model);

        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //        + "\"total_amount\":\""+ total_amount +"\","
        //        + "\"subject\":\""+ subject +"\","
        //        + "\"product_code\":\"QUICK_WAP_PAY\"}");
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println("支付宝的响应："+form);
            return form;
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单

        return form;
    }



}

