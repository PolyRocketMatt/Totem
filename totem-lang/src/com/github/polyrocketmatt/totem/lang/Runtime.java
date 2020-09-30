package com.github.polyrocketmatt.totem.lang;

/**
 * Created by PolyRocketMatt on 29/09/2020.
 */

public class Runtime {

    private static final String source = "C:\\Users\\" + System.getProperty("user.name") + "\\Totem\\test.ttm";

    public static void main(String[] args) {
        new Totem(new String[] { source, "-o" });
    }

}
