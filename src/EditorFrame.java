import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class EditorFrame extends JFrame implements ActionListener {
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
        this.setTitle("Lwphs Text Editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(600,600);
        this.setLocationRelativeTo(null); //Always spawns in the middle

        //We add the scrollPane always and set a padding on the textArea
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setMargin(new Insets(10,5, 5, 5));
        textArea.setFont(new Font("Helvetica", Font.PLAIN, 12));
        this.add(scrollPane);

        //Menu items
        this.setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        //File items
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        //Edit items
        editMenu.add(undoItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        //Close on pressing the "X" button
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
    }

    private void openOrSaveFile(int option) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue;
        if (option == 1) {
            returnValue = fileChooser.showOpenDialog(this);
        } else
            returnValue = fileChooser.showSaveDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (option == 1) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.read(reader, null);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                file = new File(file.getAbsolutePath() + ".txt"); //putting a .txt extension

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    textArea.write(writer);
                    JOptionPane.showMessageDialog(this, "File saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
