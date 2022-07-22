package view.panels;

import view.elements.BaseElement;
import view.elements.input.B1.Lamp;
import view.elements.logic.*;
import view.elements.output.B1.GeneratorB1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class ComponentsArea extends JScrollPane {
    protected Workspace _workspace;
    protected MainSplitPane _splitPane;
    private final ComponentAreaListener _listener = new ComponentAreaListener();

    public ComponentsArea() {
        super();
        JPanel p = new JPanel();
        _listener._base = p;
        p.setBackground(new Color(255, 255, 255));
        //p.setSize(300, 200);
        p.setLayout(new GridLayout(4, 2, 10, 10));

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

        public Element(BaseElement component, ImageIcon icon) {
            setIcon(icon);
            setBorder(BorderFactory.createLineBorder(Color.black));
            setPreferredSize(new Dimension(128, 128));

            obj = component.getClass().getName();
            addMouseListener(_listener);
            addMouseMotionListener(_listener);
            setOpaque(true);
            setBackground(Color.white);
        }

        public Element(Element source) {
            super();
            this.obj = source.obj;
        }
    }

    private class ComponentAreaListener extends MouseAdapter {
        private Element _curElem;
        private Point _start;
        protected Container _base;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof Element) {
                try {
                    _workspace.add((BaseElement) Class.forName(((Element) e.getSource()).obj).getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                _curElem = (Element) e.getSource();
                _start = SwingUtilities.convertPoint(_curElem, e.getPoint(), _base);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (_curElem != null) {
                Point loc = SwingUtilities.convertPoint(_curElem, e.getPoint(), null);
                loc = SwingUtilities.convertPoint(_curElem, e.getPoint(), _base);
                Point newLoc = _curElem.getLocation();
                newLoc.translate(loc.x - _start.x, loc.y - _start.y);
                _curElem.setLocation(newLoc);
                _start = loc;
                _base.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            BaseElement element;

            try {
                element = (BaseElement) Class.forName(((Element) e.getSource()).obj).getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            _workspace.add(element);
            Point p = SwingUtilities.convertPoint(_curElem, e.getPoint(), _workspace._view);
            _workspace.setPosition(element, p.x, p.y);
            _base.revalidate();
            _base.repaint();
        }
    }
}
