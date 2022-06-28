package view.elements;

import javax.swing.*;
import java.awt.*;

public class BaseElement extends JLabel {
    public BaseElement(){ super(); }

    public void setFixedSize(int width, int height){
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }
}
