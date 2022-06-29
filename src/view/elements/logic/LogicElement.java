package view.elements.logic;

import model.data.LogicElementData;
import view.elements.BaseComponent;

import javax.swing.*;
import java.awt.*;

public class LogicElement extends BaseComponent {
    public LogicElement() {
        super();
        _data = new LogicElementData();
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }
}
