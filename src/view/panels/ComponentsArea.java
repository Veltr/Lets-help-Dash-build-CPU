package view.panels;

import view.elements.BaseComponent;
import view.elements.input.B1.Lamp;
import view.elements.logic.*;
import view.elements.output.B1.GeneratorB1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

public class ComponentsArea extends JScrollPane {
    public Workspace workspace;
    private final ComponentAreaListener _listener = new ComponentAreaListener();
    public ComponentsArea() {
        super();
        JPanel p = new JPanel();
        p.setBackground(new Color(255, 255, 255));
        //p.setSize(300, 200);
        p.setLayout(new GridLayout(4, 2, 10, 10));
//        for (int row = 0; row < 4; row++) {
//            for (int col = 0; col < 2; col++) {
//                var t = new ANDElement();
//                t.setFixedSize(128, 128);
//                p.add(t);
//            }
//        }
        /*p.add(new ANDElement());
        p.add(new ORElement());
        p.add(new XORElement());
        p.add(new IMPElement());
        p.add(new GeneratorB1());
        p.add(new Lamp());*/

        p.add(new Element(new NOTElement(), new ImageIcon("src/resources/sprites/NOT.png")));
        p.add(new Element(new ANDElement(), new ImageIcon("src/resources/sprites/AND.png")));
        p.add(new Element(new ORElement(), new ImageIcon("src/resources/sprites/OR.png")));
        p.add(new Element(new XORElement(), new ImageIcon("src/resources/sprites/XOR.png")));
        p.add(new Element(new IMPElement(), new ImageIcon("src/resources/sprites/IMP.png")));

        p.add(new Element(new GeneratorB1(), new ImageIcon("src/resources/sprites/EmptyGEN.png")));

        p.add(new Element(new Lamp(), new ImageIcon("src/resources/sprites/LAMP0.png")));

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(16);
        setViewportView(p);
        setVisible(true);
    }

    private class Element extends JLabel {
        public String obj;
        public Element(BaseComponent component, ImageIcon icon){
            setIcon(icon);
            setBorder(BorderFactory.createLineBorder(Color.black));

            obj = component.getClass().getName();
            addMouseListener(new ComponentAreaListener());
        }
    }
    private class ComponentAreaListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof Element){
                try {
                    workspace.add((BaseComponent)Class.forName(((Element)e.getSource()).obj).getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }

        }
    }
}
