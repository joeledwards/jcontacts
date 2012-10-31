package com.edcraft.contact;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;

/*
------------------------------------------------
| Contacts                                 | X |
------------------------------------------------
|   ___________________________________        |
|  |_el________________________________|       |
|                                              |
|  -----------------------------------------   |
|  | Art Bell                            |^|   |
|  | Joel Edwards                        | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     | |   |
|  |                                     |v|   |
|  -----------------------------------------   |
|                                              |
| [+]                                      [-] |
------------------------------------------------
*/


// TODO:
// - create a form for editing contact information
// - create initial SQLite backend

public class ContactList
extends JFrame
implements ActionListener,
           ChangeListener,
           KeyListener
{

 // Class Components
    private String title;
    private Terminator terminator = null;

    private int currentTab = 0;
    private boolean isDoubleBuffered = false;

    private JPanel searchPanel = new JPanel(new BorderLayout());
    private JTextField searchField = new JTextField();

    //private JPanel listPanel = new JPanel(new BorderLayout());
    private JList list = new JList();
    //private JTree tree = new JTree();
    private JScrollPane scrollArea = new JScrollPane(list);
    
    private JPanel buttonPanel = new JPanel(new BorderLayout());
    private JButton addButton = new JButton("+");
    private JButton removeButton = new JButton("-");


    public ContactList(String title)
    {
        super(title);
        this.title = title;
        setMinimumSize(new Dimension(400, 400));

        setLayout(new BorderLayout());

        searchPanel.add(searchField, BorderLayout.CENTER);
        add(searchPanel, BorderLayout.NORTH);

        //listPanel.add
        add(scrollArea, BorderLayout.CENTER);

        buttonPanel.add(addButton, BorderLayout.WEST);
        buttonPanel.add(removeButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();

        terminator = new Terminator(this);
        this.addWindowListener(terminator);

     // Make sure keyboard shortcut combinations work everywhere
        this.addKeyListener(this);
        this.getContentPane().addKeyListener(this);
        addButton.addKeyListener(this);
        removeButton.addKeyListener(this); 

        updateGui();
    }

 // Quit the Application
    private void quit() {
        terminator.windowClosing(null);
    }

 // Keep the GUI up-to-date after various events
    public void updateGui()
    {
    }

 // ActionListener
    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
    }

 // StateListener
    public void stateChanged(ChangeEvent evt) {
        Object source = evt.getSource();
    }

 // KeyListener
    public void keyPressed(KeyEvent evt) {
        int modifiers = evt.getModifiersEx();
        int keyCode = evt.getKeyCode();

     // Ctrl + <key>
        int onmask = KeyEvent.CTRL_DOWN_MASK;
        int offmask = KeyEvent.SHIFT_DOWN_MASK | KeyEvent.ALT_DOWN_MASK;
        if ((modifiers & (onmask | offmask)) == onmask) {
            /*
            if (keyCode == KeyEvent.VK_S) {
                ;
            } else if (keyCode == KeyEvent.VK_A) {
                ;
            } else if (keyCode == KeyEvent.VK_Q) {
                ;
            } else if (keyCode == KeyEvent.VK_N) {
                ;
            } else if (keyCode == KeyEvent.VK_O) {
                ;
            } else if (keyCode == KeyEvent.VK_W) {
                ;
            }
            */
            ;
        }

     // Ctrl + Shift + <key>
        onmask = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        offmask = KeyEvent.ALT_DOWN_MASK;
        if ((modifiers & (onmask | offmask)) == onmask) {
            /*
            if (keyCode == KeyEvent.VK_S) {
                ;
            }
            */
        }

        updateGui();
    }

    public void keyReleased(KeyEvent evt) {
        updateGui();
    }

    public void keyTyped(KeyEvent evt) {
        updateGui();
    }
}
