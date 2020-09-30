package com.github.polyrocketmatt.totem.lang.utils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Created by PolyRocketMatt on 30/09/2020.
 *
 * Creates an identity for raw parameter use during translation.
 */

public class IdentityResolver {

    /**
     * Generate a random string.
     */
    public String nextString() {
        buffer[0] = lower.toCharArray()[random.nextInt(lower.toCharArray().length)];
        for (int idx = 1; idx < buffer.length; ++idx)
            buffer[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buffer);
    }

    /** The upper case letters */
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /** The lower case letters */
    public static final String lower = upper.toLowerCase(Locale.ROOT);

    /** The digits */
    public static final String digits = "0123456789";

    /** All possible characters */
    public static final String characters = upper + lower + digits;

    /** A prng */
    private final Random random;

    /** The characters as an array of symbols */
    private final char[] symbols;

    /** C-Style declaration of a string called "buffer" */
    private final char[] buffer;

    /**
     * Initialize a resolver.
     *
     * @param length the length of resolvable strings
     * @param random a random
     * @param symbols string of symbols to use
     */
    public IdentityResolver(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buffer = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public IdentityResolver(int length, Random random) {
        this(length, random, characters);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public IdentityResolver(int length) {
        this(length, new SecureRandom());
    }

}
