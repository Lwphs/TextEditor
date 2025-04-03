import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorFrame extends JFrame implements ActionListener{

    //text area initialization
    protected JTextArea textArea = new JTextArea();
    //menu initialization
    protected JMenuBar menuBar = new JMenuBar();

    //these are the three options
    protected JMenu fileMenu = new JMenu("File");
    protected JMenu editMenu = new JMenu("Edit");
    protected JMenu helpMenu = new JMenu("Help");

    //options inside "File"
    protected JMenuItem openItem = new JMenuItem("Open");
    protected JMenuItem saveItem = new JMenuItem("Save");
    protected JMenuItem exitItem = new JMenuItem("Exit");

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitItem) confirmExit();
        if (e.getSource() == openItem) openOrSaveFile(1);
        if (e.getSource() == saveItem) openOrSaveFile(2);
    }
}
}
