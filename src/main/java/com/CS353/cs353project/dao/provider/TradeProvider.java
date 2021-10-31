package com.CS353.cs353project.dao.provider;

import com.CS353.cs353project.param.evt.Management.QueryAuditRecordsEvt;
import com.CS353.cs353project.param.evt.Trade.QueryCommoditiesEvt;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

public class TradeProvider {
    /**
     * 查询审核记录
     */
    public String queryAuditRecords(QueryAuditRecordsEvt evt) {
        SQL sql = new SQL() {
            {
                SELECT("bookName,bookDesc,bookPrice,newOldDegree,bookStock,createUser,bookPicUrl,\n" +
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
                WHERE("status ='E'");
                if ("0".equals(evt.getType())) {
                    WHERE("auditStatus='0'");
                } else if ("1".equals(evt.getType())) {
                    WHERE("auditStatus='1'");
                } else if ("2".equals(evt.getType())) {
                    WHERE("auditStatus='2'");
                }
            }
        };
        return sql.toString();
    }


    public String queryCommodities(QueryCommoditiesEvt evt) {
        SQL sql = new SQL() {
            {
                SELECT(" bookNo,bookName,userName,\n" +
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
                        "bookDesc,bookPrice,bookSale,bookStock,recommend,createTime,bookPicUrl,newOldDegree");
                FROM("t_commodity");
                WHERE("status='E' and auditStatus='1'");
                if (StringUtils.isNotBlank(evt.getBookName())) {
                    WHERE("bookName like '%'+#{evt.bookName}+'%'");
                }
                if(evt.getSortType()!=null){
                    if(evt.getSortType()==1){
                        ORDER_BY("createTime desc");
                    }else if(evt.getSortType()==2){
                        ORDER_BY("bookPrice");
                    }else if(evt.getSortType()==3){
                        ORDER_BY("bookPrice desc");
                    }else if(evt.getSortType()==4){
                        ORDER_BY("bookSale desc");
                    }
                }
            }
        };
        return sql.toString();
    }
}
