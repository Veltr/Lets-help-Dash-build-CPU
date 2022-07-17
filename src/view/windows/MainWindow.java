package view.windows;

import view.elements.BaseComponent;
import view.elements.input.B1.Lamp;
import view.elements.logic.*;
import view.elements.output.B1.GeneratorB1;
import view.panels.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.regex.Pattern;

public class MainWindow extends JFrame {
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
                var s = _curFile.split(Pattern.quote(System.getProperty("file.separator")));
                JOptionPane.showMessageDialog(this, "Файл " + s[s.length - 1] + " сохранен");
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
    public void reload(){
        if(_curFile != null) _workspace.load(_curFile);
    }
    public void newCircuit(){
        _curFile = null;
        _workspace.clearAll();
        changeTitle();
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
            JMenuItem newC = new JMenuItem("Новая схема");
            JMenuItem open = new JMenuItem("Открыть");
            JMenuItem reload = new JMenuItem("Перезагрузить");
            JMenuItem save = new JMenuItem("Сохранить");
            JMenuItem exit = new JMenuItem("Выход");

            file.add(newC);
            file.add(open);
            file.add(reload);
            file.add(save);
            file.addSeparator();
            file.add(exit);

            newC.addActionListener(e -> newCircuit());
            newC.setAccelerator(KeyStroke.getKeyStroke("control N"));
            open.addActionListener(e -> load());
            open.setAccelerator(KeyStroke.getKeyStroke("control O"));
            reload.addActionListener(e -> reload());
            reload.setAccelerator(KeyStroke.getKeyStroke("control shift R"));
            save.addActionListener(e -> save());
            save.setAccelerator(KeyStroke.getKeyStroke("control S"));
            exit.addActionListener(e -> System.exit(0));
            return file;
        }
        private JMenu createViewMenu() {
            // создадим выпадающее меню
            JMenu viewMenu = new JMenu("Работа");
            JMenuItem runItem = new JMenuItem("Запустить");
            viewMenu.add(runItem);

            runItem.addActionListener(e -> run());
            runItem.setAccelerator(KeyStroke.getKeyStroke("control R"));
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
            JMenuItem[] i1 = new JMenuItem[5];
            i1[n] = new JMenuItem("NOT");
            i1[n++].addActionListener(e -> MainWindow.this.add(new NOTElement()));
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

