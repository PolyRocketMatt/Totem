package com.github.polyrocketmatt.totem.exception;

/**
 * Created by PolyRocketMatt on 02/10/2020.
 */

public class DivisionByZeroException extends InterpreterException {

    public DivisionByZeroException() {
        super("Division by 0!");
    }

}
