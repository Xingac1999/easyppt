package com.easyppt.utils;

import com.easyppt.annotation.Module;
import com.easyppt.annotation.Picture;
import com.easyppt.annotation.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class ClassUtils {

    private ClassUtils() {}

    public static void declaredFields(Class<?> clazz,Map<String,Field> moduleFieldMap,Map<String,Field> pictureFieldMap,
                                      Map<String,Field> textFieldMap) {
        Class<?> tempClass = clazz;
        ArrayList<Field> fieldList = new ArrayList<>();
        while (tempClass != null) {
            Collections.addAll(fieldList,tempClass.getDeclaredFields());
            tempClass = tempClass.getSuperclass();
        }
        for (Field field : fieldList) {
            declaredOneFields(field,moduleFieldMap,pictureFieldMap,textFieldMap);
        }
    }

    private static void declaredOneFields(Field field,Map<String,Field> moduleFieldMap,
                                          Map<String,Field> pictureFieldMap, Map<String,Field> textFieldMap) {
        Module module = field.getAnnotation(Module.class);
        if (module != null) {
            moduleFieldMap.put(field.getName(),field);
        }
        Picture picture = field.getAnnotation(Picture.class);
        if (picture != null) {
            pictureFieldMap.put(field.getName(),field);
        }
        Text text = field.getAnnotation(Text.class);
        if (text != null) {
            textFieldMap.put(field.getName(),field);
        }
    }


}
