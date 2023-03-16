package com.easyppt.utils;

import java.io.File;

public class PPTUtil {

    private PPTUtil() {}

    public static PPTBuilder create(String pathName, Class<?> property) {
        PPTBuilder pptBuilder = new PPTBuilder();
        File file = new File(pathName);
        pptBuilder.file(file);
        if (property != null) {
            pptBuilder.setProperty(property);
        }
        return pptBuilder;
    }

}
