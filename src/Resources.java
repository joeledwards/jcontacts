import java.awt.Image;
import java.awt.TrayIcon;
import javax.swing.ImageIcon;

public class Resources {
    public static ImageIcon getAsImageIcon(String name, int height, int width) {
        return new ImageIcon((new ImageIcon(ClassLoader.getSystemResource(name))).getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH));
    }

    public static TrayIcon getAsTrayIcon(String name, int height, int width) {
        return new TrayIcon((new ImageIcon(ClassLoader.getSystemResource(name))).getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH));
    }
    
    public static Image getAsImage(String name, int height, int width) {
        return (new ImageIcon(ClassLoader.getSystemResource(name))).getImage().getScaledInstance(height, width, Image.SCALE_SMOOTH);
    }
}

