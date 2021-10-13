package com.CS353.cs353project.dao.mapper;

import com.CS353.cs353project.bean.UserBean;
import com.CS353.cs353project.param.evt.UserLoginEvt;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserBean> {

    /**
     * 查询用户
     * 通过邮箱查询
     */
    @Select("select * from t_user where userEmail = #{userEmail} and status = 'E'")
    UserBean queryUserByEmail(@Param("userEmail") String userEmail);

    /**
     * 判断密码是否正确
     */
    @Select("select * from t_user where userEmail = #{evt.userEmail} and status = 'E' and userPassword=#{evt.userPassword}")
    UserBean judgeUserPassword(@Param("evt") UserLoginEvt evt);

    /**
     * 查询用户
     * 通过用户编码查询
     */
    @Select("select * from t_user where userNo = #{userNo} and status = 'E'")
    UserBean queryUserByNo(@Param("userNo") String userNo);

}
