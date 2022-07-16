package view.elements.logic;

import model.data.LogicElementData;
import view.elements.BaseComponent;

import javax.swing.*;
import java.awt.*;

public abstract class LogicElement extends BaseComponent {
    public LogicElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
