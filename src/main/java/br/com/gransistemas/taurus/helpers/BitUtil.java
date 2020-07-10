package br.com.gransistemas.taurus.helpers;

public class BitUtil {
    public static boolean check(int number, int index) {
        return (number & (1 << index)) != 0;
    }
}
