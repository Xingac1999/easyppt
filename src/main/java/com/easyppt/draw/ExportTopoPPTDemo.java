package com.easyppt.draw;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.springframework.stereotype.Service;

@Service
public class ExportTopoPPTDemo {
    //只生成node图片
    private static Double MIN_Longitude;
    private static Double MIN_Latitude;
    private static Double Scare_Rate;
    private static Double Node_Length = 40D;
    private static Double Border = 150D;
    private static Integer PPT_LENGTH = 2000;
    private static Integer PPT_WIDTH = 2000;
    private static Double Font_Size = 20D;

    public XMLSlideShow topoExport(Long networkId) {
        XMLSlideShow ppt  = null;
        return ppt;
    }
}
