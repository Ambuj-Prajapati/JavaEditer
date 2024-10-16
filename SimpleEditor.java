import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SimpleEditor extends JFrame implements ActionListener, KeyListener {

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, formatMenu, runMenu, helpMenu;
    private JMenuItem[] fileMenuItems = new JMenuItem[6];
    private String[] fileMenuLabels = {"New", "Open", "Save", "Save As...", "Print...", "Exit"};
    private JMenuItem[] editMenuItems = new JMenuItem[6];
    private String[] editMenuLabels = {"Cut", "Copy", "Paste", "Delete", "Select All", "Time & Date"};
    private JMenuItem[] formatMenuItems = new JMenuItem[1];
    private String[] formatMenuLabels = {"Color"};
    private JMenuItem[] runMenuItems = new JMenuItem[2];
    private String[] runMenuLabels = {"Compile", "Run"};
    private JTextArea textArea, statusArea;
    private JScrollPane textScrollPane, statusScrollPane;
    private File currentFile;
    private boolean isTextChanged;
    private JColorChooser colorChooser;
    private JFileChooser fileChooser;

    public SimpleEditor() {
        // Initialize the frame
        frame = new JFrame("Simple Editor: Untitled");
        frame.setLayout(null);

        // Initialize the menu bar and menus
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(editMenu);

        formatMenu = new JMenu("Format");
        formatMenu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(formatMenu);

        runMenu = new JMenu("Run");
        runMenu.setMnemonic(KeyEvent.VK_R);
        menuBar.add(runMenu);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);

        // Add menu items for "Help"
        JMenuItem aboutMenuItem = new JMenuItem("About Editor");
        helpMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(this);

        // Add menu items for "File"
        for (int i = 0; i < fileMenuItems.length; i++) {
            fileMenuItems[i] = new JMenuItem(fileMenuLabels[i]);
            if (i == 4 || i == 5) fileMenu.addSeparator();
            fileMenu.add(fileMenuItems[i]);
            fileMenuItems[i].addActionListener(this);
        }

        // Add menu items for "Edit"
        for (int i = 0; i < editMenuItems.length; i++) {
            editMenuItems[i] = new JMenuItem(editMenuLabels[i]);
            editMenu.addSeparator();
            editMenu.add(editMenuItems[i]);
            editMenuItems[i].addActionListener(this);
        }

        // Add menu items for "Run"
        for (int i = 0; i < runMenuItems.length; i++) {
            runMenuItems[i] = new JMenuItem(runMenuLabels[i]);
            runMenu.add(runMenuItems[i]);
            runMenuItems[i].addActionListener(this);
        }

        // Add menu items for "Format"
        formatMenuItems[0] = new JMenuItem(formatMenuLabels[0]);
        formatMenu.add(formatMenuItems[0]);
        formatMenuItems[0].addActionListener(this);

        // Initialize the text areas and scroll panes
        textArea = new JTextArea(50, 50);
        statusArea = new JTextArea(50, 50);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 20));
        statusArea.setFont(new Font("Courier New", Font.PLAIN, 20));

        textScrollPane = new JScrollPane(textArea);
        statusScrollPane = new JScrollPane(statusArea);
        textScrollPane.setBounds(0, 0, 1350, 520);
        statusScrollPane.setBounds(0, 530, 1250, 135);

        frame.add(textScrollPane);
        frame.add(statusScrollPane);

        // Add key listener to the text area
        textArea.addKeyListener(this);

        // Initialize file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Java Files", "java"));

        // Set default frame properties
        frame.setSize(1500, 740);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == textArea) {
            isTextChanged = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                handleNew();
                break;
            case "Open":
                handleOpen();
                break;
            case "Save":
                handleSave();
                break;
            case "Save As...":
                handleSaveAs();
                break;
            case "Exit":
                handleExit();
                break;
            case "Cut":
                textArea.cut();
                break;
            case "Paste":
                textArea.paste();
                break;
            case "Copy":
                textArea.copy();
                break;
            case "Select All":
                textArea.selectAll();
                break;
            case "Time & Date":
                textArea.insert(new java.util.Date().toString(), textArea.getSelectionStart());
                break;
            case "Delete":
                textArea.replaceSelection("");
                break;
            case "Color":
                handleColor();
                break;
            case "Compile":
                handleCompile();
                break;
            case "Run":
                handleRun();
                break;
            case "About Editor":
                JOptionPane.showMessageDialog(this, "This Editor was created by Mr. Ambuj.\nProject completed under the guidance of Prof. Manish Bhatia.");
                break;
        }
    }

    private void handleNew() {
        if (isTextChanged) {
            int response = JOptionPane.showConfirmDialog(this, "Text has been changed.\nDo you want to save it?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                handleSave();
            } else if (response == JOptionPane.NO_OPTION) {
                textArea.setText("");
                isTextChanged = false;
            }
        } else {
            textArea.setText("");
            isTextChanged = false;
        }
    }

    private void handleOpen() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(currentFile)) {
                byte[] data = new byte[(int) currentFile.length()];
                fis.read(data);
                textArea.setText(new String(data));
                frame.setTitle("Simple Editor: " + currentFile.getName());
                isTextChanged = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage());
            }
        }
    }

    private void handleSave() {
        if (currentFile == null) {
            handleSaveAs();
        } else {
            try (FileOutputStream fos = new FileOutputStream(currentFile)) {
                fos.write(textArea.getText().getBytes());
                frame.setTitle("Simple Editor: " + currentFile.getName());
                isTextChanged = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    private void handleSaveAs() {
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (FileOutputStream fos = new FileOutputStream(currentFile)) {
                fos.write(textArea.getText().getBytes());
                frame.setTitle("Simple Editor: " + currentFile.getName());
                isTextChanged = false;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        }
    }

    private void handleExit() {
        if (isTextChanged) {
            int response = JOptionPane.showConfirmDialog(this, "Text has been changed.\nDo you want to save it?", "Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                handleSave();
            } else if (response == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    private void handleColor() {
        colorChooser = new JColorChooser();
        Color color = colorChooser.showDialog(this, "Choose a color", textArea.getForeground());
        if (color != null) {
            textArea.setForeground(color);
        }
    }

    private void handleCompile() {
        JOptionPane.showMessageDialog(this, "Compile action not implemented.");
    }

    private void handleRun() {
        JOptionPane.showMessageDialog(this, "Run action not implemented.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleEditor::new);
    }
}
