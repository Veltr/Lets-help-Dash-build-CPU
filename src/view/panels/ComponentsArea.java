package view.panels;

import view.elements.input.Lamp;
import view.elements.logic.ANDElement;
import view.elements.logic.IMPElement;
import view.elements.logic.ORElement;
import view.elements.logic.XORElement;
import view.elements.output.Generator;

import javax.swing.*;
import java.awt.*;

public class ComponentsArea extends JScrollPane {
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
        p.add(new ANDElement());
        p.add(new ORElement());
        p.add(new XORElement());
        p.add(new IMPElement());
        p.add(new Generator());
        p.add(new Lamp());

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        getVerticalScrollBar().setUnitIncrement(16);
        setViewportView(p);
        setVisible(true);
    }
}
