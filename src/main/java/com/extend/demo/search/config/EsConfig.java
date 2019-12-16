package com.extend.demo.search.config;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.context.annotation.Configuration;

/**
 * @author snh
 * @version 1.0
 * @className EsConfig
 * @description TODO
 * @date 2019/11/22 14:34
 **/
@Configuration
public class EsConfig {


    public static void main(String[] args) throws TesseractException {

        /*File imageFile = new File("E:\\python\\timg.jpg");
        ITesseract install = new Tesseract();
        install.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        String result = install.doOCR(imageFile);
        result = result.replaceAll("\r|\n" , "");
        System.out.println(result);*/

        Integer integer1=3;
        Integer integer2=3;
        if (integer1==integer2)
            System.out.println("integer1==integer2");
        else
            System.out.println("integer1!=integer2");

        Integer integer3=300;
        Integer integer4=300;
        if (integer3==integer4)
            System.out.println("integer3==integer4");
        else
            System.out.println("integer3!=integer4");
    }

}
