import gfx.windows.MainWindow;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new MainWindow();
            ex.setVisible(true);
        });
    }
}
