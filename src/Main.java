import model.data.*;
import view.windows.MainWindow;

import java.awt.*;

public class Main {
    private static void viewTest(){
        EventQueue.invokeLater(() -> {
            var ex = new MainWindow();
            ex.setLocationRelativeTo(null);
            ex.setVisible(true);
        });
    }
    private static void modelTest(){
        CircuitData circuit = new CircuitData();


        OutputElementData[] gens = new OutputElementData[2];
        for(int i = 0; i < gens.length; i++){
            gens[i] = new OutputElementData(1);

            circuit.add(gens[i]);
        }
        gens[0].setOutputData(new WireData(BusType.B1, 1));
        gens[1].setOutputData(new WireData(BusType.B1, 1));


        LogicElementData element = new LogicElementData(2, 1, a -> {
            WireData[] out = new WireData[1];
            out[0] = new WireData(a[0].getType(), a[0].getValue() & a[1].getValue());
            return out;
        });
        circuit.add(element);


        InputElementData lamp = new InputElementData(1);
        circuit.add(lamp);


        gens[0].connect(element, 0, 0);
        gens[1].connect(element, 0, 1);

        element.connect(lamp, 0, 0);

        circuit.start();
    }
    public static void main(String[] args) {
        viewTest();
        //modelTest();
    }
}
