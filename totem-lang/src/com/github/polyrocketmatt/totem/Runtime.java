package com.github.polyrocketmatt.totem;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 */

public class Runtime {

    private static final String source = "def MyObject(int param1, string param2) {} {{{{}}}}";

    public static void main(String[] args) {
        new Totem(new String[] { source, "-o", "-s" });
    }

}
