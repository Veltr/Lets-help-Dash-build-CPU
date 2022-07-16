package view.elements.input;

import model.data.InputElementData;
import view.elements.BaseComponent;

import javax.swing.*;
import java.awt.*;

public abstract class InputElement extends BaseComponent {
    protected int _curValue = 0;
    public InputElement() {
        super();
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public abstract void setState();
}