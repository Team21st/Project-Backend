package com.CS353.cs353project.service;

import com.CS353.cs353project.bean.UserBean;
import com.CS353.cs353project.dao.mapper.UserMapper;
import com.CS353.cs353project.param.evt.*;
import com.CS353.cs353project.param.model.AliyunOssResultModel;
import com.CS353.cs353project.param.model.QueryUserPrivateInfoModel;
import com.CS353.cs353project.param.model.UserLoginModel;
import com.CS353.cs353project.param.out.ServiceResp;
import com.CS353.cs353project.utils.AliyunOSSUtil;
import com.CS353.cs353project.utils.JwtUtils;
import com.CS353.cs353project.utils.Md5Util;
import com.CS353.cs353project.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;
    @Autowired
    private VerifyCodeUtils verifyCodeUtils;

    /**
     * 注册
     */
    public ServiceResp userRegister(UserRegisterEvt evt) {
        // 检验用户是否存在
        UserBean userBean = userMapper.queryUserByEmail(evt.getUserEmail());
        if (userBean != null) {
            return new ServiceResp().error("User already exists");
        }
        //校验验证码(redis验证其中是否存在)
        String emailKey = "UserEmail_" + evt.getUserEmail();
        String resp = verifyCodeUtils.verify(emailKey, evt.getCode());
        if ("1".equals(resp)) {//验证码过期
            return new ServiceResp().error("Verification code doesn't exist or expire");
        }
        if ("3".equals(resp)) {//验证码错误
            return new ServiceResp().error("Verification code wrong");
        }
        // 将用户数据保存至数据库
        UserBean addBean = new UserBean();
        addBean.setUserNo(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        addBean.setUserName("user_"+evt.getUserEmail());
        addBean.setUserEmail(evt.getUserEmail());
        addBean.setUserPassword(Md5Util.MD5(evt.getUserPassword()));
        addBean.setStatus("E");
        addBean.setUserRoot(0);
        addBean.setAuthentication(2);
        addBean.setIsBan(0);
        addBean.setUnquaComm(0);
        addBean.setCreateUser(evt.getUserEmail());
        int info = userMapper.insert(addBean);
        if (info == 1) {
            logger.info(String.format("User %s registration success", evt.getUserEmail()));
            return new ServiceResp().success("Registration success");
        }
        return new ServiceResp().error("Registration failed");
    }


    /**
     * 登录
     */
    public ServiceResp userLogin(UserLoginEvt evt) {
        // 查询用户是否存在或被删除
        UserBean userBean = userMapper.queryUserByEmail(evt.getUserEmail());
        if (userBean == null) {
            return new ServiceResp().error("User doesn't exist");
        }
        //判断密码是否正确
        evt.setUserPassword(Md5Util.MD5(evt.getUserPassword()));
        if (!userBean.getUserPassword().equals(evt.getUserPassword())) {
            return new ServiceResp().error("password wrong");
        }
        //生成令牌
        String token = JwtUtils.createToken(userBean);
        //更新用户最后一次登录时间
        userBean.setLastLoginTime(new Date());
        int info = userMapper.updateById(userBean);
        if (info != 1) {
            logger.error(String.format("User %s failed to update the last login time", evt.getUserEmail()));
        }
        //返回用户信息与令牌
        UserLoginModel userLoginModel = new UserLoginModel();
        userBean.setUserPassword(null);//让用户密码不可见
        userLoginModel.setToken(token);
        userLoginModel.setUserBean(userBean);
        logger.info(String.format("User %s login success", evt.getUserEmail()));
        return new ServiceResp().success(userLoginModel);
    }

    /**
     * 修改密码(在已经登录的情况下)
     */
    public ServiceResp changeUserPassword(HttpServletRequest request, ChangeUserPasswordEvt evt) {
        // 取出用户数据
        UserBean userInfo = userMapper.queryUserByNo((String) request.getAttribute("userNo"));
        // 校验旧密码是否正确
        if (!Md5Util.MD5(evt.getOldPassword()).equals(userInfo.getUserPassword())) {
            return new ServiceResp().error("Wrong password");
        }
        //更新密码
        userInfo.setUserPassword(Md5Util.MD5(evt.getNewPassword()));
        int info = userMapper.updateById(userInfo);
        if (info == 1) {
            return new ServiceResp().success("Password modify success");
        }
        return new ServiceResp().error("Password modify failed");
    }

    /**
     * 修改密码(在忘记密码的情况下)
     */
    public ServiceResp forgetUserPassword(ForgetUserPasswordEvt evt) {
        // 取出用户数据
        UserBean userInfo = userMapper.queryUserByEmail(evt.getUserEmail());
        //校验验证码(redis验证其中是否存在)
        String emailKey = "UserEmail_" + evt.getUserEmail();
        String resp = verifyCodeUtils.verify(emailKey, evt.getCode());
        if ("1".equals(resp)) {//验证码过期
            return new ServiceResp().error("Verification code doesn't exist or expire");
        }
        if ("3".equals(resp)) {//验证码错误
            return new ServiceResp().error("Verification code wrong");
        }
        //更新密码
        userInfo.setUserPassword(Md5Util.MD5(evt.getNewPassword()));
        int info = userMapper.updateById(userInfo);
        if (info == 1) {
            return new ServiceResp().success("Password modify success");
        }
        return new ServiceResp().error("Password modify failed");
    }

    /**
     * 上传头像
     */
    public ServiceResp uploadHeadPortrait(HttpServletRequest request, MultipartFile file) {
        String userEmail = (String) request.getAttribute("userEmail");
        //userEmail = "1446795616@qq.com";//@passToken 情况下使用
        String ossUrl = "SHBM/HeadPortrait/"+userEmail+"/"+userEmail+"_HeadPortrait";
        AliyunOssResultModel resultModel= AliyunOSSUtil.uploadFile(file,ossUrl);
        if(resultModel.isSuccess()){
            //将用户头像写入数据库
            // 取出用户数据
            UserBean userInfo = userMapper.queryUserByNo((String) request.getAttribute("userNo"));
            //更新头像
            String url = resultModel.getUrl();
            userInfo.setProfileUrl(url);
            int info = userMapper.updateById(userInfo);
            if(info!=1){
                return new ServiceResp().error("update head portrait failed");
            }
            return new ServiceResp().success(resultModel);
        }else {
            return new ServiceResp().error(resultModel.getMsg());
        }
    }

    /**
     *获取用户个人信息
     */
    public ServiceResp queryUserPrivateInfo(HttpServletRequest request){
        // 取出用户数据
        UserBean userInfo = userMapper.queryUserByNo((String) request.getAttribute("userNo"));
        QueryUserPrivateInfoModel queryUserPrivateInfoModel=new QueryUserPrivateInfoModel();
        BeanUtils.copyProperties(queryUserPrivateInfoModel,userInfo);
        return new ServiceResp().success(queryUserPrivateInfoModel);
    }

    /**
     *修改用户个人信息（除了头像）
     */
    public ServiceResp updateUserPrivateInfo(HttpServletRequest request, UpdateUserPrivateInfoEvt evt){
        // 取出用户数据
        UserBean userInfo = userMapper.queryUserByNo((String) request.getAttribute("userNo"));
        BeanUtils.copyProperties(userInfo,evt);
        int result = userMapper.updateById(userInfo);
        if(result!=1){
            return new ServiceResp().error("update user private information failed");
        }
        return new ServiceResp().success("update user private information success");
    }
}
