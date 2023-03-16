package com.easyppt.annotation;

import org.apache.poi.sl.usermodel.ShapeType;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Module {
    /**
     * 组件形状
     */
    ShapeType value() default ShapeType.LINE;

    /**
     * 是否使用组件名称
     * @see com.easyppt.model.Locate#getText()
     * 会在组件正下方生成文本框放置文本
     */
    boolean useText() default false;

    /**
     * 组件线条颜色
     * @see Color#black
     * @see Color#white
     * @return
     */
    String color() default "255255255";
}
