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
    private boolean exit = false;

    public Terminator(Window window)
    {
        this.window = window;
    }
    
    public static Terminator createTerminator(Window window)
    {   
    	Terminator terminator = new Terminator(window);
    	return terminator;
    }
    
    public static Terminator createExitingTerminator(Window window)
    {
    	Terminator terminator = new Terminator(window);
    	terminator.exit = true;
    	return terminator;
    }

    public void windowClosing(WindowEvent evt) {
        window.setVisible(false);
        if (exit) {
            System.exit(0);
        }
    }
}
