package com.CS353.cs353project.param.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum sortType {
    //1(按时间最新排序)
    latestTime(1, "latestTime"),
    //2（按时间最久排序）
    earliestTime(2,"earliestTime"),
    //3（按价格低到高排序）
    lowestPrice(3,"lowestPrice"),
    //4（按价格高到低排序）
    highestPrice(4,"highestPrice"),
    //5（按图书销量最多排序）
    maximumSales(5,"maximumSales"),
    //6（按图书销量最少排序）
    minimumSales(6,"minimumSales");

    private Integer code;

    private String disc;

    public static String getJsonValue() {
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        object.put("type", "select");
        object.put("required", "true");
        for (sortType c : sortType.values()) {
            JSONObject item = new JSONObject();
            item.put("key", c.name());
            item.put("value", c.code);
            jsonArray.add(item);
        }
        object.put("list", jsonArray);
        return object.toJSONString();
    }

    // 普通方法
    public static Integer getName(Integer code) {
        for (sortType c : sortType.values()) {
            if (c.name().equals(code)) {
                return c.code;
            }
        }
        return null;
    }

    public static String getCode(String disc) {
        for (sortType c : sortType.values()) {
            if (c.disc.equals(disc)) {
                return c.name();
            }
        }
        return null;
    }

    sortType(int code, String disc) {
        this.code = code;
        this.disc = disc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
