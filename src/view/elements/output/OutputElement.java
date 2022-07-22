package view.elements.output;

import view.elements.BaseElement;

import javax.swing.*;
import java.awt.*;

public abstract class OutputElement extends BaseElement {
    protected int _curValue = 0;
    public OutputElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
}