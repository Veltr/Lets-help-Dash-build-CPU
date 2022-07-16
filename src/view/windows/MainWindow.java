package view.windows;

import view.elements.BaseComponent;
import view.elements.input.Lamp;
import view.elements.logic.ANDElement;
import view.elements.logic.IMPElement;
import view.elements.logic.ORElement;
import view.elements.logic.XORElement;
import view.elements.output.GeneratorB1;
import view.panels.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.regex.Pattern;

public class MainWindow extends JFrame {
    private boolean _isRunning = false;
    private final Workspace _workspace;
    private String _curFile;
    private final String _title = "God, forgive me";
    private final String _extension = "gfm";
    public MainWindow() {
        super();
        changeTitle();
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //setLayout(new GridLayout());
        var menu = new MainMenu();
        setJMenuBar(menu);

        _workspace = new Workspace();
        var compArea = new ComponentsArea();
        compArea.workspace = _workspace;

        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, _workspace, compArea);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(800-296);

        splitPane.setResizeWeight(1.0);
        add(splitPane);
    }

    public void run(){
        _isRunning = true;
        _workspace.run();
    }
    public void save(){
        if(_curFile == null){
            JFileChooser ch = new JFileChooser();
            ch.setDialogTitle("Сохранение файла");
            ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
            ch.setFileFilter(new FileNameExtensionFilter(_title,_extension));
            ch.setCurrentDirectory(new File(System.getProperty("user.dir")));
            ch.setSelectedFile(new File("Без имени." + _extension));
            if (ch.showSaveDialog(this) == JFileChooser.APPROVE_OPTION ) {
                _curFile = ch.getSelectedFile().getAbsolutePath();
                JOptionPane.showMessageDialog(this, "Файл '" + _curFile + " сохранен");
                changeTitle();
            }
            else return;
        }
        _workspace.save(_curFile);
    }
    public void load(){
        JFileChooser ch = new JFileChooser();
        ch.setDialogTitle("Загрузка файла");
        ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        ch.setFileFilter(new FileNameExtensionFilter(_title,_extension));
        ch.setCurrentDirectory(new File(System.getProperty("user.dir")));
        if (ch.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ) {
            _workspace.load(_curFile = ch.getSelectedFile().getAbsolutePath());
            changeTitle();
        }
    }
    private void add(BaseComponent e){
        _workspace.add(e);
    }
    private void changeTitle(){
        if(_curFile == null) setTitle("untitled - " + _title);
        else {
            var s = _curFile.split(Pattern.quote(System.getProperty("file.separator")));
            setTitle(s[s.length - 1].split("\\.")[0] + " - " + _title);
        }
    }

    private class MainMenu extends JMenuBar {
        public MainMenu(){
            super();
            add(createFileMenu());
            add(createViewMenu());
            add(createComponentsMenu());
        }

        private JMenu createFileMenu() {
            JMenu file = new JMenu("Файл");
            JMenuItem open = new JMenuItem("Открыть");
            JMenuItem save = new JMenuItem("Сохранить");
            JMenuItem exit = new JMenuItem("Выход");
            file.add(open);
            file.add(save);
            file.addSeparator();
            file.add(exit);

            open.addActionListener(e -> load());
            exit.addActionListener(e -> System.exit(0));
            save.addActionListener(e -> save());
            save.setAccelerator(KeyStroke.getKeyStroke("control S"));
            return file;
        }
        private JMenu createViewMenu() {
            // создадим выпадающее меню
            JMenu viewMenu = new JMenu("Работа");
            JMenuItem runItem = new JMenuItem("Запустить");
            viewMenu.add(runItem);

            runItem.addActionListener(e -> {
                run();
            });
            return viewMenu;
        }

        private JMenu createComponentsMenu(){
            JMenu m0 = new JMenu("Компоненты");

            int k = 0;
            JMenu[] m = new JMenu[3];
            m[k++] = new JMenu("Логические");
            m[k++] = new JMenu("Выходные");
            m[k] = new JMenu("Входные");

            int n = 0; k = 0;
            JMenuItem[] i1 = new JMenuItem[4];
            i1[n] = new JMenuItem("AND");
            i1[n++].addActionListener(e -> MainWindow.this.add(new ANDElement()));
            i1[n] = new JMenuItem("OR");
            i1[n++].addActionListener(e -> MainWindow.this.add(new ORElement()));
            i1[n] = new JMenuItem("XOR");
            i1[n++].addActionListener(e -> MainWindow.this.add(new XORElement()));
            i1[n] = new JMenuItem("IMP");
            i1[n].addActionListener(e -> MainWindow.this.add(new IMPElement()));
            for (var i : i1) m[k].add(i); k++;

            n = 0;
            JMenuItem[] i2 = new JMenuItem[1];
            i2[n] = new JMenuItem("Generator B1");
            i2[n].addActionListener(e -> MainWindow.this.add(new GeneratorB1()));
            for (var i : i2) m[k].add(i); k++;

            n = 0;
            JMenuItem[] i3 = new JMenuItem[1];
            i3[n] = new JMenuItem("Lamp");
            i3[n].addActionListener(e -> MainWindow.this.add(new Lamp()));
            for (var i : i3) m[k].add(i); k++;

            for(var i : m) m0.add(i);
            return m0;
        }
    }
}

