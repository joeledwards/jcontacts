package com.edcraft.contact;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BoxLayout;
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
------------------------------------------------------------------------
| Contact Form                                                     | X |
------------------------------------------------------------------------
|                ______________________________________________        |
|  Name:        |_Joel_Edwards_________________________________|       |
|                ______________________________________________        |
|  Tags:        |_Customer_Centrix,_Loadstorm__________________|       |
|                ______________________________________________        |
|  Work Phone:  |_(505)_853-2582_______________________________|       |
|                ______________________________________________        |
|  Home Phone:  |_(505)_918-2106_______________________________|       |
|                ______________________________________________        |
|  Work E-mail: |_joel@ed-craft.com____________________________|       |
|                ______________________________________________        |
|  Home E-mail: |_joeledwards@gmail.com________________________|       |
|                                                                      |
| [Discard]                                                   [Update] |
------------------------------------------------------------------------
*/


// TODO:
// - create a form for editing contact information
// - create initial SQLite backend

public class ContactForm
extends JFrame
implements ActionListener,
           ChangeListener,
           KeyListener
{
    public static final int MINIMUM_WIDTH  = 400;
    public static final int MINIMUM_HEIGHT = 300;
    public static final int DEFAULT_WIDTH  = 400;
    public static final int DEFAULT_HEIGHT = 300; 

 // Class Components
    private Terminator terminator = null;
    
    private boolean newContact = true;
    private int contactId = -1;

    private JLabel		nameLabel;
    private JTextField	nameField;
    private JLabel    	tagsLabel;
    private JTextField	tagsField;
    private JLabel    	workPhoneLabel;
    private JTextField	workPhoneField;
    private JLabel    	homePhoneLabel;
    private JTextField	homePhoneField;
    private JLabel    	workEmailLabel;
    private JTextField	workEmailField;
    private JLabel    	homeEmailLabel;
    private JTextField	homeEmailField;
    
    private JPanel searchPanel = new JPanel(new BorderLayout());
    private JTextField searchField = new JTextField();

    //private JPanel listPanel = new JPanel(new BorderLayout());
    private JList list = new JList();
    //private JTree tree = new JTree();
    private JScrollPane scrollArea = new JScrollPane(list);
    
    private JPanel buttonPanel = new JPanel(new BorderLayout());
    
    private JButton cancelButton = new JButton("Discard");
    private JButton saveButton = new JButton("Save"); // Save / Update

    public ContactForm(String title)
    {
        super(title);
        
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	    Container contentPane = getContentPane();
        setLayout(new BorderLayout());
	    //contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
	    
        // Filters
        JPanel contactContainer = new JPanel(new BorderLayout());
        contactContainer.setBorder(new EmptyBorder(5,3,0,3));
        GridBagLayout contactGrid = new GridBagLayout();
        JPanel contactPanel = new JPanel(contactGrid);
        contactPanel.setBorder(new TitledBorder(new EtchedBorder(), "Filters"));
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;

        c.insets.top = 0;
        c.insets.left = 5;
        c.insets.bottom = 5;
        c.insets.right = 5;

        // create
        nameField = new JTextField();
        //nameField.setColumns(6);
        nameField.setEditable(true);
        nameField.setSize(DEFAULT_WIDTH, 1);
        nameLabel = new JLabel("Name: ", JLabel.TRAILING); 
        nameLabel.setLabelFor(nameField);
        // add to grid
        c.gridx = 0; c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.NONE;
        contactGrid.setConstraints(nameLabel, c);
        contactPanel.add(nameLabel);
        c.gridx = 1; c.gridy = 0;
        c.weightx = 50.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contactGrid.setConstraints(nameField, c);
        contactPanel.add(nameField);

        // create
        tagsField = new JTextField();
        //tagsField.setColumns(6);
        tagsField.setEditable(true);
        tagsField.setSize(DEFAULT_WIDTH, 1);
        tagsLabel = new JLabel("Station: ", JLabel.TRAILING); 
        tagsLabel.setLabelFor(tagsField);
        // add to grid
        c.gridx = 0; c.gridy = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.NONE;
        contactGrid.setConstraints(tagsLabel, c);
        contactPanel.add(tagsLabel);
        c.gridx = 1; c.gridy = 1;
        c.weightx = 50.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contactGrid.setConstraints(tagsField, c);
        contactPanel.add(tagsField);

        // create
        workPhoneField = new JTextField();
        //workPhoneField.setColumns(6);
        workPhoneField.setEditable(true);
        workPhoneField.setSize(DEFAULT_WIDTH, 1);
        workPhoneLabel = new JLabel("Work Phone: ", JLabel.TRAILING); 
        workPhoneLabel.setLabelFor(workPhoneField);
        // add to grid
        c.gridx = 0; c.gridy = 2;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.NONE;
        contactGrid.setConstraints(workPhoneLabel, c);
        contactPanel.add(workPhoneLabel);
        c.gridx = 1; c.gridy = 2;
        c.weightx = 50.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contactGrid.setConstraints(workPhoneField, c);
        contactPanel.add(workPhoneField);

        // create
        homePhoneField = new JTextField();
        //homePhoneField.setColumns(6);
        homePhoneField.setEditable(true);
        homePhoneField.setSize(DEFAULT_WIDTH, 1);
        homePhoneLabel = new JLabel("Home Phone: ", JLabel.TRAILING); 
        homePhoneLabel.setLabelFor(homePhoneField);
        // add to grid
        c.gridx = 0; c.gridy = 3;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.NONE;
        contactGrid.setConstraints(homePhoneLabel, c);
        contactPanel.add(homePhoneLabel);
        c.gridx = 1; c.gridy = 3;
        c.weightx = 50.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contactGrid.setConstraints(homePhoneField, c);
        contactPanel.add(homePhoneField);

        // create
        workEmailField = new JTextField();
        //workEmailField.setColumns(6);
        workEmailField.setEditable(true);
        workEmailField.setSize(DEFAULT_WIDTH, 1);
        workEmailLabel = new JLabel("Work E-Mail: ", JLabel.TRAILING); 
        workEmailLabel.setLabelFor(workEmailField);
        // add to grid
        c.gridx = 0; c.gridy = 4;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.NONE;
        contactGrid.setConstraints(workEmailLabel, c);
        contactPanel.add(workEmailLabel);
        c.gridx = 1; c.gridy = 4;
        c.weightx = 50.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        contactGrid.setConstraints(workEmailField, c);
        contactPanel.add(workEmailField);

        c.insets.top = 0;
        c.insets.bottom = 8;

        // create
        homeEmailField = new JTextField();
        //homeEmailField.setColumns(6);
        homeEmailField.setEditable(true);
        homeEmailField.setSize(DEFAULT_WIDTH, 1);
        homeEmailLabel = new JLabel("Home E-Mail: ", JLabel.TRAILING); 
        homeEmailLabel.setLabelFor(homeEmailField);
        // add to grid
        c.gridx = 0; c.gridy = 5;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.NONE;
        contactGrid.setConstraints(homeEmailLabel, c);
        contactPanel.add(homeEmailLabel);
        c.gridx = 1; c.gridy = 5;
        c.weightx = 50.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.gridwidth = GridBagConstraints.REMAINDER;
        contactGrid.setConstraints(homeEmailField, c);
        contactPanel.add(homeEmailField);

        contactContainer.add(contactPanel, BorderLayout.CENTER);
        add(contactContainer, BorderLayout.CENTER);

        buttonPanel.add(cancelButton, BorderLayout.WEST);
        buttonPanel.add(saveButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();

        terminator = Terminator.createTerminator(this);
        this.addWindowListener(terminator);

     // Make sure keyboard shortcut combinations work everywhere
        this.addKeyListener(this);
        this.getContentPane().addKeyListener(this);
        cancelButton.addActionListener(this);
        saveButton.addActionListener(this); 

        updateGui();
    }
    
    // Contact ID
    public void setContactId(int contactId) {
    	this.contactId = contactId;
    }
    
    public int getContactId() {
    	return contactId;
    }
    
    // New contact
    public boolean isNewContact() {
    	return newContact;
    }
    
    public void clearNewContact() {
    	newContact = false;
    }
    
    public void setNewContact() {
    	newContact = true;
    }
    
    // Name
    public void setName(String name) {
    	nameField.setText(name);
    }
    
    public String getName() {
    	return nameField.getText();
    }

    // Tags
    public void setTags(Collection<String> tags) {
    	String tagString = "";
    	boolean first = true;
    	for (String tag: tags) {
    		if (tag.length() > 0) {
    			tagString += first ? "" : ", " + tag;
    			first = false;
    		}
    	}
    	nameField.setText(tagString);
    }
    
    public Collection<String> getTags() {
    	ArrayList<String> tags = new ArrayList<String>();
    	for (String tag: nameField.getText().split(",")) {
    		tags.add(tag.trim());
    	}
    	return tags;
    }
    
    // Phone Numbers
    public void setWorkPhone(String workPhone) {
    	workPhoneField.setText(workPhone);
    }
    
    public String getWorkPhone() {
    	return workPhoneField.getText();
    }

    public void setHomePhone(String homePhone) {
    	homePhoneField.setText(homePhone);
    }
    
    public String getHomePhone() {
    	return homePhoneField.getText();
    }

    // E-Mail Addresses
    public void setWorkEmail(String workEmail) {
    	workEmailField.setText(workEmail);
    }
    
    public String getWorkEmail() {
    	return workEmailField.getText();
    }

    public void setHomeEmail(String homeEmail) {
    	homeEmailField.setText(homeEmail);
    }
    
    public String getHomeEmail() {
    	return homeEmailField.getText();
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
