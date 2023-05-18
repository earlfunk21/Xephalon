package com.morax.xephalon.conversion;

public class Decimal {
    public static String[] toBinary(int decimal) {
        StringBuilder binary = new StringBuilder("");
        StringBuilder solution = new StringBuilder();
        while (decimal > 0) {
            int remainder = decimal % 2;
            binary.insert(0, remainder);
            decimal = decimal / 2;

            solution.append(decimal)
                    .append(" ÷ 2 = ")
                    .append(decimal / 2)
                    .append(", (R: ").append(remainder).append(")\n");
        }

        return new String[]{binary.toString(), solution.toString()};
    }

    public static String[] toOctal(int decimal) {
        StringBuilder octal = new StringBuilder("");
        StringBuilder solution = new StringBuilder();
        while (decimal > 0) {
            int remainder = decimal % 8;
            octal.insert(0, remainder);
            decimal = decimal / 8;

            solution.append(decimal)
                    .append(" ÷ 8 = ")
                    .append(decimal / 8)
                    .append(", (R: ").append(remainder).append(")\n");
        }

        return new String[]{octal.toString(), solution.toString()};
    }

    public static String[] toHexadecimal(int decimal) {
        StringBuilder hexadecimal = new StringBuilder("");
        StringBuilder solution = new StringBuilder();
        while (decimal > 0) {
            int remainder = decimal % 16;
            hexadecimal.insert(0, toHexDigit(remainder));
            decimal = decimal / 16;

            solution.append(decimal)
                    .append(" ÷ 16 = ")
                    .append(decimal / 16)
                    .append(", (R: ").append(toHexDigit(remainder)).append(")\n");
        }

        return new String[]{hexadecimal.toString(), solution.toString()};
    }

    public static char toHexDigit(int digit) {
        if (digit >= 0 && digit <= 9) {
            return (char) ('0' + digit);
        } else {
            return (char) ('A' + digit - 10);
        }
    }
}
