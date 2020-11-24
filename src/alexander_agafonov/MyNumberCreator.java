package alexander_agafonov;

import numbers.do_not_change.NumberCreator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The {@code MyNumberCreator} class is used to generate lists of {@link Number} elements
 */
public class MyNumberCreator extends NumberCreator {

    /**
     * Represents subclasses of {@link Number} type.
     */
    enum NumberType {
        INTEGER {
            @Override
            public String toString() {
                return "Integer";
            }

            @Override
            Integer generate(Random random) {
                return randomInt(random);
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return Integer.parseInt(value);
            }
        }, BYTE {
            @Override
            public String toString() {
                return "Byte";
            }

            @Override
            Byte generate(Random random) {
                return (byte) randomInt(random);
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return Byte.parseByte(value);
            }
        }, SHORT {
            @Override
            public String toString() {
                return "Short";
            }

            @Override
            Short generate(Random random) {
                return (short) randomInt(random);
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return Short.parseShort(value);
            }
        }, LONG {
            @Override
            public String toString() {
                return "Long";
            }

            @Override
            Long generate(Random random) {
                return (long) randomInt(random);
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return Long.parseLong(value);
            }
        }, FLOAT {
            @Override
            public String toString() {
                return "Float";
            }

            @Override
            Float generate(Random random) {
                return (float) randomDouble(random);
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return Float.parseFloat(value);
            }
        }, DOUBLE {
            @Override
            public String toString() {
                return "Double";
            }

            @Override
            Double generate(Random random) {
                return randomDouble(random);
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return Double.parseDouble(value);
            }
        }, BIG_INTEGER {
            @Override
            public String toString() {
                return "BigInteger";
            }

            @Override
            BigInteger generate(Random random) {
                return new BigInteger(Integer.toString(randomInt(random)));
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return new BigInteger(value);
            }
        }, BIG_DECIMAL {
            @Override
            public String toString() {
                return "BigDecimal";
            }

            @Override
            BigDecimal generate(Random random) {
                return BigDecimal.valueOf((double) DOUBLE.generate(random));
            }

            @Override
            Number parse(String value) throws NumberFormatException {
                return new BigDecimal(value);
            }
        };

        /**
         * Generates random {@link Integer} number belonging to [{@value NUMBER_LOWER_BOUNDARY}; {@value NUMBER_UPPER_BOUNDARY}].
         *
         * @param random instance of {@link Random} that will be used as generator.
         * @return the {@link Integer} random number belonging to [{@value NUMBER_LOWER_BOUNDARY}; {@value NUMBER_UPPER_BOUNDARY}].
         */
        private static int randomInt(Random random) {
            return NUMBER_LOWER_BOUNDARY + random.nextInt(NUMBER_UPPER_BOUNDARY - NUMBER_LOWER_BOUNDARY + 1);
        }

        /**
         * Generates random {@link Double} number belonging to [{@value NUMBER_LOWER_BOUNDARY}; {@value NUMBER_UPPER_BOUNDARY}].
         *
         * @param random instance of {@link Random} that will be used as generator.
         * @return the random {@link Double}  number belonging to [{@value NUMBER_LOWER_BOUNDARY}; {@value NUMBER_UPPER_BOUNDARY}].
         */
        private static double randomDouble(Random random) {
            return NUMBER_LOWER_BOUNDARY + random.nextDouble() * (NUMBER_UPPER_BOUNDARY - NUMBER_LOWER_BOUNDARY);
        }

        /**
         * Generates random value.
         *
         * @param random instance of {@link Random} that will be used as generator.
         * @return the random value.
         */
        abstract Number generate(Random random);

        /**
         * Parses the {@link String} value to the number type.
         *
         * @param value string value to be parsed.
         * @return the parsed value.
         * @throws NumberFormatException thrown when the input {@link String} have incorrect format and cannot be parsed.
         */
        abstract Number parse(String value) throws NumberFormatException;
    }

    @Override
    public int validateAndSetNumberQuantity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number quantity belonging to [5;20]: ");
        boolean inputIsCorrect = false;
        int numberQuantity = 0;
        while (!inputIsCorrect) {
            try {
                numberQuantity = (int) NumberType.INTEGER.parse(scanner.nextLine()); //NumberFormatException is thrown here if the input is incorrect.
                checkBounds(numberQuantity, MIN_NUMBER_QUANTITY, MAX_NUMBER_QUANTITY); //NumberOutOfBoundsException may be thrown here.
                inputIsCorrect = true;
            } catch (NumberFormatException e) {
                System.out.print("Incorrect format. Number should be integer\nEnter the number again: ");
            } catch (NumberOutOfBoundsException e) {
                System.out.printf("Number quantity should lie in [%d;%d]\nEnter the number again: ",
                        MIN_NUMBER_QUANTITY, MAX_NUMBER_QUANTITY);
            }
        }
        return numberQuantity;
    }

    @Override
    public List<Number> generateNumbers(int numberQuantity) {
        List<Number> list = new ArrayList<>();
        Random random = new Random();
        NumberType[] numbersTypes = NumberType.values();
        for (int i = 0; i < numberQuantity; i++) {
            list.add(numbersTypes[random.nextInt(numbersTypes.length)].generate(random));
        }
        System.out.printf("The generated array: %s\n", list);
        return list;
    }

    @Override
    public List<Number> insertNumbers(int numberQuantity) {
        List<Number> list = new ArrayList<>();
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        NumberType[] numberTypes = NumberType.values();
        for (int i = 0; i < numberQuantity; i++) {
            NumberType numberType = numberTypes[random.nextInt(numberTypes.length)];
            System.out.printf("%d of %d. Enter %s value belonging to [%d;%d]: ",
                    i + 1, numberQuantity, numberType.toString(),
                    NUMBER_LOWER_BOUNDARY, NUMBER_UPPER_BOUNDARY);

            boolean inputIsCorrect = false;

            while (!inputIsCorrect) {
                try {
                    String input = scanner.nextLine();
                    Number number = numberType.parse(input); //NumberFormatException may be thrown here.
                    checkBounds(number, NUMBER_LOWER_BOUNDARY, NUMBER_UPPER_BOUNDARY); //NumberOutOfBoundsException can be thrown here.
                    inputIsCorrect = true;
                    list.add(number);
                } catch (NumberFormatException e) {
                    System.out.printf("Incorrect format. Number should be %s\nEnter the number again: ",
                            numberType.toString());
                } catch (NumberOutOfBoundsException e) {
                    System.out.printf("Number should lie in [%d;%d]\nEnter the number again: ", NUMBER_LOWER_BOUNDARY, NUMBER_UPPER_BOUNDARY);
                }
            }

        }
        System.out.printf("The inserted array: %s\n", list.toString());
        return list;
    }

    /**
     * Checks whether the value belongs to a specified interval.
     *
     * @param value      value to be checked.
     * @param lowerBound lower bound of interval.
     * @param upperBound upper bound of interval.
     * @throws NumberOutOfBoundsException thrown when value does not belong to the interval.
     */
    private void checkBounds(Number value, double lowerBound, double upperBound) throws NumberOutOfBoundsException {
        double doubleValue = value.doubleValue();
        if (doubleValue < lowerBound || doubleValue > upperBound)
            throw new NumberOutOfBoundsException();
    }

}
