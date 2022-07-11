package model.exceptions;

import model.data.BaseElementData;
import model.data.PortData;

public class NullConnectionException extends CircuitException {
    public BaseElementData element;
    public PortData port;

    public NullConnectionException(BaseElementData element, PortData port){
        super();
        this.element = element;
        this.port = port;
    }

    @Override
    public String getMessage() {
        return "Element: " + element + "\nPort: " + port;
    }
}
