package com.CS353.cs353project.utils;


import com.CS353.cs353project.param.out.ServiceResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class VerifyCodeUtils {
    @Autowired
    private RedisTemplate redisTemplate;

    //生成四位数字验证码
    public String generateCode() {
        String number = "1234567890";
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(number.length());
            char c = number.charAt(index);
            result += c;
        }
        return result;
    }

    //生成验证码，并写入redis
    public ServiceResp sendCode(String email) {
        String mailStr = "UserEmail_" + email;
        if (checkCode(mailStr)) {
            String errorMsg = "2分钟内只能向邮箱:" + email + "发送一次验证码";
            return new ServiceResp().error(errorMsg);
        } else {
            String code = generateCode();
            redisTemplate.opsForValue().set(mailStr, code, 2, TimeUnit.MINUTES);
            return new ServiceResp().success(code);
        }
    }

    //检查对应邮箱是否存在验证码
    public boolean checkCode(String email) {
        return redisTemplate.hasKey(email);
    }

    public String verify(String emailKey, String code) {
        String mailStr = emailKey;
        Boolean flag =checkCode(mailStr);
        if(!flag){
            return "1";//验证码过期或不存在
        }
        String trueCode = (String) redisTemplate.opsForValue().get(emailKey);
        if (code.equals(trueCode)) {
            return "2";//验证码通过
        } else {
            return "3";//验证码错误
        }
    }
}