package com.CS353.cs353project.dao.provider;

import com.CS353.cs353project.param.evt.Management.QueryAllUserEvt;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

public class ManagementProvider {
    public String queryAllUsers(QueryAllUserEvt evt) {
        SQL sql = new SQL() {
            {
                SELECT("*");
                FROM("t_user");
                WHERE("status='E' and userRoot=0");
                if (StringUtils.isNotBlank(evt.getUserEmail())) {
                    WHERE("userEmail = #{evt.userEmail}");
                }
                if (evt.getIsBan() != null) {
                    WHERE("isBan = #{evt.isBan}");
                }
                if (evt.getAuthentication() != null) {
                    WHERE("authentication = #{evt.authentication}");
                }
            }
        };
        return sql.toString();
    }
}
