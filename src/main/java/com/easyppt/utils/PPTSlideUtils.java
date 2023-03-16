package com.easyppt.utils;

import com.easyppt.annotation.Module;
import com.easyppt.annotation.Picture;
import com.easyppt.annotation.Text;
import com.easyppt.model.Locate;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.apache.commons.io.IOUtils.toByteArray;

public class PPTSlideUtils {
//    private static final Logger LOG = LoggerBuilder.getLogger(PPTSlideUtils.class);

    private PPTSlideUtils() {}

    public static void createModule(XSLFSlide slide, ArrayList properties, Module module) {
        if (properties == null) {
            return;
        }
        for (Object obj : properties) {
            XSLFAutoShape autoShape = slide.createAutoShape();//创建表格
            autoShape.setAnchor(anchor((Locate) obj));
            autoShape.setShapeType(module.value());
            autoShape.setLineColor(convertColor(module.color()));
        }
    }

    public static void createPicture(XSLFSlide slide, ArrayList properties, XSLFPictureData pictureData) {
        if (properties == null) {
            return;
        }
        for (Object obj : properties) {
            XSLFPictureShape picture = slide.createPicture(pictureData);
            picture.setAnchor(anchor((Locate) obj));
        }
    }

    public static void createText(XSLFSlide slide, ArrayList properties, Text text) {
        if (properties == null) {
            return;
        }
        for (Object obj : properties) {
            XSLFTextBox textBox = slide.createTextBox();
            // x y设置距离  w h 设置大小
            textBox.setAnchor(anchor((Locate) obj));
            XSLFTextParagraph textParagraph = textBox.getTextParagraphs().isEmpty()
                    ? textBox.addNewTextParagraph() : textBox.getTextParagraphs().get(0);
            textParagraph.setTextAlign(TextParagraph.TextAlign.CENTER);
            XSLFTextRun textRun = textParagraph.addNewTextRun();
            textRun.setFontSize(text.fontSize());
            textRun.setText(((Locate) obj).getText());
        }
    }

    public static XSLFPictureData addPicture(XMLSlideShow slideShow, Picture picture) {
        Resource classPathResource = new ClassPathResource(picture.value());
        byte[] workbook = null;
        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
            workbook = toByteArray(inputStream);
        } catch (IOException e) {
//            LOG.error("error when export excel template: {}", e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
//                    LOG.error("error when close excel template: {}", e.getMessage());
                }
            }
        }
        // 插入图片
        return slideShow.addPicture(workbook, PictureData.PictureType.PNG);
    }

    private static Color convertColor(String color) {
        if (color.length() != 9) {
//            LOG.debug("color parameter error,9 digits required");
        }
        Integer colorValue = Integer.valueOf(color);
        int bcolor = colorValue % 1000;
        int temp = colorValue / 1000;
        int gcolor = temp % 1000;
        int rcolor = temp / 1000;
        return new Color(rcolor,gcolor,bcolor);
    }

    public static Rectangle2D anchor(Locate locate) {
        return new Rectangle2D.Double(locate.getStartX(),
                locate.getStartY(), locate.getWidth(), locate.getHeight());
    }
}
