package com.CS353.cs353project.dao.provider;

import com.CS353.cs353project.param.evt.Management.QueryAuditRecordsEvt;
import com.CS353.cs353project.param.evt.Trade.Order.QueryOrderEvt;
import com.CS353.cs353project.param.evt.Trade.QueryCommoditiesEvt;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;


public class TradeProvider {
    /**
     * 查询审核记录
     */
    public String queryAuditRecords(QueryAuditRecordsEvt evt) {
        SQL sql = new SQL() {
            {
                SELECT("bookNo,bookName,bookDesc,bookPrice,newOldDegree,bookStock,createUser,bookPicUrl,\n" +
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
                        "when '9' then '专业知识' end as bookTag,\n" +
                        "case auditStatus \n" +
                        "when '1' then '通过'\n" +
                        "when '0' then '审核中'\n" +
                        "else '审核不通过' end as auditStatus");
                FROM("t_commodity");
                WHERE("status ='E'");
                if (StringUtils.isNotBlank(evt.getType())) {
                    if ("0".equals(evt.getType())) {
                        WHERE("auditStatus='0'");
                    } else if ("1".equals(evt.getType())) {
                        WHERE("auditStatus='1'");
                    } else if ("2".equals(evt.getType())) {
                        WHERE("auditStatus='2'");
                    }
                }
                if (StringUtils.isNotBlank(evt.getAuditor())) {
                    WHERE("auditor=#{evt.auditor}");
                }
            }
        };
        return sql.toString();
    }

    /**
     * 查询商品
     */
    public String queryCommodities(@Param("evt") QueryCommoditiesEvt evt) {
        SQL sql = new SQL() {
            {
                SELECT(" bookNo,bookName,sellerName,sellerNo,\n" +
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
                        "when '9' then '专业知识' end as bookTag,\n" +
                        "bookDesc,bookPrice,bookSale,bookStock,recommend,createTime,newOldDegree");
                FROM("t_commodity");
                WHERE("auditStatus='1' and bookStock >0");
                if(evt.getOnShelvedStatus()!=null){
                    if(evt.getOnShelvedStatus()==0){
                        WHERE("status='E'");
                    }else if(evt.getOnShelvedStatus()==1){
                        WHERE("status='D'");
                    }
                }
                if(StringUtils.isNotBlank(evt.getBookNo())){
                    WHERE("bookNo=#{evt.bookNo}");
                }
                if (StringUtils.isNotBlank(evt.getSellerNo())) {
                    WHERE("sellerNo =#{evt.sellerNo}");
                }
                if (StringUtils.isNotBlank(evt.getBookName())) {
                    WHERE("bookName like CONCAT('%',#{evt.bookName},'%')");
                }
                if (StringUtils.isNotBlank(evt.getSellerName())) {
                    WHERE("sellerName like CONCAT('%',#{evt.sellerName},'%')");
                }
                if (evt.getSortType() != null) {
                    if (evt.getSortType().contains(1)) {//按时间最新排序
                        ORDER_BY("createTime desc");
                    }
                    if (evt.getSortType().contains(2)) {//按时间最久排序
                        ORDER_BY("createTime");
                    }
                    if (evt.getSortType().contains(3)) {//按价格低到高排序
                        ORDER_BY("bookPrice");
                    }
                    if (evt.getSortType().contains(4)) {//按价格高到低排序
                        ORDER_BY("bookPrice desc");
                    }
                    if (evt.getSortType().contains(5)) {//按图书销量最多排序
                        ORDER_BY("bookSale desc");
                    }
                    if (evt.getSortType().contains(6)) {//按图书销量最少排序
                        ORDER_BY("bookSale");
                    }
                }
            }
        };
        return sql.toString();
    }

    /**
     * 查询购物车
     */
    public String queryShoppingCart(String buyerNo) {
        SQL sql = new SQL() {
            {
                SELECT("*");
                FROM("t_shoppingCart");
                WHERE("status='E' and buyerNo=#{buyerNo}");
                ORDER_BY("sellerNo");
            }
        };
        return sql.toString();
    }

    /**
     * 查询订单
     */
    public String queryOrder(QueryOrderEvt evt) {
        SQL sql = new SQL() {
            {
                SELECT(" orderNo,bookNo,bookName,sellerNo,address,consignee,phone,num,price,deTimeFrom,deTimeTo,buyerDisplay,sellerDisplay,createTime,createUser,\n" +
                        "case orderStatus\n" +
                        "when 0 then '待发货'\n" +
                        "when 1 then '已发货'\n" +
                        "when 2 then '完成'\n" +
                        "when 3 then '申请取消'\n" +
                        "when 4 then '已取消'\n" +
                        "end as orderStatus,\n" +
                        "case\n" +
                        "when cancelOperatorRole=0 then '买家'\n" +
                        "when cancelOperatorRole=1 then '卖家'\n" +
                        "else '无'\n" +
                        " end as cancelOperatorRole,\n" +
                        "case \n" +
                        "when cancelOperator is null then '无'\n" +
                        "else cancelOperator \n" +
                        "end as cancelOperator,\n" +
                        "case \n" +
                        "when cancelReason is null then '无'\n" +
                        "else cancelReason\n" +
                        "end as cancelReason");
                FROM("t_order");
                WHERE("status='E'");
                if(evt.getOperatorRole()!=null){
                    if (evt.getOperatorRole() == 0) {//以买家身份查看
                        WHERE("buyerNo=#{evt.operatorNo}");
                        WHERE("buyerDisplay=0");
                    } else if(evt.getOperatorRole() == 1){//以卖家身份查看
                        WHERE("sellerNo=#{evt.operatorNo}");
                        WHERE("sellerDisplay=0");
                    }
                }
                if(evt.getOrderStatus()!=null){
                    if(evt.getOrderStatus()==0){
                        WHERE("orderStatus=0");
                    }else if(evt.getOrderStatus()==1) {
                        WHERE("orderStatus=1");
                    }else if(evt.getOrderStatus()==2){
                        WHERE("orderStatus=2");
                    }else if(evt.getOrderStatus()==3){
                        WHERE("orderStatus=3");
                    }else if(evt.getOrderStatus()==4){
                        WHERE("orderStatus=4");
                    }
                }
                if (evt.getSortType() != null) {
                    if (evt.getSortType().contains(0)) {//时间（最新在前）
                        ORDER_BY("createTime desc");
                    }
                    if (evt.getSortType().contains(1)) {//时间（最旧在前）
                        ORDER_BY("createTime");
                    }
                    if (evt.getSortType().contains(2)) {//金额（最多在前）
                        ORDER_BY("price desc");
                    }
                    if (evt.getSortType().contains(3)) {//金额（最少在前）
                        ORDER_BY("price");
                    }
                    if (evt.getSortType().contains(4)) {//购买数量（最多在前）
                        ORDER_BY("num desc");
                    }
                    if (evt.getSortType().contains(5)) {//购买数量（最少在前）
                        ORDER_BY("num");
                    }
                }
            }
        };
        return sql.toString();
    }

}
