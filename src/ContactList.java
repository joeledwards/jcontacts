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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/*
------------------------------------------------
|                                          | X |
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
implements ActionListener, ChangeListener, KeyListener
{

 // Class Components
    private String title;
    private Terminator terminator = null;

    private int currentTab = 0;
    private boolean isDoubleBuffered = false;

    private JPanel searchPanel = new JPanel(new BorderLayout());
    private JTextField searchField = new JTextField();

    private JPanel listPanel = new JPanel(new BorderLayout());
    private JTree tree = new JTree();
    private JScrollPane scrollArea = new JScrollPane(tree);
    
    private JPanel buttonPanel = new JPanel(new BorderLayout());
    private JButton addButton = new JButton("+");
    private JButton removeButton = new JButton("-");


    public ContactList(String title)
    {
        super(title);
        this.title = title;
        setMinimumSize(new Dimension(400, 400));

        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        setLayout(new BorderLayout());

        searchPanel.add(searchField, BorderLayout.CENER);
        add(searchPanel, BorderLayout.NORTH);

        //listPanel.add
        add(scrollArea, BorderLayout.CENTER);

        buttonPanel.add(addButton, BorderLayout.WEST);
        buttonPanel.add(removeButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        createNewTab(null);
        pack();

        terminator = new Terminator(this, tabs);
        this.addWindowListener(terminator);
        loadButton.addActionListener(this);
        saveButton.addActionListener(this); 

     // Make sure keyboard shortcut combinations work everywhere
        this.addKeyListener(this);
        this.getContentPane().addKeyListener(this);
        tabs.addKeyListener(this);
        loadButton.addKeyListener(this);
        saveButton.addKeyListener(this); 

        updateGui();
    }

 // File Operations
    private void open()
    {
        // select file to load
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open File");
        fileChooser.setCurrentDirectory(defaultOpenDir);
        fileChooser.setMultiSelectionEnabled(false);
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            defaultOpenDir = fileChooser.getCurrentDirectory();
            File file = fileChooser.getSelectedFile();

            int openIndex = getOpenIndex(file);
            if (openIndex < 0) {
                Tab tab = (Tab)(tabs.getSelectedComponent());
                if ((tab != null) && tab.isFresh()) {
                    tab.setFile(file);
                } else {
                    createNewTab(file);
                }
            } else {
                tabs.setSelectedIndex(openIndex);
            }
        }
    }

    private void save()
    {
        int index = tabs.getSelectedIndex();
        if (index < 0) {
            return;
        }

        Tab tab = (Tab)tabs.getComponentAt(index);
        if (tab.getFile() == null) {
            saveAs();
        } else {
            tab.save();
        }
    }

    private void saveAs()
    {
        int index = tabs.getSelectedIndex();
        if (index < 0) {
            return;
        }

        Tab tab = (Tab)tabs.getComponentAt(index);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As File");
        fileChooser.setCurrentDirectory(defaultSaveDir);
        fileChooser.setMultiSelectionEnabled(false);
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            defaultSaveDir = fileChooser.getCurrentDirectory();
            File file = fileChooser.getSelectedFile();
            tab.saveToFile(file);
        }
    }

 // Edit Operations
    private void selectAll()
    {
        int index = tabs.getSelectedIndex();
        if (index < 0) {
            return;
        }
        Tab tab = (Tab)tabs.getComponentAt(index);
        tab.getArea().selectAll();
    }

    private static int EDIT_OPERATION_COPY  = 1;
    private static int EDIT_OPERATION_CUT   = 2;
    private static int EDIT_OPERATION_PASTE = 3;

    private void editOperation(int operation) {
        int index = tabs.getSelectedIndex();
        if (index >= 0) {
            Tab tab = (Tab)tabs.getComponentAt(index);
            JTextArea area = tab.getArea();
            int selectionStart = area.getSelectionStart();
            int selectionEnd = area.getSelectionEnd();
            int selectionWidth = selectionEnd - selectionStart;
            int caretPosition = area.getCaretPosition();
            if (selectionWidth > 0) {
                if ((operation == EDIT_OPERATION_COPY) ||
                    (operation == EDIT_OPERATION_CUT)) {
                    clipboard = area.getSelectedText();
                    if (operation == EDIT_OPERATION_CUT) {
                        area.replaceSelection("");
                    }
                } else if (operation == EDIT_OPERATION_PASTE) {
                    area.replaceSelection(clipboard);
                }
            } else if ((operation == EDIT_OPERATION_PASTE) &&
                       (caretPosition >= 0)) {
                area.insert(clipboard, caretPosition);
            }
        }
    }

    private void copy()
    {
        editOperation(EDIT_OPERATION_COPY);
    }

    private void cut()
    {
        editOperation(EDIT_OPERATION_CUT);
    }

    private void paste()
    {
        editOperation(EDIT_OPERATION_PASTE);
    }

 // Quit the Application
    private void quit() {
        terminator.windowClosing(null);
    }

 // Keep the GUI up-to-date after various events
    public void updateGui()
    {
        int index = tabs.getSelectedIndex();
        if (index >= 0) {
            Tab tab = (Tab)tabs.getComponentAt(index);
            if (tab.isFresh()) {
                saveFileMenuItem.setEnabled(false);
                saveAsFileMenuItem.setEnabled(false);
            } else {
                saveAsFileMenuItem.setEnabled(true);
                if (tab.isSaved()) {
                    saveFileMenuItem.setEnabled(false);
                } else {
                    saveFileMenuItem.setEnabled(true);
                }
            }
            closeFileMenuItem.setEnabled(true);

            JTextArea area = tab.getArea();
            int selectionStart = area.getSelectionStart();
            int selectionEnd = area.getSelectionEnd();
            int selectionWidth = selectionEnd - selectionStart;
            if (selectionWidth > 0) {
                cutEditMenuItem.setEnabled(true);
                copyEditMenuItem.setEnabled(true);
            } else {
                cutEditMenuItem.setEnabled(false);
                copyEditMenuItem.setEnabled(false);
            }

            int caretPosition = area.getCaretPosition();
            if (((caretPosition >= 0) || (selectionWidth > 0)) && (clipboard != null)) {
                pasteEditMenuItem.setEnabled(true);
            } else {
                pasteEditMenuItem.setEnabled(false);
            }
        } else {
            closeFileMenuItem.setEnabled(false);
            saveFileMenuItem.setEnabled(false);
            saveAsFileMenuItem.setEnabled(false);
        }
    }

 // ActionListener
    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
        if (source == loadButton) {
            open();
        }
        else if (source == saveButton) {
            save();
        }
        else if (source == openFileMenuItem) {
            open();
        }
        else if (source == saveFileMenuItem) {
            save();
        }
        else if (source == saveAsFileMenuItem) {
            saveAs();
        }
        else if (source == newFileMenuItem) {
            createNewTab(null);
        }
        else if (source == closeFileMenuItem) {
            closeCurrentTab();
        }
        else if (source == quitFileMenuItem) {
            quit();
        }
        else if (source == selectAllEditMenuItem) {
            selectAll();
        }
        else if (source == copyEditMenuItem) {
            copy();
        }
        else if (source == cutEditMenuItem) {
            cut();
        }
        else if (source == pasteEditMenuItem) {
            paste();
        }
        updateGui();
    }

 // StateListener
    public void stateChanged(ChangeEvent evt) {
        Object source = evt.getSource();
        if (source == tabs) {
            updateGui();
        }
    }

 // KeyListener
    public void keyPressed(KeyEvent evt) {
        int modifiers = evt.getModifiersEx();
        int keyCode = evt.getKeyCode();

     // Ctrl + <key>
        int onmask = KeyEvent.CTRL_DOWN_MASK;
        int offmask = KeyEvent.SHIFT_DOWN_MASK | KeyEvent.ALT_DOWN_MASK;
        if ((modifiers & (onmask | offmask)) == onmask) {
            if (keyCode == KeyEvent.VK_S) {
                save();
            } else if (keyCode == KeyEvent.VK_A) {
                selectAll();
            } else if (keyCode == KeyEvent.VK_Q) {
                quit();
            } else if (keyCode == KeyEvent.VK_N) {
                createNewTab(null);
            } else if (keyCode == KeyEvent.VK_O) {
                open();
            } else if (keyCode == KeyEvent.VK_W) {
                closeCurrentTab();
            }
        }

     // Ctrl + Shift + <key>
        onmask = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        offmask = KeyEvent.ALT_DOWN_MASK;
        if ((modifiers & (onmask | offmask)) == onmask) {
            if (keyCode == KeyEvent.VK_S) {
                saveAs();
            }
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
