package gfx.elements;

import javax.swing.*;
import java.awt.*;

public class ANDElement extends BaseElement {
    public ANDElement(){
        super();
        buildGFX("resources/AND.png");
    }

    @Override
    protected void fillData() {
        super.fillData();
        _portsData.input.add(new PortData(PortData.BusType.B1, new Point(29, 128)));
        _portsData.input.add(new PortData(PortData.BusType.B1, new Point(89, 128)));

        _portsData.output.add(new PortData(PortData.BusType.B1, new Point(55, 0)));
    }
}
