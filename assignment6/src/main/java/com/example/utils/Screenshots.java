package com.example.utils;

import org.openqa.selenium.*;
import java.io.File;

public class Screenshots {
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = "screenshots/" + screenshotName + ".png";
            File destFile = new File(filePath);
            srcFile.renameTo(destFile);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
