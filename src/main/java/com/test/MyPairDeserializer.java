package com.test;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-13
 */
public class MyPairDeserializer extends KeyDeserializer{


    @Override
    public Object deserializeKey(String keyMap, DeserializationContext deserializationContext) throws IOException {

        String[] pairs = keyMap.split("and");
        String firstName = pairs[0].trim();
        String lastName = pairs[1].trim();

        return new MyPair(firstName, lastName);
    }
}
