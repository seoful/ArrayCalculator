package alexander_agafonov;

import numbers.do_not_change.Calculator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MyCalculator} class represents a simple calculator that performs arithmetic operations on lists.
 */
public class MyCalculator extends Calculator {

    /**
     * This is the constructor of a Calculator
     *
     * @param numbers the list of numbers
     */
    public MyCalculator(List<Number> numbers) {
        super(numbers);
    }

    @Override
    public double summarize() {
        double sum = 0;
        for (Number n : getNumbers()) {
            sum += n.doubleValue();
        }
        System.out.printf("The sum of all elements: %f\n", sum);

        return sum;
    }

    @Override
    public double multiply() {
        double mul = 1;
        for (Number n : getNumbers()) {
            mul *= n.doubleValue();
        }
        System.out.printf("The product of all elements: %f\n", mul);

        return mul;
    }

    @Override
    public void deleteNegativeNumbers() {
        List<Number> numbers = getNumbers();
        List<Number> numbersCopy = new ArrayList<>(numbers);
        for (Number n : numbersCopy) {
            if (n.doubleValue() < 0) {
                numbers.remove(n);
            }
        }
        System.out.printf("Array after deleting negative numbers: %s\n", numbers);
    }


    @Override
    public void divideBy(double divisor) {
        try {
            List<Number> numbers = getNumbers();
            for (int i = 0; i < numbers.size(); i++) {
                Number n = numbers.get(i);
                double doubleResult = n.doubleValue() / divisor;
                changeArrayElement(numbers, i, n, doubleResult);
            }
        } catch (ArithmeticException e) {
            System.out.println("Division by zero...\n");
        }
        System.out.printf("Array after division by %f: %s\n", divisor, getNumbers());
    }


    @Override
    public void getSquareRoot() {
        List<Number> numbers = getNumbers();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Array after getting square roots: [");
        for (int i = 0; i < numbers.size(); i++) {
            Number number = numbers.get(i);
            try {
                if (number.doubleValue() < 0)
                    throw new ValueLessThanZeroException();
                double doubleSqrt = Math.sqrt(number.doubleValue());
                changeArrayElement(numbers, i, number, doubleSqrt);
                stringBuilder.append(numbers.get(i)).append(", ");
            } catch (ValueLessThanZeroException e) {
                stringBuilder.append(number).append(" (Value is less then zero), ");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("]");
        System.out.println(stringBuilder.toString());
    }

    /**
     * Changes the value of the element in the specified list without changing it's type.
     *
     * @param numbers        list to be changed.
     * @param index          index of list's element to be changed.
     * @param originalNumber unmodified number belonging to a list .
     * @param newValue       new value to be assigned to an element.
     */
    private void changeArrayElement(List<Number> numbers, int index, Number originalNumber, double newValue) {
        if (originalNumber instanceof Byte)
            numbers.set(index, (byte) newValue);
        else if (originalNumber instanceof Short)
            numbers.set(index, (short) newValue);
        else if (originalNumber instanceof Integer)
            numbers.set(index, (int) newValue);
        else if (originalNumber instanceof Float)
            numbers.set(index, (float) newValue);
        else if (originalNumber instanceof Double)
            numbers.set(index, newValue);
        else if (originalNumber instanceof BigInteger) {
            long longNewValue = (long) newValue;
            numbers.set(index, new BigInteger(Long.toString(longNewValue)));
        } else if (originalNumber instanceof BigDecimal)
            numbers.set(index, new BigDecimal(newValue));

    }
}
