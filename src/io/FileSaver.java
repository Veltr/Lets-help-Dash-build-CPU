package io;

import view.windows.MainWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.regex.Pattern;

public class FileSaver {
    private static final String _title = "Logic builder";
    private static final String _extension = "circ";

    public static String save(MainWindow window) {
        JFileChooser ch = new JFileChooser();
        ch.setDialogTitle("Сохранение файла");
        ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        ch.setFileFilter(new FileNameExtensionFilter(_title, _extension));
        ch.setCurrentDirectory(new File(System.getProperty("user.dir")));
        ch.setSelectedFile(new File("Без имени." + _extension));
        if (ch.showSaveDialog(window) == JFileChooser.APPROVE_OPTION)
            return ch.getSelectedFile().getAbsolutePath();
        return null;
    }
    public static String load(MainWindow window) {
        JFileChooser ch = new JFileChooser();
        ch.setDialogTitle("Загрузка файла");
        ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        ch.setFileFilter(new FileNameExtensionFilter(_title, _extension));
        ch.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if (ch.showOpenDialog(window) == JFileChooser.APPROVE_OPTION)
            return ch.getSelectedFile().getAbsolutePath();
        return null;
    }
    public static String getTitle(String file) {
        if (file == null) return "untitled - " + _title;
        else {
            var s = file.split(Pattern.quote(System.getProperty("file.separator")));
            return s[s.length - 1].split("\\.")[0] + " - " + _title;
        }
    }
}
