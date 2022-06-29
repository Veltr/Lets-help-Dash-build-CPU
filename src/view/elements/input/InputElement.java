package view.elements.input;

import model.data.InputElementData;
import view.elements.BaseComponent;

import javax.swing.*;
import java.awt.*;

public class InputElement extends BaseComponent {
    public InputElement() {
        super();
        _data = new InputElementData();
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }
}