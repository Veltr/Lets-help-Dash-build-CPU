package view.elements.output;

import view.elements.BaseComponent;

import javax.swing.*;
import java.awt.*;

public abstract class OutputElement extends BaseComponent {
    protected int _curValue = 0;
    public OutputElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}