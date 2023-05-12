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
    public String app_id = "2021000121661943";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDB/Nao/JMTNlNqUyroXHstuvh0xZLNLJVXGKi1XGUAvryF+9H4ZryUxKWnftQlYLeMtxbTQY6f9c1PbFuX66gh8KVJ6/cHOdFPjDm5wsZin1W4uCll5+GWMT6sb5jirIwyVzkTsRyq7fAlyvIMPSK950+hAYOt2zXSrlWS4k6Ib4IOdp/TPCz4Zv/6dNy+xwzvs107jmXUmCIRoItjp801+IlSex1yn0NtxeuhHXWqt0XQoLN3wEWjF5/OJCZW75e7A17uWe1Ml5GoEH0oRnC5YPPwGxkSdon2c9vBJRzKXaaGX4bjIVpQmltklxm6eZ2oGIvQ3QjY+EEe8POWhrPjAgMBAAECggEALt8DkuMsOjknIIql1Gz8Ckmh3L0cQlziXKAFq4R+lxZ/ALSewDE6oYt+JyFa6wkacHKQnky5xGaky+r3mpaDEmhN17WfA2zx6RGNpRetzYiIMtC0l/3WIO8XIJz6SNK+MS+oB90EezxwiQEnSNPAwDfSoql2ApzzvUsIPCxYI1i56C9AgxKUCMWiFTVwSI59LiVe6+homY+WptKuQLsgUCQeoeeUvOVJB7Dk6ZDYUZhOauEGGHvVddtLzg6lmDl1q1RW/jLLMYnvp2g2j8p2iQzvjWFfEidLkvOpev1bAhyCZBKOza102DAb18s3kb+YBPEgkHYe3zQPi+8+vNNuqQKBgQDz4HSNhKvvm38GFiTjSTAA5oQ90Wet2L3RcPwWWYVWgik33UsJtszAbWZpBD8szbH/qPBSqHIYoftHdjvq2E41Bb8q8tbgTBL6HYDGvSxQAyJKsMO4LGWSGy5OWFWPJE5/FpQDr3H5bH2DjTmQqcZHaP2XrSTHQhjfitK4mWKmdwKBgQDLoYAk/YjNIL0vZ/+BNhtkXE3vdaw7PexSPM67X9QJyGpYMuq+agjVRjbsB0w4Xb7/Qs9Byzdhslm0CQnhh2h22u9KTM03pPrZMFImdCOMNlec3/FX0hgu1eaImOzxxxyc4OXuUZr//lX5zyKy579ihkj0umoyqYeyaYnPH5K89QKBgFo0rsfdx/8TxBNLQ4+ydYgPu+BlwXTDU8rRgNz/ddt+v6JDP0szi16t2JCFmpyG5Q0Mvv/EAJft6lUPfbArHBpuqrxHIIXMfcR3/zcDqA1ca2W3Jl3MRjGZzjH6aINOyZ4mis8mKQE6SN7M8Pr6YTXutsZQq+bsIukknzbavWuXAoGBAJ7sMGKuLLJLNttkwxhI7h/pmjmlEQI7VdDo/D9ojsgDzlt/gY2Sm8o3vm1/VJE5OPNnZPHodBSfaTsFQtztHS6Ft/O49QDUlaIkADlastOfJcmdTXJI2e7TqdQ55dLcnwwgydKxhdL84ITSkDYogEmYnB/DzVY00V8A1zj7Xz5xAoGBAMwAez2kEiN3Tm3caPO52tS/m8593igeGKZlx2JjPQXw0PA2HxMZM/+LMF5Ez4pJ1tgP4FboU7zoGavYGAHvwKQ1l9dORJ1Q8HDCfs9iRjZsT+dKQyXPCw1c6ME3IBW85IjV2+Kz5Hh6bzFZ7PUuxsXM40hEYMu8JdaDwYCk/LW6";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw8TR4S89QFgUmcbrNoZ9qQxnNh++OaT66IisJGPud2A5nEZnClQFH0hvczD0kqL8Xc3Fgkiy/2Mkxm1TOqjQ6sZCAnFNM8wlgFBxf7dZFFhSIRWAjVSomG0wXkEcKWuOeG+mxBZYT/ood10+F+5390HOys0gRFsNkQofI+n6tUJRKtzvge6WEF8/W3DOMR31vyr0DclODJexbQgPKPhL1toHMdg4pd5TQTUhT0BiBWylB7+lyk5Vv312fyK6U2Vp1K9+WRmGis6aqpk5rdYc+6AftZADe4Y3QOEr9ue4SAjEA2NilwIYcGmOual+I4vE0qkWX86Mhh5cdvV6KlcOWQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    public String notify_url = "http://syfmgj.natappfree.cc/paySuccess";


    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    public String return_url = "http://localhost:8080/front/page/pay-success.html";

    // 签名方式
    public String sign_type = "RSA2";

    // 字符编码格式
    public String charset = "utf-8";


    public  String FORMAT ="JSON";

    //订单超时时间
    private String timeout = "2h";

    //// 支付宝网关； https://openapi.alipaydev.com/gateway.do
    //public String gatewayUrl;
    // 支付宝网关（沙箱环境）
    public  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";


    public String pay1(PayVo vo) throws AlipayApiException, IOException {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, app_id,merchant_private_key, FORMAT , charset, alipay_public_key, sign_type); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);//在公共参数中设置回跳和通知地址

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
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
            System.out.println("支付宝的响应："+form);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
     return form;
    }



}

