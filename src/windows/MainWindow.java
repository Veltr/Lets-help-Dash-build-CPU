package windows;

import javax.swing.*;

public class MainWindow extends JFrame {
    private JPanel _main;

    public MainWindow() {
        super("God, forgive me");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        _main = new JPanel();
        add(_main);
    }
}
