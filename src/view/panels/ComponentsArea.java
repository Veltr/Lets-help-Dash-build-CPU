package view.panels;

import view.elements.logic.ANDElement;
import view.elements.logic.ORElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComponentsArea extends JScrollPane {
    public Workspace workspace;
    private final ComponentAreaListener _listener = new ComponentAreaListener();
    public ComponentsArea() {
        super();
        JPanel p = new JPanel();
        p.setBackground(new Color(255, 255, 255));
        //p.setSize(300, 200);
        p.setLayout(new GridLayout(3, 2, 10, 10));
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

        p.add(new Element<ANDElement>(new ImageIcon("src/resources/sprites/AND.png")));
        p.add(new Element<ORElement>(new ImageIcon("src/resources/sprites/OR.png")));

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(16);
        setViewportView(p);
        setVisible(true);
    }

    private class Element<T> extends JLabel {
        public T link;
        public Element(ImageIcon icon){
            setIcon(icon);
            setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }
    private static class ComponentAreaListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof Element<?>){

            }

        }
    }
}
