package com.CS353.cs353project.dao.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    /**
     * 查询上架商品信息（包括所有审核状态）
     */
    public String queryMyCommodity(@Param("createUser") String createUser){
        SQL sql=new SQL(){
            {
                SELECT("bookNo,bookName,bookDesc,bookPrice,newOldDegree,bookStock,bookPicUrl,\n" +
                        "date_format(createTime, '%Y-%m-%d %H:%i:%S') createTime,\n" +
                        "case bookTag \n" +
                        "when '0' then '文学'\n" +
                        "when '1' then '随笔'\n" +
                        "when '2' then '历史'\n" +
                        "when '3' then '科幻'\n" +
                        "when '4' then '奇幻'\n" +
                        "when '5' then '悬疑'\n" +
                        "when '6' then '推理'\n" +
                        "when '7' then '哲学'\n" +
                        "when '8' then '工具'\n" +
                        "when '9' then '专业知识' end,\n" +
                        "case auditStatus \n" +
                        "when '1' then '通过'\n" +
                        "when '0' then '审核中'\n" +
                        "else '审核不通过' end");
                FROM("t_commodity");
                WHERE("status ='E' and createUser= #{createUser}");
            }
        };
        return sql.toString();
    }
}
