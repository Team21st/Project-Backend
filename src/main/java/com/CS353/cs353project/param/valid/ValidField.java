package com.CS353.cs353project.param.valid;


import java.lang.annotation.*;

/**
 * @author wanggl
 * @version V1.0
 * @Title: ValidField
 * @Package com.doone.tool.common.valid
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/3/5 12:33
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidField {

    /**
     * 字段描述
     * @return String
     */
    String value() default "";

    /**
     * 限定长度
     * @return 限制值
     */
    int length() default -1;

    /**
     * 是否允许为空，默认是不允许
     * @return true or false
     */
    boolean nullable() default false;

    /**
     * 字段示例
     *
     * @return String
     */
    String example() default "";

    /**
     * 默认值
     * 可设置基本类型、String和日期类型（日期类型格式：yyyy-MM-dd HH:mm:ss）
     * @return String
     */
    String def() default "";

    /**
     * 字段校验的正则表达示
     * @return String
     */
    String regex() default "";

    /**
     * 使用预定义的模式表达示校验
     * regex和pattern，任先一，两都都填，则只使用regex
     *
     * @return Pattern
     */
    Pattern pattern() default Pattern.NULL;

    /**
     * 验证失败返回的消息
     *
     * @return String
     */
    String message() default "";

    /**
     * 超出长度是否裁剪
     *
     * @return true 是 false 不是
     */
    boolean cut() default false;

}
