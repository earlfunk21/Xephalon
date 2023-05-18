package com.morax.xephalon.conversion;

public class Binary {

    public static String[] toDecimal(String binary) {
        int decimal = 0;
        int power = 0;
        for (int i = binary.length() - 1; i >= 0; i--) {
            int digit = binary.charAt(i) - '0';
            int value = digit * (int) Math.pow(2, power);
            decimal += value;
            power++;
        }

        return new String[]{String.valueOf(decimal), generateSolution(binary, decimal, 10)};
    }

    public static String[] toOctal(String binary) {
        int decimal = Integer.parseInt(toDecimal(binary)[0]);
        String octal = toOctal(decimal);
        return new String[]{octal, generateSolution(binary, decimal, 8)};
    }

    public static String toOctal(int decimal) {
        StringBuilder hexadecimal = new StringBuilder("");
        while (decimal > 0) {
            int remainder = decimal % 16;
            hexadecimal.insert(0, toHexDigit(remainder));
            decimal = decimal / 16;
        }
        return hexadecimal.toString();
    }

    public static String[] toHexadecimal(String binary) {
        int decimal = Integer.parseInt(toDecimal(binary)[0]);
        String hexadecimal = toHexadecimal(decimal);
        return new String[]{hexadecimal, generateSolution(binary, decimal, 16)};
    }

    public static String toHexadecimal(int decimal) {
        StringBuilder hexadecimal = new StringBuilder("");
        while (decimal > 0) {
            int remainder = decimal % 16;
            hexadecimal.insert(0, toHexDigit(remainder));
            decimal = decimal / 16;
        }
        return hexadecimal.toString();
    }

    public static String generateSolution(String binary, int decimal, int base) {
        StringBuilder solution = new StringBuilder();
        int power = 0;
        for (int i = binary.length() - 1; i >= 0; i--) {
            int digit = binary.charAt(i) - '0';
            int value = digit * (int) Math.pow(2, power);
            solution.append(digit)
                    .append(" * 2^")
                    .append(power)
                    .append(" = ")
                    .append(value)
                    .append("\n");
            power++;
        }
        return solution.toString();
    }

    public static char toHexDigit(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) ('0' + digit);
        } else {
            return (char) ('A' + digit - 10);
        }
    }


}
