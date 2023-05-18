package com.morax.xephalon.conversion;

public class Octal {

    public static String[] toBinary(String octal) {
        StringBuilder binary = new StringBuilder("");
        for (int i = 0; i < octal.length(); i++) {
            int digit = octal.charAt(i) - '0';
            String binaryDigit = toBinaryDigit(digit);
            binary.append(binaryDigit);
        }

        return new String[]{binary.toString(), generateSolution(octal, binary.toString())};
    }

    public static String toBinaryDigit(int digit) {
        StringBuilder binaryDigit = new StringBuilder(Integer.toBinaryString(digit));
        while (binaryDigit.length() < 3) {
            binaryDigit.insert(0, "0");
        }
        return binaryDigit.toString();
    }

    public static String generateSolution(String octal, String binary) {
        StringBuilder solution = new StringBuilder();
        for (int i = 0; i < octal.length(); i++) {
            int digit = octal.charAt(i) - '0';
            String binaryDigit = toBinaryDigit(digit);
            solution.append(digit)
                    .append(" (Octal) -> ")
                    .append(binaryDigit)
                    .append(" (Binary)")
                    .append("\n");
        }
        solution.append("Octal: ").append(octal).append("\n");
        solution.append("Binary: ").append(binary);
        return solution.toString();
    }

    public String generateSolution(String octal, String binary, int base) {
        StringBuilder solution = new StringBuilder();
        for (int i = 0; i < octal.length(); i++) {
            int digit = octal.charAt(i) - '0';
            String binaryDigit = toBinaryDigit(digit);
            solution.append(digit)
                    .append(" (Octal) -> ")
                    .append(binaryDigit)
                    .append(" (Binary)")
                    .append("\n");
        }
        solution.append("Octal: ").append(octal).append("\n");
        solution.append("Binary: ").append(binary);
        return solution.toString();
    }


    public static String[] toDecimal(String octal) {
        int decimal = 0;
        int power = 0;
        StringBuilder solution = new StringBuilder();
        for (int i = octal.length() - 1; i >= 0; i--) {
            int digit = octal.charAt(i) - '0';
            int value = digit * (int) Math.pow(8, power);
            decimal += value;
            power++;

            solution.append(digit)
                    .append(" * 8^")
                    .append(power)
                    .append(" = ")
                    .append(value)
                    .append("\n");
        }

        return new String[]{String.valueOf(decimal), solution.toString()};
    }

    public static String[] toHexadecimal(String octal) {
        int decimal = Integer.parseInt(toDecimal(octal)[0]);
        return new String[]{toHexadecimal(decimal), generateSolution(octal, decimal, 16)};
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

    public static String generateSolution(String octal, int decimal, int base) {
        StringBuilder solution = new StringBuilder();
        int power = 0;
        for (int i = octal.length() - 1; i >= 0; i--) {
            int digit = octal.charAt(i) - '0';
            int value = digit * (int) Math.pow(8, power);
            solution.append(digit)
                    .append(" * 8^")
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
