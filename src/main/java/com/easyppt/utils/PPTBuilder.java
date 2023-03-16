package com.easyppt.utils;

import com.easyppt.annotation.Module;
import com.easyppt.annotation.Picture;
import com.easyppt.annotation.Text;
import com.easyppt.model.PPTWorkBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.slf4j.Logger;
import org.springframework.cglib.beans.BeanMap;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PPTBuilder {
//    private static final Logger LOG = LoggerBuilder.getLogger(PPTBuilder.class);


    private PPTWorkBook pptWorkBook;

    public PPTBuilder() {
        pptWorkBook = new PPTWorkBook();
    }

    protected PPTBuilder file(File outputFile) {
        pptWorkBook.setFile(outputFile);
        return this;
    }

    public PPTBuilder size(int length,int width) {
        pptWorkBook.setLength(length);
        pptWorkBook.setWidth(width);
        return this;
    }

    public void setProperty(Class<?> clazz) {
        pptWorkBook.setClazz(clazz);
    }

    public <T> PPTBuilder doDraw(T data) {
        //读注解
        //各属性的配置
        HashMap<String, Field> modelueFiledMap = new HashMap<>();
        HashMap<String, Field> pictureFiledMap = new HashMap<>();
        HashMap<String, Field> textFiledMap = new HashMap<>();
        ClassUtils.declaredFields(data.getClass(),modelueFiledMap,pictureFiledMap,textFiledMap);
        FieldCache fieldCache = new FieldCache(modelueFiledMap, pictureFiledMap, textFiledMap);
        //读对象值
        BeanMap.Generator generator = new BeanMap.Generator();
        generator.setBean(data);
        generator.setContextClass(data.getClass());
        HashMap<Object, ArrayList> beanMap = new HashMap<>(generator.create());
        //根据注解生成图形
        XMLSlideShow xmlSlideShow = drawPPT(fieldCache, beanMap);
        pptWorkBook.setSlideShow(xmlSlideShow);
        return this;
    }

    public XMLSlideShow getPPT() {
        return pptWorkBook.getSlideShow();
    }

    public XMLSlideShow toFile() {
        //创建ppt
        if (pptWorkBook.getFile() != null) {
            try {
                pptWorkBook.getSlideShow().write(new FileOutputStream(pptWorkBook.getFile()));
            } catch (IOException exp) {
//                LOG.error(exp.getMessage());
            }
        }
        return pptWorkBook.getSlideShow();
    }

    /**
     * 根据注解与属性关系生成ppt
     * @param fieldCache 各属性名与属性值对应
     * @param beanMap 参数详情
     * @return
     */
    private XMLSlideShow drawPPT(FieldCache fieldCache,HashMap<Object, ArrayList> beanMap) {
        XMLSlideShow xmlSlideShow = new XMLSlideShow();
        if (pptWorkBook.getLength() != 0 && pptWorkBook.getWidth() != 0) {
            xmlSlideShow.setPageSize(new Dimension(pptWorkBook.getLength(),pptWorkBook.getWidth()));

        }
        XSLFSlide slide = xmlSlideShow.createSlide();
        //处理形状
        for (Map.Entry<String, Field> fieldEntry : fieldCache.getModeleFieldMap().entrySet()) {
            String fileName = fieldEntry.getKey();
            ArrayList properties = beanMap.get(fileName);
            Field module = fieldEntry.getValue();
            Module annotation = module.getAnnotation(Module.class);
            PPTSlideUtils.createModule(slide,properties,annotation);
        }
        //处理图片
        for (Map.Entry<String, Field> fieldEntry : fieldCache.getPictureFieldMap().entrySet()) {
            Field picture = fieldEntry.getValue();
            Picture annotation = picture.getAnnotation(Picture.class);
            XSLFPictureData pictureData = PPTSlideUtils.addPicture(xmlSlideShow, annotation);
            String fileName = fieldEntry.getKey();
            ArrayList properties = beanMap.get(fileName);
            PPTSlideUtils.createPicture(slide,properties,pictureData);

        }
        //处理文本
        for (Map.Entry<String, Field> fieldEntry : fieldCache.getTextFieldMap().entrySet()) {
            Field text = fieldEntry.getValue();
            Text annotation = text.getAnnotation(Text.class);
            String fileName = fieldEntry.getKey();
            ArrayList properties = beanMap.get(fileName);
            PPTSlideUtils.createText(slide,properties,annotation);
        }
        return xmlSlideShow;
    }



    /**
     * 存储属性与注解对应关系
     * PPT固定模型,只有全参构造函数
     */
    @Getter
    @AllArgsConstructor
    private static class FieldCache {
        private final Map<String,Field> modeleFieldMap;
        private final Map<String,Field> pictureFieldMap;
        private final Map<String,Field> textFieldMap;

//        public FieldCache(Map<String,Field> modeleFieldMap,Map<String,Field> pictureFieldMap,
//                          Map<String,Field> textFieldMap) {
//            this.modeleFieldMap = modeleFieldMap;
//            this.pictureFieldMap = pictureFieldMap;
//            this.textFieldMap = textFieldMap;
//        }
    }

}
