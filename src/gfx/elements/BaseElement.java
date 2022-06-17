package gfx.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class BaseElement extends JLabel {
    protected PortsData _portsData;
    protected ArrayList<ConnectionPoint> _inputPorts;
    protected ArrayList<ConnectionPoint> _outputPorts;
    public BaseElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(new Listener());
        _inputPorts = new ArrayList<>();
        _outputPorts = new ArrayList<>();
        fillData();
    }

    public void setFixedSize(int width, int height){
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

    protected void buildGFX(String source) {
        // setIcon(new ImageIcon(source));
        for (PortData t : _portsData.input) {
            var point = new ConnectionPoint(t, true);
            point.setBounds(t.position.x - 5, t.position.y - 10, 10, 10);
            _inputPorts.add(point);
            add(point);
        }
        for (PortData t : _portsData.output) {
            var point = new ConnectionPoint(t, false);
            point.setBounds(t.position.x - 5, t.position.y, 10, 10);
            _outputPorts.add(point);
            add(point);
        }
        setFixedSize(128, 128);
        setIcon(new ImageIcon(source));
    }

    protected void fillData() { _portsData = new PortsData(); }

    public static class PortData {
        public enum BusType { B1, B8, B16 }
        public BusType type;
        public BaseElement connection;
        public Point position;
        PortData(BusType type, Point position){
            this.type = type;
            this.position = position;
        }
    }
    public static class PortsData {
        public LinkedList<PortData> input;
        public LinkedList<PortData> output;
        PortsData(){
            input = new LinkedList<>();
            output = new LinkedList<>();
        }
    }

    protected static class Listener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            /*Container t = ((BaseElement) e.getSource());
            while (true){
                t = t.getParent();
                if(t instanceof JFrame){
                    t.add(new JLabel("asasd"));
                    break;
                }
            }*/
            System.out.println(e.getPoint());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
