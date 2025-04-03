import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EditorFrame extends JFrame implements ActionListener{
    protected JTextArea textArea = new JTextArea(); //text area initialization
    protected JMenuBar menuBar = new JMenuBar(); //menu initialization
    JScrollPane scrollPane = new JScrollPane(textArea); //scrollBar

    //Menu options
    protected JMenu fileMenu = new JMenu("File");
    protected JMenu editMenu = new JMenu("Edit");
    protected JMenu helpMenu = new JMenu("Help");

    //options inside "File"
    protected JMenuItem openItem = new JMenuItem("Open          Ctrl+O");
    protected JMenuItem saveItem = new JMenuItem("Save            Ctrl+S");
    protected JMenuItem exitItem = new JMenuItem("Exit             Alt+F4");

    //options inside "Edit"
    protected JMenuItem undoItem = new JMenuItem("Undo     ");
    protected JMenuItem copyItem = new JMenuItem("Copy     ");
    protected JMenuItem pasteItem = new JMenuItem("Paste    ");

    EditorFrame() {
        this.setTitle("Text Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);

        //textArea declaration
        this.add(textArea);

        //menu items
        this.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
    }

    private void openOrSaveFile(int option) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (option == 1) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.read(reader, null);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    textArea.write(writer);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
        }
    }

    private void confirmExit() {
        JLabel messageLabel = new JLabel("Are you sure you want to exit?", SwingConstants.CENTER);
        int response = JOptionPane.showConfirmDialog(
                null,
                messageLabel,
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitItem) confirmExit();
        if (e.getSource() == openItem) openOrSaveFile(1);
        if (e.getSource() == saveItem) openOrSaveFile(2);
    }
}
}
