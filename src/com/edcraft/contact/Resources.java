package com.edcraft.contact;
import java.awt.Image;
import java.awt.TrayIcon;
import java.net.URL;

import javax.swing.ImageIcon;

public class Resources {
    public static Image getAsImage(String name, int height, int width) {
    	System.out.format("name=\"%s\"\n", name);
    	URL url = ClassLoader.getSystemResource(name);
    	System.out.format("URL=\"%s\"\n", url);
    	ImageIcon imageIcon = new ImageIcon(url);
    	Image rawImage = imageIcon.getImage();
    	Image scaledImage = rawImage.getScaledInstance(height, width, Image.SCALE_SMOOTH);
        return scaledImage;
    }
    
    public static ImageIcon getAsImageIcon(String name, int height, int width) {
    	Image scaledImage = getAsImage(name, height, width);
    	ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
    	return scaledImageIcon;
    }
    
    public static TrayIcon getAsTrayIcon(String name, int height, int width) {
        Image scaledImage = getAsImage(name, height, width);
        TrayIcon trayIcon = new TrayIcon(scaledImage);
        return trayIcon;
    }
}

