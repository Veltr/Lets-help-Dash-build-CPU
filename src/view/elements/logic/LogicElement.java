package view.elements.logic;

import view.elements.BaseElement;

import javax.swing.*;
import java.awt.*;

public abstract class LogicElement extends BaseElement {
    public LogicElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
