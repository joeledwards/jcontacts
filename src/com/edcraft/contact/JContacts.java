package com.edcraft.contact;
import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;

import java.lang.Integer;

public class JContacts
implements ActionListener, MouseListener
{
    private SystemTray  tray;
    private TrayIcon    icon;
    private PopupMenu   menu;

    private ContactList list;
    private ContactForm form;

    private Image bookImage;

    private MenuItem     listItem;
    private Image        listImage;
    private MenuShortcut listShortcut;

    private MenuItem     quitItem;
    private Image        quitImage;
    private MenuShortcut quitShortcut;

    public static void main(String args[])
    {
        if (!SystemTray.isSupported()) {
            System.err.println("The system tray is not supported.");
            System.exit(1);
        }
        
        JContacts core = new JContacts();
    }

    public JContacts()
    {
        tray = SystemTray.getSystemTray();
        menu = new PopupMenu();

        bookImage = Resources.getAsImage("resources/icons/address-book-128.png", 20, 20);

        icon = new TrayIcon(bookImage, "Contacts", menu);

        listShortcut = new MenuShortcut(KeyEvent.VK_O, false);
        listItem = new MenuItem("Open List", listShortcut);
        listItem.addActionListener(this);

        quitShortcut = new MenuShortcut(KeyEvent.VK_Q, false);
        quitItem = new MenuItem("Quit", quitShortcut);
        quitItem.addActionListener(this);

        menu.add(listItem);
        menu.add(quitItem);

        try {
            tray.add(icon);
        }
        catch (AWTException exc) {
            System.err.println("Failed to add tray icon.");
            System.exit(1);
        }
    }

    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == quitItem) {
            System.exit(1);
        } else if (event.getSource() == listItem) {
            list = new ContactList("Contacts"); 
            list.setVisible(true);
        }
    }

    public void mouseClicked(MouseEvent event)
    {
    }

    public void mouseDragged(MouseEvent event)
    {
    }

    public void mouseEntered(MouseEvent event)
    {
    }

    public void mouseExited(MouseEvent event)
    {
    }

    public void mouseMoved(MouseEvent event)
    {
    }

    public void mousePressed(MouseEvent event)
    {
    }

    public void mouseReleased(MouseEvent event)
    {
    }
}

