package com.morax.xephalon.conversion;

public class Hexadecimal {


    public static String[] toOctal(String hexadecimal) {
        String binary = toBinary(hexadecimal)[0];
        String octal = binaryToOctal(binary);
        String solution = generateSolution(hexadecimal, binary, octal);
        return new String[]{octal, solution};
    }

    public static String[] toDecimal(String hexadecimal) {
        int decimal = 0;
        int power = 0;
        StringBuilder solution = new StringBuilder();
        for (int i = hexadecimal.length() - 1; i >= 0; i--) {
            char digit = hexadecimal.charAt(i);
            int value = hexCharToDecimal(digit);
            decimal += value * (int) Math.pow(16, power);
            power++;

            solution.append(digit)
                    .append(" * 16^")
                    .append(power)
                    .append(" = ")
                    .append(value * (int) Math.pow(16, power))
                    .append("\n");
        }

        return new String[]{String.valueOf(decimal), solution.toString()};
    }

    public static String[] toBinary(String hexadecimal) {
        StringBuilder binary = new StringBuilder();
        StringBuilder solution = new StringBuilder();
        for (int i = 0; i < hexadecimal.length(); i++) {
            char digit = hexadecimal.charAt(i);
            String binaryDigit = hexCharToBinary(digit);
            binary.append(binaryDigit);
            solution.append(digit)
                    .append(" (Hexadecimal) -> ")
                    .append(binaryDigit)
                    .append(" (Binary)")
                    .append("\n");
        }

        return new String[]{binary.toString(), solution.toString()};
    }

    public static int hexCharToDecimal(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'F') {
            return c - 'A' + 10;
        } else if (c >= 'a' && c <= 'f') {
            return c - 'a' + 10;
        } else {
            throw new IllegalArgumentException("Invalid hexadecimal character: " + c);
        }
    }

    public static String hexCharToBinary(char c) {
        int decimal = hexCharToDecimal(c);
        StringBuilder binary = new StringBuilder(Integer.toBinaryString(decimal));
        while (binary.length() < 4) {
            binary.insert(0, "0");
        }
        return binary.toString();
    }

    public static String binaryToOctal(String binary) {
        StringBuilder octal = new StringBuilder("");
        int numDigits = binary.length() % 3 == 0 ? 0 : 3 - binary.length() % 3;
        for (int i = 0; i < numDigits; i++) {
            binary = "0" + binary;
        }
        for (int i = 0; i < binary.length(); i += 3) {
            String binaryChunk = binary.substring(i, i + 3);
            int decimal = Integer.parseInt(binaryChunk, 2);
            octal.append(decimal);
        }
        return octal.toString();
    }

    public static String generateSolution(String hexadecimal, String binary, String octal) {
        StringBuilder solution = new StringBuilder();
        for (int i = 0; i < hexadecimal.length(); i++) {
            char digit = hexadecimal.charAt(i);
            String binaryDigit = hexCharToBinary(digit);
            solution.append(digit)
                    .append(" (Hexadecimal) -> ")
                    .append(binaryDigit)
                    .append(" (Binary)")
                    .append("\n");
        }
        solution.append("Binary: ").append(binary).append("\n");
        solution.append("Octal: ").append(octal);
        return solution.toString();
    }
}
