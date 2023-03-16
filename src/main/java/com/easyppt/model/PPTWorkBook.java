package com.easyppt.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode
public class PPTWorkBook<T> {

    private File file;

    private Class<T> clazz;

    private int length;

    private int width;

    private XMLSlideShow slideShow;
}
