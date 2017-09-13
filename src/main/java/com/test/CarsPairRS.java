package com.test;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Map;

/**
 * @author Denys Ovcharuk (DOV) / WorldTicket A/S
 * @since 2017-09-13
 */

@Data
public class CarsPairRS {

    @JsonDeserialize(keyUsing = MyPairDeserializer.class)
    private Map<MyPair, String> carsMap;

}
