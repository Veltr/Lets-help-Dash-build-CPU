package view.elements.input;

import view.elements.BaseElement;

import javax.swing.*;
import java.awt.*;

public abstract class InputElement extends BaseElement {
    protected int _curValue = 0;
    public InputElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public abstract void setState();
}