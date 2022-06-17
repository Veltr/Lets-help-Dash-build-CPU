package gfx.elements;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BaseElement extends JLabel {
    protected String source;
    public BaseElement() {
        super();
    }

    protected void buildGFX(){
        setIcon(new GFX(source));
    }

    protected static class GFX extends ImageIcon {
        public GFX(String filename){
            super(filename);
        }
    }
}
