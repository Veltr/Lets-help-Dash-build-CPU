package view.elements;

import model.data.BaseElementData;
import model.data.BusType;
import model.data.PortData;
import view.panels.Workspace;
import view.staff.Wire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BaseComponent extends JLabel {
    protected BaseElementData _data;
    protected ArrayList<ImageIcon> _icons = new ArrayList<>();
    private final ArrayList<ConnectionPoint> _inputPorts = new ArrayList<>();
    private final ArrayList<ConnectionPoint> _outputPorts = new ArrayList<>();

    public BaseComponent(){ super(); addMouseListener(new BaseComponentListener()); }
    public void setFixedSize(int width, int height){
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }
    protected void setVisibleAllPorts(boolean b){
        for(var i : getComponents()) i.setVisible(b);
    }
    protected void getGFX(String filePath) {
        try(var file = new Scanner(new File(filePath))) {
            int n = file.nextInt(); file.nextLine();
            for(int i = 0; i < n; i++)
                _icons.add(new ImageIcon(file.nextLine()));

            setIcon(_icons.get(0));
            setFixedSize(128, 128);

            n = file.nextInt();
            for(int i = 0; i < n; i++){
                int b = file.nextInt(),
                        x = file.nextInt(),
                        y = file.nextInt();
                BusType t = BusType.B1;
                if(b == 8) t = BusType.B8;
                else if(b == 16) t = BusType.B16;

                _data.setPort(i, new PortData(t, i, _data), true);
                var point = new ConnectionPoint(_data.getPortData(i, true), true);
                point.setBounds(x - 5, y - 10, 10, 10);
                add(point);
                _inputPorts.add(point);
            }

            n = file.nextInt();
            for(int i = 0; i < n; i++){
                int b = file.nextInt(),
                        x = file.nextInt(),
                        y = file.nextInt();
                BusType t = BusType.B1;
                if(b == 8) t = BusType.B8;
                else if(b == 16) t = BusType.B16;

                _data.setPort(i, new PortData(t, i, _data), false);
                var point = new ConnectionPoint(_data.getPortData(i, false), false);
                point.setBounds(x - 5, y, 10, 10);
                add(point);
                _outputPorts.add(point);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public BaseElementData getElementData(){
        return _data;
    }
    public void delete(){
        for(var i : getComponents()) ((ConnectionPoint)i).disconnect();
        getParent().remove(this);
    }
    public void writeToFile(DataOutputStream file) throws IOException {
        var name = getClass().getName().split("\\.", 3);
        file.writeUTF(name[2]);

        var location = getBounds();
        file.writeInt(location.x); file.writeInt(location.y);
        file.writeInt(location.width); file.writeInt(location.height);
    }
    public ArrayList<Wire> getAllWires(){
        ArrayList<Wire> out = new ArrayList<>();
        for(var i : getComponents())
            if(((ConnectionPoint)i).getWire() != null) out.add(((ConnectionPoint)i).getWire());
        return out;
    }
    public String getMessageForError(PortData data){
        StringBuilder sb = new StringBuilder();
        if( _inputPorts.size() > data.getIndex() && _inputPorts.get(data.getIndex())._data == data) sb.append("Входной ");
        else if( _outputPorts.size() > data.getIndex() && _outputPorts.get(data.getIndex())._data == data) sb.append("Выходной ");
        sb.append(String.format("порт #%d не соединен", data.getIndex()));
        return sb.toString();
    }
    public Wire connect(BaseComponent to, int outputPortIndex, int inputPortIndex) {
        _data.connect(to._data, outputPortIndex, inputPortIndex);
        Wire wire = new Wire(_outputPorts.get(outputPortIndex));
        wire.setEndPoint(to._inputPorts.get(inputPortIndex));
        return wire;
    }
    protected static class BaseComponentListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            ((BaseComponent)e.getSource()).setVisibleAllPorts(true);
            //System.out.println("Enter " + e.getSource());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ((BaseComponent)e.getSource()).setVisibleAllPorts(false);
            //System.out.println("Exit  " + e.getSource());
        }
    }
}
