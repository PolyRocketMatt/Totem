package com.github.polyrocketmatt.totem.parser;

/**
 * Created by PolyRocketMatt on 03/10/2020.
 *
 * An acceptor can choose to accept a given stream of
 * tokens, only if it matches a certain criterion.
 */

public interface Acceptor {

    boolean accepts();

}
