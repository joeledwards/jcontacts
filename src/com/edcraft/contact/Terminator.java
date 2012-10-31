package com.edcraft.contact;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Terminator
extends WindowAdapter
{
    private Window window = null;

    public Terminator(Window window)
    {
        this.window = window;
    }

    public void windowClosing(WindowEvent evt) {
        boolean close = true;
        if (close) {
            window.setVisible(false);
            System.exit(0);
        }
    }
}
