package view.elements.output;

import model.data.OutputElementData;
import view.elements.BaseComponent;

import javax.swing.*;
import java.awt.*;

public class OutputElement extends BaseComponent {
    public OutputElement() {
        super();
        _data = new OutputElementData();
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }
}