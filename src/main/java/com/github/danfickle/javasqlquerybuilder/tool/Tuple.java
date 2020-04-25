package com.github.danfickle.javasqlquerybuilder.tool;

import lombok.Data;

/**
 * @author Elias Arndt | bote100
 * Created on 19.04.2020
 */

@Data
public class Tuple<A, B> {
    private final A a;
    private final B b;
}

