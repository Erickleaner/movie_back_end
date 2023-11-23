package cn.itcast.movie_back_end.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.HashMap;
import java.util.Map;

public class PhoneCodeVerify {
    private static final String accessKeyId = "LTAI5t7fHyAr8h4JWLxdBfoP";
    private static final String accessKeySecret = "vPMBaNqnWmztSwl1G1zYSLrh7F8VdO";

    //短信签名,即最前面在【】这个中的文字,可以打开手机随便看一条商业短信,例：【淘宝】亲爱的用户.....
    private static final String signName = "王一番的博客";

    // 短信模板,即在阿里云短信服务中自己创建的短信模板ID
    private static final String templateCode = "SMS_463920271";

    public static void setTestTemp(CommonRequest request){
        request.putQueryParameter("SignName", "阿里云短信测试");
        // 模板ID
        request.putQueryParameter("TemplateCode", "SMS_244065828");
    }

    public static void setCustomTemp(CommonRequest request){
        request.putQueryParameter("SignName", signName);
        // 模板ID
        request.putQueryParameter("TemplateCode", templateCode);
    }

    private String makeCode(){
        //0 - 9
        StringBuilder code = new StringBuilder();
        for (int i=0;i<4;i++){
            int num;
            if (i==0) num = (int) (Math.random()*9) + 1;
            else num = (int) (Math.random()*10);
            code.append(num);
        }
        return code.toString();
    }

    public String sendSms(String phone) {
        // 初始化acsClient,需要填写自己的accessKeyId和accessSecret
        // regionId只有一个杭州,不支持其他地区
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        // 组装请求对象,这几项为默认的设置,不必修改
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        // 阿里云服务器域名
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 短信接收者手机号
        request.putQueryParameter("PhoneNumbers", phone);
        /* 短信签名
        request.putQueryParameter("SignName", signName);
         模板ID
        request.putQueryParameter("TemplateCode", templateCode);*/
        //setTestTemp(request);
        setCustomTemp(request);
        // 构建短信模板参数替换,要求格式为json
        // 如果你的短信模板为：验证码为：${code},那么你的json为：{"code": "2345"}
        Map<String, String> jsonParam = new HashMap<>();
        //1~9
        //0-9
        String code = makeCode();
        jsonParam.put("code", code);

        //此处我用的json格式化的依赖是fastjson,SpringBoot自带的是jackson
        request.putQueryParameter("TemplateParam", JSON.toJSONString(jsonParam));
        // 发送请求并获取响应,判断是否成功
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (ClientException e) {
            return null;
        }
        // 网络通畅即可
        if (response.getHttpResponse().isSuccess()) {
            System.out.println(response.getData());
            return code;
        }
        return null;
    }
}
