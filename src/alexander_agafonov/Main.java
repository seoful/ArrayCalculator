package alexander_agafonov;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyNumberCreator numberCreator = new MyNumberCreator();
        int n = numberCreator.validateAndSetNumberQuantity();
        List<Number> list = numberCreator.generateNumbers(n);
        MyCalculator calculator = new MyCalculator(list);
        calculator.summarize();
        calculator.multiply();
        calculator.getSquareRoot();
        calculator.divideBy(2);
        calculator.divideBy(2.0);
        calculator.divideBy(5.0);
        calculator.divideBy(0.2);
        calculator.divideBy(2);
        calculator.deleteNegativeNumbers();
        calculator.getSquareRoot();

    }
}
