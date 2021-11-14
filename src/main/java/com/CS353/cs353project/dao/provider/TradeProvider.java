package com.CS353.cs353project.dao.provider;

import com.CS353.cs353project.param.evt.Management.QueryAuditRecordsEvt;
import com.CS353.cs353project.param.evt.Trade.BuyerQueryOrderListEvt;
import com.CS353.cs353project.param.evt.Trade.QueryCommoditiesEvt;
import com.CS353.cs353project.param.evt.Trade.SellerQueryOrderListEvt;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                        "when '9' then '专业知识' end,\n" +
                        "case auditStatus \n" +
                        "when '1' then '通过'\n" +
                        "when '0' then '审核中'\n" +
                        "else '审核不通过' end");
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
            }
        };
        return sql.toString();
    }


    public String queryCommodities(QueryCommoditiesEvt evt) {
        List<Integer> typeList = new ArrayList<>();
        if (evt.getSortType() != null) {
            typeList = Arrays.asList(evt.getSortType());
        }
        List<Integer> finalTypeList = typeList;
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
                        "when '9' then '专业知识' end,\n" +
                        "bookDesc,bookPrice,bookSale,bookStock,recommend,createTime,bookPicUrl,newOldDegree");
                FROM("t_commodity");
                WHERE("status='E' and auditStatus='1' and bookStock >0");
                if (StringUtils.isNotBlank(evt.getBookName())) {
                    WHERE("bookName like CONCAT('%',#{evt.bookName},'%')");
                }
                if (StringUtils.isNotBlank(evt.getSellerName())) {
                    WHERE("sellerName like CONCAT('%',#{evt.sellerName},'%')");
                }
                if (evt.getSortType() != null) {
                    if (finalTypeList.contains(1)) {//按时间最新排序
                        ORDER_BY("createTime desc");
                    }
                    if (finalTypeList.contains(2)) {//按时间最久排序
                        ORDER_BY("createTime");
                    }
                    if (finalTypeList.contains(3)) {//按价格低到高排序
                        ORDER_BY("bookPrice");
                    }
                    if (finalTypeList.contains(4)) {//按价格高到低排序
                        ORDER_BY("bookPrice desc");
                    }
                    if (finalTypeList.contains(5)) {//按图书销量最多排序
                        ORDER_BY("bookSale desc");
                    }
                    if (finalTypeList.contains(6)) {//按图书销量最少排序
                        ORDER_BY("bookSale");
                    }
                }

            }
        };
        return sql.toString();
    }

    /**
     * 商家查看订单
     */
    public String sellerQueryOrderList(SellerQueryOrderListEvt evt) {
        List<Integer> typeList = new ArrayList<>();
        if (evt.getSortType() != null) {
            typeList = Arrays.asList(evt.getSortType());
        }
        List<Integer> finalTypeList = typeList;
        SQL sql = new SQL() {
            {
                SELECT("orderNo,bookNo,bookName,sellerNo,address,consignee,phone,num,price,deTimeFrom,deTimeTo,buyerDisplay,sellerDisplay,orderStatus,createTime,createUser");
                FROM("t_order");
                WHERE("sellerNo=#{evt.sellerNo} and status='E'");
                if (StringUtils.isNotBlank(evt.getBookName())) {
                    WHERE("bookName like CONCAT('%',#{evt.bookName},'%')");
                }
                if (StringUtils.isNotBlank(evt.getConsignee())) {
                    WHERE("consignee =#{evt.consignee}");
                }
                if (evt.getOrderStatus() != null) {
                    WHERE("orderStatus=#{evt.orderStatus}");
                }
                if (evt.getSortType() != null) {
                    if (finalTypeList.contains(0)) {//时间（最新在前）
                        ORDER_BY("createTime desc");
                    }
                    if (finalTypeList.contains(1)) {//时间（最旧在前）
                        ORDER_BY("createTime");
                    }
                    if (finalTypeList.contains(2)) {//金额（最多在前）
                        ORDER_BY("price desc");
                    }
                    if (finalTypeList.contains(3)) {//金额（最少在前）
                        ORDER_BY("price");
                    }
                    if (finalTypeList.contains(4)) {//购买数量（最多在前）
                        ORDER_BY("num desc");
                    }
                    if (finalTypeList.contains(5)) {//购买数量（最少在前）
                        ORDER_BY("num");
                    }
                }
            }
        };
        return sql.toString();
    }

    /**
     * 买家查看订单
     */
    public String buyerQueryOrderList(BuyerQueryOrderListEvt evt) {
        List<Integer> typeList = new ArrayList<>();
        if (evt.getSortType() != null) {
            typeList = Arrays.asList(evt.getSortType());
        }
        List<Integer> finalTypeList = typeList;
        SQL sql = new SQL() {
            {
                SELECT("orderNo,bookNo,bookName,sellerNo,address,consignee,phone,num,price,deTimeFrom,deTimeTo,buyerDisplay,sellerDisplay,orderStatus,createTime,createUser");
                FROM("t_order");
                WHERE("buyerNo=#{evt.buyerNo} and status='E'");
                if (StringUtils.isNotBlank(evt.getBookName())) {
                    WHERE("bookName like CONCAT('%',#{evt.bookName},'%')");
                }
                if (StringUtils.isNotBlank(evt.getConsignee())) {
                    WHERE("consignee =#{evt.consignee}");
                }
                if (evt.getOrderStatus() != null) {
                    WHERE("orderStatus=#{evt.orderStatus}");
                }
                if (evt.getSortType() != null) {
                    if (finalTypeList.contains(0)) {//时间（最新在前）
                        ORDER_BY("createTime desc");
                    }
                    if (finalTypeList.contains(1)) {//时间（最旧在前）
                        ORDER_BY("createTime");
                    }
                    if (finalTypeList.contains(2)) {//金额（最多在前）
                        ORDER_BY("price desc");
                    }
                    if (finalTypeList.contains(3)) {//金额（最少在前）
                        ORDER_BY("price");
                    }
                    if (finalTypeList.contains(4)) {//购买数量（最多在前）
                        ORDER_BY("num desc");
                    }
                    if (finalTypeList.contains(5)) {//购买数量（最少在前）
                        ORDER_BY("num");
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

}
