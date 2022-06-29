package view.elements;

import model.data.BaseElementData;
import model.data.PortData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BaseComponent extends JLabel implements MouseListener {
    protected BaseElementData _data;
    public BaseComponent(){ super(); }

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
            setIcon(new ImageIcon(file.nextLine()));
            setFixedSize(128, 128);

            int n = file.nextInt();
            for(int i = 0; i < n; i++){
                int b = file.nextInt(),
                        x = file.nextInt(),
                        y = file.nextInt();
                PortData.BusType t = PortData.BusType.B1;
                if(b == 8) t = PortData.BusType.B8;
                else if(b == 16) t = PortData.BusType.B16;

                PortData cur = new PortData(t, _data);
                //_data.inputPorts.add(cur);
                _data.add(cur, true);
                var point = new ConnectionPoint(cur, true);
                point.setBounds(x - 5, y - 10, 10, 10);
                add(point);
            }

            n = file.nextInt();
            for(int i = 0; i < n; i++){
                int b = file.nextInt(),
                        x = file.nextInt(),
                        y = file.nextInt();
                PortData.BusType t = PortData.BusType.B1;
                if(b == 8) t = PortData.BusType.B8;
                else if(b == 16) t = PortData.BusType.B16;

                PortData cur = new PortData(t, _data);
                //_data.outputPorts.add(cur);
                _data.add(cur, false);
                var point = new ConnectionPoint(cur, false);
                point.setBounds(x - 5, y, 10, 10);
                add(point);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //region Listener
    @Override
    public void mouseClicked(MouseEvent e) {
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
        ((BaseComponent)e.getSource()).setVisibleAllPorts(true);
        //System.out.println("Enter " + e.getSource());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ((BaseComponent)e.getSource()).setVisibleAllPorts(false);
        //System.out.println("Exit  " + e.getSource());
    }
    //endregion
}
