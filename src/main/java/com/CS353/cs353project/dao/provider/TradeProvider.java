package com.CS353.cs353project.dao.provider;

import org.apache.ibatis.jdbc.SQL;

public class TradeProvider {
    /**
     *查询审核记录
     */
    public String queryAuditRecords(String type){
        SQL sql = new SQL() {
            {
                SELECT("bookName,bookDesc,bookPrice,newOldDegree,bookStock,createUser,bookPicUrl,\n" +
                        "date_format(createTime, '%Y-%m-%d %H:%i:%S') createTime,\n" +
                        "case bookTag \n" +
                        "when '0' then '文艺'\n" +
                        "when '1' then '科幻' end,\n" +
                        "case auditStatus \n" +
                        "when '1' then '通过'\n" +
                        "when '0' then '审核中'\n" +
                        "else '审核不通过' end");
                FROM("t_commodity");
                WHERE("status ='E'");
                if ("0".equals(type)){
                    WHERE("auditStatus='0'");
                }else if("1".equals(type)){
                    WHERE("auditStatus='1'");
                }else if("2".equals(type)){
                    WHERE("auditStatus='2'");
                }
            }
        };
        return sql.toString();
    }
}
