package com.lywtakeout.takeout.config;

/**
 * 黄洋林: Huang
 * 2023/4/19 18:40
 */


import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号（使用沙箱环境的APPID）
    public static String app_id = "2021000122677931";

    // 商户私钥（应用私钥），您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCIZ6d5JSn6svlizK6EmcbClKiKPOuo/eRB2UnjKHK7gtRR9M8+hb4Pw6ti0C5qY/LiS/Jg7NqxiSldsBCaII+S8IEHP+KrctoIjh/irNGDkQ0uFtcacNZRfVj4nAZO0m7/onRCR5I+vXenAXA+sRCYUllr9/sH7ywhn67+EsCrYEPFuK++TZ8SGbKcokPbLCi4tK7/qrC5Xq8GjrKsSAUc2mLKhy0mpqC3ZgncPlPuPKpxLLjtsVGadjAfk5gzwb7RFgQnkisfiIQOZJhecwur22R7Tk9Kz6xqMU3DqXuMUBdgRIjXWcM2PjxVc0aBkpNy5ficnclTsr+3Ruyjmh6LAgMBAAECggEAPyXdflQaLxmUIo0nbfPS9Fnl/LlfUAjaFzIE5sje9LgImzh+iS9ZYerv+PrvwgkrdGA8gWqlg/M/10OfK/tO/HOjCDwnL8An+DXzxPjEtef89g1U38TFM0yYV9/De9In2OjYcaa4qZsFPaeI0z0dtabKBxXyfSXYhGhslusPj0Z7eCWtOVwUnhmWJLOdCWiUW8TSHMSs9AqOLZUpLWxzzWPAKg5cixnxhDYN7+8m0n5MyXhuM3j4/khLydR3/LALd53vs9RCQEJ0L3gsesvZY8Dc5PPuNDV12aTETNtP0+DJO2tF+HiKlPVdPxR84jzTwhGcwVS7uHstBPgXpvECoQKBgQDuIZ9rrld1bjXk84gNc2amQUzUf89FwesiurQ03QNwawyLVi667sWyf1urIulbXu+5mF499rFb+weT588tK3g6ycvPn2ULWWy8vtwCBCtihAIzpFNPWhYBK1GLTxlBqkkIeCnpGXYX6x7d2979FVcfmboMp962BvgH4mw6HmulHQKBgQCSo+uW5R7qvneHVpm+Ae6AgYVentm/4Kx3idNYw1+0xqp1KCYIxLMowicKQzqFUhEslnnWBn+Z5PKo4+TwRVeWqWq/Kllr4KoaHnTx3B3XiFtUGBQvW+u0OxsLG25EWj4p+Rwy1HsvPxuSAjjBP7v1dfdhsBWrzzgoQRVzyR3JxwKBgB+yA/keLG/p6mx96ab4rDoGyQYjuN/tDrIBrci3fiSxausE3pVtAI1gnD/sFqlduoS2fhy7QCIhCIIsQYwelBMnFuScr6NSFMIOmVP/YBLtxSlAfwI52GHxdRoYviDaXHwvGkz3YZuPZP1crZJ8C6ueF4Blu6P4y3ypK4SU1fkVAoGAX2BioRiRTVlo4QwAn0ceW1Li7jIfqt91DaMFZdxdaXU19VGRu25EoSP2xIQJkiFYrkmJIR3E7NGuyw6/H0/aslGq7n+a/cF4odhhc9YZStCTplSq/EqrOeSfqE/FHowblq1CHAV7wbUoN4VeaMMNe4+owU8IKWF0Qyphy5dp2o0CgYEAmJBZn/+M9pncoG/z4YTIGCt3iIk9yD6Euqu+sK2sJORRmm1JWRowGDLNRz/UVXJqQ4BPBbxbydZFT+V5ljzFIZ6O/UTXraExDY8rnKeKz0WQWzK/ny9ZwQkJ4+IdwF47cTItjuQ30eUN3eT3pLD7uPl94nY77lysMtYNenLL4Fw=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjIs28db5EEXvBZB6X5qilq7z7vZ8FDjCHXv4AMuFmEyW7YNKpNpMptAAtuoRiC+Ch5QaYnQU2Rkv+H5BhSN4YkD8pQ97cZaXH/cZW9hI9ZBELcbbEQhg835GkNSNltoAfO+XcQy5Rqvf3ChIYf/sL1aGFxL5MoY6rl3/L9qkiaWlreHoBfacyo2eoe3H/B83BGPJ6JwBKopTjxC8Kb3/QK2vu0widB5FWsP8TV0g2EuLOJrVtNF0Ky8Ti5k7OaUNxPLDG8utXx5K4EgyjD5HIPM932cRsRQz/44j6gmr8ZhSTrvRXLfYXJ8lwqq2BjtpQQv2ijJ6H7BhdlD8OmDkawIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 工程公网访问地址使用内网穿透客户端提供的域名
    public static String notify_url = "http://krgsvj.natappfree.cc/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080//front/page/pay-success.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关（正式环境）
    // public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

    // 支付宝网关（沙箱环境）
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 日志路径
    public static String log_path = "E:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
